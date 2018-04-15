package services;

import java.util.Timer;
import java.util.TimerTask;

import serviceui.ServiceUI;

/**
 * The Class SpeakerService.
 */
public class SpeakerService extends Service {

    private final Timer timer;
    private int percentHot;

    public SpeakerService(String name) {
        super(name, "_speaker._udp.local.");
        timer = new Timer();
        percentHot = 0;
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        if (a.equals("get_status")) {
            sendBack(getStatus());
        } else if (a.equals("Warm")) {
// every task should run every 2 seconds
            timer.schedule(new RemindTask(), 0, 2000);
            sendBack("OK");
            ui.updateArea("Warming Speaker");
        } else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            if (percentHot < 100) {
// every time run method gets called it adds 10% until it goes to 100^
                percentHot += 10;
            }
        }
    }

    @Override
    public String getStatus() {
        return "Speaker is " + percentHot + "% warmed.";
    }

    public static void main(String[] args) {
        new SpeakerService("Dominic's Speaker");
      /*  new SpeakerService("Karry's Speaker");
        new SpeakerService("Kevin's Speaker");
        new SpeakerService("Living Room");
        new SpeakerService("Kitchen");*/
    }
    
    
}