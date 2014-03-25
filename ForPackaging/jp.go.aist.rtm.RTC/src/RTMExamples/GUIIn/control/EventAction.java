package RTMExamples.GUIIn.control;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import RTMExamples.GUIIn.model.PortValue;
import RTMExamples.GUIIn.model.PortValueListener;


public class EventAction extends WindowAdapter implements PortValueListener {

    public EventAction(PortValue[] portValues) {

        _portValues = portValues;
    }

    public void onChanged(final PortValue portValue) {

        System.out.println("-------------------------");
        System.out.println("name: " + portValue.getName());
        System.out.println("data: " + portValue.getData());
        System.out.println("enabled: " + portValue.isEnabled());
        System.out.println("-------------------------");
    }

    public void windowClosed(WindowEvent ev) {
        
        System.out.println("*** EXIT ****");
        System.exit(0);
    }
    
    private PortValue[] _portValues;
}
