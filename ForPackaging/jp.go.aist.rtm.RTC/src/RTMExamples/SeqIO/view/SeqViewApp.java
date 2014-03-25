package RTMExamples.SeqIO.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SeqViewApp extends JFrame {

    private static final long serialVersionUID = -1889216805167585013L;
    private SeqView _view;

    public static void main(String[] args) {
        
        final JFrame frame = new SeqViewApp();
        frame.setSize(700, 280);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public SeqViewApp() {

        super();

        _view = new SeqView();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(_view, BorderLayout.CENTER);
    }

    public void init(String title) {
        
        final JFrame frame = this;
        frame.setSize(700, 280);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setTitle(title);
    }

    public void setOctetVal(byte oc) {
        _updater.setOctetVal(oc);
    }

    public void setShortVal(short sh) {
        _updater.setShortVal(sh);
    }
    
    public void setLongVal(int lg) {
        _updater.setLongVal(lg);
    }
    
    public void setFloatVal(float fl) {
        _updater.setFloatVal(fl);
    }
    
    public void setDoubleVal(double db) {
        _updater.setDoubleVal(db);
    }

    public void setOctetSeqVal(byte[] oc) {
        _updater.setOctetSeqVal(oc);
    }
    
    public void setShortSeqVal(short[] sh) {
        _updater.setShortSeqVal(sh);
    }
    
    public void setLongSeqVal(int[] lg) {
        _updater.setLongSeqVal(lg);
    }
    
    public void setFloatSeqVal(float[] fl) {
        _updater.setFloatSeqVal(fl);
    }
    
    public void setDoubleSeqVal(double[] db) {
        _updater.setDoubleSeqVal(db);
    }

    private Updater _updater = new Updater();
    
    private class Updater implements Runnable {
        
        public Updater() {
            
            reset();
        }
        
        public void run() {
            
            synchronized (this) {
                
                if (_octetValMarked) {
                    _view.setOctetVal(_octetVal);
                }
                if (_shortValMarked) {
                    _view.setShortVal(_shortVal);
                }
                if (_longValMarked) {
                    _view.setLongVal(_longVal);
                }
                if (_floatValMarked) {
                    _view.setFloatVal(_floatVal);
                }
                if (_doubleValMarked) {
                    _view.setDoubleVal(_doubleVal);
                }
    
                if (_octetSeqVal != null) {
                    _view.setOctetSeqVal(_octetSeqVal);
                }
                if (_shortSeqVal != null) {
                    _view.setShortSeqVal(_shortSeqVal);
                }
                if (_longSeqVal != null) {
                    _view.setLongSeqVal(_longSeqVal);
                }
                if (_floatSeqVal != null) {
                    _view.setFloatSeqVal(_floatSeqVal);
                }
                if (_doubleSeqVal != null) {
                    _view.setDoubleSeqVal(_doubleSeqVal);
                }
                
                reset();
            }
        }
        
        synchronized public void setOctetVal(byte value) {
            
            _octetValMarked = true;
            _octetVal = value;
            registInvoker();
        }

        synchronized public void setShortVal(short value) {
            
            _shortValMarked = true;
            _shortVal = value;
            registInvoker();
        }
        
        synchronized public void setLongVal(int value) {
            
            _longValMarked = true;
            _longVal = value;
            registInvoker();
        }
        
        synchronized public void setFloatVal(float value) {
            
            _floatValMarked = true;
            _floatVal = value;
            registInvoker();
        }
        
        synchronized public void setDoubleVal(double value) {
            
            _doubleValMarked = true;
            _doubleVal = value;
            registInvoker();
        }
        
        synchronized public void setOctetSeqVal(byte[] values) {
            
            _octetSeqVal = values;
            registInvoker();
        }
        
        synchronized public void setShortSeqVal(short[] values) {
            
            _shortSeqVal = values;
            registInvoker();
        }
        
        synchronized public void setLongSeqVal(int[] values) {
            
            _longSeqVal = values;
            registInvoker();
        }
        
        synchronized public void setFloatSeqVal(float[] values) {
            
            _floatSeqVal = values;
            registInvoker();
        }
        
        synchronized public void setDoubleSeqVal(double[] values) {
            
            _doubleSeqVal = values;
            registInvoker();
        }
        
        private void reset() {
            
            _octetValMarked = false;
            _shortValMarked = false;
            _longValMarked = false;
            _floatValMarked = false;
            _doubleValMarked = false;
            
            _octetSeqVal = null;
            _shortSeqVal = null;
            _longSeqVal = null;
            _floatSeqVal = null;
            _doubleSeqVal = null;
            
            _registered = false;
        }
        
        private void registInvoker() {
            
            if (! _registered) {
                _registered = true;
                SwingUtilities.invokeLater(this);
            }
        }
        
        private boolean _octetValMarked;
        private byte _octetVal;
        private boolean _shortValMarked;
        private short _shortVal;
        private boolean _longValMarked;
        private int _longVal;
        private boolean _floatValMarked;
        private float _floatVal;
        private boolean _doubleValMarked;
        private double _doubleVal;
        
        private byte[] _octetSeqVal;
        private short[] _shortSeqVal;
        private int[] _longSeqVal;
        private float[] _floatSeqVal;
        private double[] _doubleSeqVal;
        
        private boolean _registered;
    }
}
