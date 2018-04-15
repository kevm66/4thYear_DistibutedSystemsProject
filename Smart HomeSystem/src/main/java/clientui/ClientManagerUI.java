package clientui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import client.ClientManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
/*
 *
 * @reference Dominic Carr 													/example.java
 *
 */

public class ClientManagerUI extends JFrame {
        private final JTabbedPane allPanels;
    private static final long serialVersionUID = -4512962459244007477L;

    public ClientManagerUI(final ClientManager clientManager) {
        super("Smart Home Management System - Dashboard");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clientManager.end();
            }
        });
        setResizable(false);
        pack();
        setSize(UIConstants.UIWIDTH, UIConstants.UIWIDTH);
        setLocation(setPosition(this));
        allPanels = new JTabbedPane();
        getContentPane().add(allPanels);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        update(this.getGraphics());
        
//        JLabel label1 = new JLabel("Test");
//        label1.setText("Label Text");
//        //label1.setText("");
//        
//        JOptionPane.showMessageDialog(null, "No services running");
   
    }

    public static Point setPosition(Component c) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - c.getWidth()) / 2);
        return new Point(x, 0);
    }

    public void addPanel(JPanel a, String name) {
        allPanels.addTab(name, a);
    }

    public void removePanel(JPanel returnUI) {
        allPanels.remove(returnUI);
    }
    public void updateArea(String e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
