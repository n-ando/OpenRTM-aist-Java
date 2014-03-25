package RTMExamples.GUIIn.view;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import RTMExamples.GUIIn.model.PortValue;


public class PortValueSpinner extends JPanel {

    private static final long serialVersionUID = -6492189074201430102L;
    
    public PortValueSpinner(PortValue<Double> portValue,
            double min, double max, double step) {
        
        if (portValue == null) {
            throw new IllegalArgumentException("portValue is null.");
        }
        
        if (min > max) {
            throw new IllegalArgumentException("min > max.");
        }
        
        _portValue = portValue;
        
        _checkBox = new JCheckBox(portValue.getName(), portValue.isEnabled());
        _spinner = new JSpinner(new SpinnerNumberModel(
                portValue.getData().doubleValue(), min, max, step));
        JComponent editor = _spinner.getEditor();
        if (editor instanceof DefaultEditor) {
            ((DefaultEditor) editor).getTextField().setColumns(5);
        }
        
        Listener listener = new Listener();
        _checkBox.addItemListener(listener);
        _spinner.addChangeListener(listener);
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(_checkBox);
        add(_spinner);
    }

    protected class Listener implements ItemListener, ChangeListener {
        
        public void itemStateChanged(ItemEvent ev) {
            
            if (ev.getItemSelectable() == _checkBox) {
                _portValue.setEnabled(_checkBox.isSelected());
            }
        }

        public void stateChanged(ChangeEvent ev) {
        
            if (ev.getSource() == _spinner) {
                Number value = (Number) _spinner.getValue();
                _portValue.setData(value.doubleValue());
            }
        }
    }

    protected PortValue<Double> _portValue;
    protected JCheckBox _checkBox;
    protected JSpinner _spinner;
}
