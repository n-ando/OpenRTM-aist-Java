package jp.go.aist.rtm.RTC.sample.PortValueInput.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import jp.go.aist.rtm.RTC.sample.PortValueInput.control.EventAction;
import jp.go.aist.rtm.RTC.sample.PortValueInput.model.PortValue;

public class PortValueInputApp extends JFrame {

    private static final long serialVersionUID = -1889216805167585013L;

    public PortValueInputApp() {

        super("PortValueInput");

        RootPane pane = new RootPane();

        PortValue<Integer> pvInteger = new PortValue<Integer>("Integer", 0, false);
        PortValueSlider inputSlider = new PortValueSlider(pvInteger, -100, 100);
        pane.add(inputSlider);

        PortValue<String> pvString = new PortValue<String>("String", "", false);
        PortValueTextField inputString = new PortValueTextField(pvString);
        pane.add(inputString);

        PortValue<Double> pvDouble = new PortValue<Double>("Double", 0.0d, false);
        PortValueSpinner inputDouble = new PortValueSpinner(pvDouble, -1.0d, 1.0d, 0.1d);
        pane.add(inputDouble);
        
        EventAction eventAction = new EventAction(
                new PortValue[] {pvInteger, pvString});
        pvString.addListener(eventAction);
        pvInteger.addListener(eventAction);
        pvDouble.addListener(eventAction);
        addWindowListener(eventAction);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        final JFrame frame = new PortValueInputApp();
        frame.setSize(360, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
