package RTMExamples.GUIIn.view;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import RTMExamples.GUIIn.model.PortValue;


public class PortValueSlider extends JPanel {

    private static final long serialVersionUID = 3108560963073665416L;

    public PortValueSlider(PortValue<Integer> portValue, int min, int max) {
        
        if (portValue == null) {
            throw new IllegalArgumentException("portValue is null.");
        }
        
        if (min > max) {
            throw new IllegalArgumentException("min > max.");
        }
        
        _portValue = portValue;
        
        _checkBox = new JCheckBox(portValue.getName(), portValue.isEnabled());
        
        _slider = new JSlider(min, max, portValue.getData().intValue());
        _slider.setPaintTicks(true);
        _slider.setMajorTickSpacing((max - min) / 5);
        _slider.setMinorTickSpacing((max - min) / 10);
        _slider.setPaintLabels(true);
        
        _textField = new JTextField(portValue.getData().toString(), 4);
        _textField.setHorizontalAlignment(SwingConstants.RIGHT);
        _textField.setEditable(false);
        
        Listener listener = new Listener();
        _checkBox.addItemListener(listener);
        _slider.addChangeListener(listener);
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(_checkBox);
        add(_slider);
        add(_textField);
    }

    protected class Listener implements ItemListener, ChangeListener {
        
        public void itemStateChanged(ItemEvent ev) {
            
            if (ev.getItemSelectable() == _checkBox) {
                _portValue.setEnabled(_checkBox.isSelected());
            }
        }

        public void stateChanged(ChangeEvent ev) {

            if (ev.getSource() == _slider) {
                _textField.setText(Integer.toString(_slider.getValue()));
                _portValue.setData(Integer.valueOf(_slider.getValue()));
            }
        }
    }
    
    protected PortValue<Integer> _portValue;
    protected JCheckBox _checkBox;
    protected JSlider _slider;
    protected JTextField _textField;
}
