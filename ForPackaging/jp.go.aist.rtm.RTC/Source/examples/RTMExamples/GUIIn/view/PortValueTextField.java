package RTMExamples.GUIIn.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTMExamples.GUIIn.model.PortValue;

public class PortValueTextField extends JPanel {

    private static final long serialVersionUID = -3045116031511834833L;

    public PortValueTextField(PortValue<String> portValue) {
        
        if (portValue == null) {
            throw new IllegalArgumentException("portValue is null.");
        }
        
        _portValue = portValue;
        _checkBox = new JCheckBox(portValue.getName(), portValue.isEnabled());
        _textField = new JTextField(portValue.getData(), 24);
        
        Listener listener = new Listener();
        _checkBox.addItemListener(listener);
        _textField.addActionListener(listener);
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(_checkBox);
        add(_textField);
    }
    
    protected class Listener implements ItemListener, ActionListener {

        public void itemStateChanged(ItemEvent ev) {
            
            if (ev.getItemSelectable() == _checkBox) {
                _portValue.setEnabled(_checkBox.isSelected());
            }
        }

        public void actionPerformed(ActionEvent ev) {
            
            if (ev.getSource() == _textField) {
                _portValue.setData(_textField.getText());
            }
        }
    }
    
    protected PortValue<String> _portValue;
    protected JCheckBox _checkBox;
    protected JTextField _textField;
}
