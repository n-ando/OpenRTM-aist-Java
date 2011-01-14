package RTMExamples.SeqIO.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SeqView extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField _octetField;
    private JTextField _shortField;
    private JTextField _longField;
    private JTextField _floatField;
    private JTextField _doubleField;
    
    private JTextField[] _octetSeqFields;
    private JTextField[] _shortSeqFields;
    private JTextField[] _longSeqFields;
    private JTextField[] _floatSeqFields;
    private JTextField[] _doubleSeqFields;
    
    private NumberFormat _doubleFormat;
    private NumberFormat _floatFormat;
    
    public SeqView() {

        // ウィジェットを作成する
        _octetField = new JTextField("----------");
        _octetField.setEditable(false);
        _octetField.setHorizontalAlignment(JTextField.RIGHT);
 
        _shortField = new JTextField("----------");
        _shortField.setEditable(false);
        _shortField.setHorizontalAlignment(JTextField.RIGHT);
        
        _longField = new JTextField("----------");
        _longField.setEditable(false);
        _longField.setHorizontalAlignment(JTextField.RIGHT);
        
        _floatField = new JTextField("----------");
        _floatField.setEditable(false);
        _floatField.setHorizontalAlignment(JTextField.RIGHT);

        _doubleField = new JTextField("----------");
        _doubleField.setEditable(false);
        _doubleField.setHorizontalAlignment(JTextField.RIGHT);

        _octetSeqFields = new JTextField[10];
        for (int i = 0; i < _octetSeqFields.length; i++) {
            _octetSeqFields[i] = new JTextField("----------");
            _octetSeqFields[i].setEditable(false);
            _octetSeqFields[i].setHorizontalAlignment(JTextField.RIGHT);
        }
        
        _shortSeqFields = new JTextField[10];
        for (int i = 0; i < _shortSeqFields.length; i++) {
            _shortSeqFields[i] = new JTextField("----------");
            _shortSeqFields[i].setEditable(false);
            _shortSeqFields[i].setHorizontalAlignment(JTextField.RIGHT);
        }
        
        _longSeqFields = new JTextField[10];
        for (int i = 0; i < _longSeqFields.length; i++) {
            _longSeqFields[i] = new JTextField("----------");
            _longSeqFields[i].setEditable(false);
            _longSeqFields[i].setHorizontalAlignment(JTextField.RIGHT);
        }
        
        _floatSeqFields = new JTextField[10];
        for (int i = 0; i < _floatSeqFields.length; i++) {
            _floatSeqFields[i] = new JTextField("----------");
            _floatSeqFields[i].setEditable(false);
            _floatSeqFields[i].setHorizontalAlignment(JTextField.RIGHT);
        }
        
        _doubleSeqFields = new JTextField[10];
        for (int i = 0; i < _doubleSeqFields.length; i++) {
            _doubleSeqFields[i] = new JTextField("----------");
            _doubleSeqFields[i].setEditable(false);
            _doubleSeqFields[i].setHorizontalAlignment(JTextField.RIGHT);
        }
        
        // ウィジェットを並べる
        JPanel octetPane = new JPanel();
        octetPane.setLayout(new BoxLayout(octetPane, BoxLayout.Y_AXIS));
        octetPane.add(new JLabel("octet:"));
        octetPane.add(_octetField);
        octetPane.add(Box.createVerticalGlue());

        JPanel shortPane = new JPanel();
        shortPane.setLayout(new BoxLayout(shortPane, BoxLayout.Y_AXIS));
        shortPane.add(new JLabel("short:"));
        shortPane.add(_shortField);
        shortPane.add(Box.createVerticalGlue());
        
        JPanel longPane = new JPanel();
        longPane.setLayout(new BoxLayout(longPane, BoxLayout.Y_AXIS));
        longPane.add(new JLabel("long:"));
        longPane.add(_longField);
        longPane.add(Box.createVerticalGlue());

        JPanel floatPane = new JPanel();
        floatPane.setLayout(new BoxLayout(floatPane, BoxLayout.Y_AXIS));
        floatPane.add(new JLabel("float:"));
        floatPane.add(_floatField);
        floatPane.add(Box.createVerticalGlue());

        JPanel doublePane = new JPanel();
        doublePane.setLayout(new BoxLayout(doublePane, BoxLayout.Y_AXIS));
        doublePane.add(new JLabel("double:"));
        doublePane.add(_doubleField);
        doublePane.add(Box.createVerticalGlue());

        JPanel octetSeqPane = new JPanel();
        octetSeqPane.setLayout(new BoxLayout(octetSeqPane, BoxLayout.Y_AXIS));
        octetSeqPane.add(new JLabel("octet seq:"));
        for (int i = 0; i < _octetSeqFields.length; i++) {
               octetSeqPane.add(_octetSeqFields[i]);
        }

        JPanel shortSeqPane = new JPanel();
        shortSeqPane.setLayout(new BoxLayout(shortSeqPane, BoxLayout.Y_AXIS));
        shortSeqPane.add(new JLabel("short seq:"));
        for (int i = 0; i < _shortSeqFields.length; i++) {
            shortSeqPane.add(_shortSeqFields[i]);
        }

        JPanel longSeqPane = new JPanel();
        longSeqPane.setLayout(new BoxLayout(longSeqPane, BoxLayout.Y_AXIS));
        longSeqPane.add(new JLabel("long seq:"));
        for (int i = 0; i < _longSeqFields.length; i++) {
            longSeqPane.add(_longSeqFields[i]);
        }

        JPanel floatSeqPane = new JPanel();
        floatSeqPane.setLayout(new BoxLayout(floatSeqPane, BoxLayout.Y_AXIS));
        floatSeqPane.add(new JLabel("float seq:"));
        for (int i = 0; i < _floatSeqFields.length; i++) {
            floatSeqPane.add(_floatSeqFields[i]);
        }

        JPanel doubleSeqPane = new JPanel();
        doubleSeqPane.setLayout(new BoxLayout(doubleSeqPane, BoxLayout.Y_AXIS));
        doubleSeqPane.add(new JLabel("double seq:"));
        for (int i = 0; i < _doubleSeqFields.length; i++) {
            doubleSeqPane.add(_doubleSeqFields[i]);
        }

        JPanel octetGroupPane = new JPanel();
        octetGroupPane.setLayout(new BoxLayout(octetGroupPane, BoxLayout.Y_AXIS));
        octetGroupPane.add(octetPane);
        octetGroupPane.add(octetSeqPane);
        octetGroupPane.add(Box.createVerticalGlue());

        JPanel shortGroupPane = new JPanel();
        shortGroupPane.setLayout(new BoxLayout(shortGroupPane, BoxLayout.Y_AXIS));
        shortGroupPane.add(shortPane);
        shortGroupPane.add(shortSeqPane);
        shortGroupPane.add(Box.createVerticalGlue());

        JPanel longGroupPane = new JPanel();
        longGroupPane.setLayout(new BoxLayout(longGroupPane, BoxLayout.Y_AXIS));
        longGroupPane.add(longPane);
        longGroupPane.add(longSeqPane);
        longGroupPane.add(Box.createVerticalGlue());
        
        JPanel floatGroupPane = new JPanel();
        floatGroupPane.setLayout(new BoxLayout(floatGroupPane, BoxLayout.Y_AXIS));
        floatGroupPane.add(floatPane);
        floatGroupPane.add(floatSeqPane);
        floatGroupPane.add(Box.createVerticalGlue());

        JPanel doubleGroupPane = new JPanel();
        doubleGroupPane.setLayout(new BoxLayout(doubleGroupPane, BoxLayout.Y_AXIS));
        doubleGroupPane.add(doublePane);
        doubleGroupPane.add(doubleSeqPane);
        doubleGroupPane.add(Box.createVerticalGlue());

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(octetGroupPane);
        add(shortGroupPane);
        add(longGroupPane);
        add(floatGroupPane);
        add(doubleGroupPane);
        
        // フォーマッタを作成する
        _doubleFormat = new DecimalFormat("0.000000000000000E0");
        _floatFormat = new DecimalFormat("0.000000000000000E0");
    }

    public void setOctetVal(byte value) {
        
       _octetField.setText(String.format("0x%02X", value));
    }

    public void setShortVal(short value) {

        _shortField.setText(Short.toString(value));
    }

    public void setLongVal(int value) {
        
        _longField.setText(Long.toString(value));
    }
    
    public void setFloatVal(float value) {

        _floatField.setText(_floatFormat.format(value));
    }
    
    public void setDoubleVal(double value) {

        _doubleField.setText(_doubleFormat.format(value));
    }

    public void setOctetSeqVal(byte[] values) {
        
        int len = Math.min(values.length, _octetSeqFields.length);
        for (int i = 0; i < len; i++) {
               _octetSeqFields[i].setText(String.format("0x%02X", values[i]));
        }
    }

    public void setShortSeqVal(short[] values) {
        
        int len = Math.min(values.length, _shortSeqFields.length);
        for (int i = 0; i < len; i++) {
            _shortSeqFields[i].setText(Short.toString(values[i]));
        }
    }

    public void setLongSeqVal(int[] values) {
        
        int len = Math.min(values.length, _longSeqFields.length);
        for (int i = 0; i < len; i++) {
            _longSeqFields[i].setText(Long.toString(values[i]));
        }
    }

    public void setFloatSeqVal(float[] values) {
        
        int len = Math.min(values.length, _floatSeqFields.length);
        for (int i = 0; i < len; i++) {
            _floatSeqFields[i].setText(_floatFormat.format(values[i]));
        }
    }
    
    public void setDoubleSeqVal(double[] values) {
        
        int len = Math.min(values.length, _doubleSeqFields.length);
        for (int i = 0; i < len; i++) {
            _doubleSeqFields[i].setText(_doubleFormat.format(values[i]));
        }
    }
}
