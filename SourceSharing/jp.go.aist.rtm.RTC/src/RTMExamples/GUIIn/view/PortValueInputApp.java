package RTMExamples.GUIIn.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import RTMExamples.GUIIn.control.EventAction;
import RTMExamples.GUIIn.model.PortValue;

public class PortValueInputApp extends JFrame {

    private static final long serialVersionUID = -1889216805167585013L;
    private PortValue<Integer> pvInteger;
    private PortValue<String> pvString;
    private PortValue<Double> pvDouble;
    
    public PortValue<Integer> getSliderValue() {
        return pvInteger;
    }
    public PortValue<String> getTextValue() {
        return pvString;
    }
    public PortValue<Double> getSpinnerValue() {
        return pvDouble;
    }

    public PortValueInputApp() {

        super("GUIIn");

        RootPane pane = new RootPane();

        pvInteger = new PortValue<Integer>("Integer", Integer.valueOf(0), false);
        PortValueSlider inputSlider = new PortValueSlider(pvInteger, -100, 100);
        pane.add(inputSlider);

        pvString = new PortValue<String>("String", "", false);
        PortValueTextField inputString = new PortValueTextField(pvString);
        pane.add(inputString);

        pvDouble = new PortValue<Double>("Double", Double.valueOf(0.0d), false);
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
    
    public void init() {
//        final JFrame frame = new PortValueInputApp();
        final JFrame frame = this;
        frame.setSize(360, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

}
