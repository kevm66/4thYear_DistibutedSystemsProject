package services;

import com.google.gson.Gson;
import models.OvenModel;

import serviceui.ServiceUI;

/*
 * @OvenService.java							
 *
 * @author:Karolina Laptas, x14446332
 *
 * @reference sample skeleton by Dominic Carr https://moodle.ncirl.ie/course/view.php?id=1473	
 */
public class OvenService extends Service {

    private int highestTemp;
    private int lowestTemp;
    private int longestTime;
    private int shortestTime;
    private int currentTemp;
    private int currentTime;
    private int resetTime;
    private int setTime;
    private static boolean isWarmed, isCooling, isOn, isOff,isSet, isReset;

    public OvenService(String name) {
        super(name, "_oven._udp.local.");
        highestTemp = 250;
        lowestTemp = 0; 
        longestTime = 60;
        shortestTime = 0;
// the temperature the oven is at
        currentTemp = 0; 
        currentTime = 0; 
        isWarmed = false;
        isCooling = false;
        isOn = false;
        isOff = true;
        isSet = false;
        isReset = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        System.out.println("recieved: " + a);
        OvenModel ovenM = new Gson().fromJson(a, OvenModel.class);

        if (ovenM.getAction() == OvenModel.Action.STATUS) {
            String msg = getStatus();
            String json = new Gson().toJson(new OvenModel(OvenModel.Action.STATUS, msg));
            sendBack(json);
        }
//warm oven
        else if (ovenM.getAction() == OvenModel.Action.warm) {
            warmTemp();
            String msg = (isWarmed) ? "The oven temperature is increasing" : "Cannot turn temperature up any higher, Oven is on its highest temperature";
            String json = new Gson().toJson(new OvenModel(OvenModel.Action.warm, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isWarmed) ? "The oven temperature is increasing" : "Cannot turn temperature up any higher, Oven is on its highest temperature";
            ui.updateArea(serviceMessage);
        }
//cool oven
        else if (ovenM.getAction() == OvenModel.Action.cool) {
            coldTemp();
            String msg = (isCooling) ? "The oven temperature is decreasing" : "Cannot turn temperature down any lower, Oven is on its lowest temperature";
            String json = new Gson().toJson(new OvenModel(OvenModel.Action.cool, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isCooling) ? "The oven temperature is decreasing" : "Cannot turn temperature down more, Oven is on its lowest temperature";
            ui.updateArea(serviceMessage);
//turn on
        } else if (ovenM.getAction() == OvenModel.Action.On) {
            powerOn();
            String msg = (isOn) ? "The Oven is ON" : "Oven is On";
            String json = new Gson().toJson(new OvenModel(OvenModel.Action.On, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isOn) ? "The oven is On" : "...";
            ui.updateArea(serviceMessage);

//turn off
        } else if (ovenM.getAction() == OvenModel.Action.Off) {
            powerOff();
            String msg = (isOff) ? "Oven is OFF" : "The oven is off";
            String json = new Gson().toJson(new OvenModel(OvenModel.Action.Off, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isOff) ? "Oven is OFF":"...";
            ui.updateArea(serviceMessage);
            
        }else if (ovenM.getAction() == OvenModel.Action.reset) {
            reset();
            String msg = (isReset) ? "The timer is reset to 0 minutes" : "Sorry  the timer is already reset";
            String json = new Gson().toJson(new OvenModel(OvenModel.Action.reset, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isReset) ? "The timer is reset to 0 minutes" : "Sorry you cannot reset the timer again";
            ui.updateArea(serviceMessage);
        
        }else if (ovenM.getAction() == OvenModel.Action.setTimer) {
            set();
            String msg = (isSet) ? "A minute have been added to the timer " : "...";//"Sorry the timer is set to 60 minutes cannot add any more";
            String json = new Gson().toJson(new OvenModel(OvenModel.Action.setTimer, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isSet) ? "A minute have been added to the timer " : "Sorry you cannot set the timer for any longer";
            ui.updateArea(serviceMessage);    
            
        } else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }

    public void warmTemp() {
        if (currentTemp != highestTemp) {
            isWarmed = true;
            currentTemp += 50;
        } else {
            isWarmed = false;
        }
    }

    public void coldTemp() {
        if (currentTemp != lowestTemp) {
            isCooling = true;
            currentTemp -= 50;
        } else {
            isCooling = false;
        }
    }
    private void reset() {
         if (currentTime != 0) {
            isReset = true;
            currentTime = 0;
        } else {
            isReset = false;
            
        }
     }
    private void set() {
         if (currentTime != longestTime) {
            isSet = true;
            currentTime += 01;
        } else {
            isSet = false;
            ui.updateArea("Timer is  on " + currentTime + "minutes.");
            
        }
    }
     

    public void powerOff() {
        System.out.println("Oven is turned Off");
    }

    public void powerOn() {
        System.out.println("Oven is turned on");
    }

    @Override
    public String getStatus() {
        return "Oven is " + currentTemp + "ºC warmed.";
    }
       

    public static void main(String[] args) {
        new OvenService("Oven");
    }

    
}
