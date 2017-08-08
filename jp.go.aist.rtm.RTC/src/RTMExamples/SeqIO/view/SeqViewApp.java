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
                
                synchronized (_octetValMarked_mutex) {
                    if (_octetValMarked) {
                        _view.setOctetVal(_octetVal);
                    }
                }
                synchronized (_shortValMarked_mutex) {
                    if (_shortValMarked) {
                        _view.setShortVal(_shortVal);
                    }
                }
                synchronized (_longValMarked_mutex) {
                    if (_longValMarked) {
                        _view.setLongVal(_longVal);
                    }
                }
                synchronized (_floatValMarked_mutex) {
                    if (_floatValMarked) {
                        _view.setFloatVal(_floatVal);
                    }
                }
                synchronized (_doubleValMarked_mutex) {
                    if (_doubleValMarked) {
                        _view.setDoubleVal(_doubleVal);
                    }
                }
    
                synchronized (_octetSeqVal_mutex) {
                    if (_octetSeqVal != null) {
                        _view.setOctetSeqVal(_octetSeqVal);
                    }
                }
                synchronized (_shortSeqVal_mutex) {
                    if (_shortSeqVal != null) {
                        _view.setShortSeqVal(_shortSeqVal);
                    }
                }
                synchronized (_longSeqVal_mutex) {
                    if (_longSeqVal != null) {
                        _view.setLongSeqVal(_longSeqVal);
                    }
                }
                synchronized (_floatSeqVal_mutex) {
                    if (_floatSeqVal != null) {
                        _view.setFloatSeqVal(_floatSeqVal);
                    }
                }
                synchronized (_doubleSeqVal_mutex) {
                    if (_doubleSeqVal != null) {
                        _view.setDoubleSeqVal(_doubleSeqVal);
                    }
                }
                
                reset();
            }
        }
        
        synchronized public void setOctetVal(byte value) {
            
            synchronized (_octetValMarked_mutex) {
                _octetValMarked = true;
            }
            _octetVal = value;
            registInvoker();
        }

        synchronized public void setShortVal(short value) {
            
            synchronized (_shortValMarked_mutex) {
                _shortValMarked = true;
            }
            _shortVal = value;
            registInvoker();
        }
        
        synchronized public void setLongVal(int value) {
            
            synchronized (_longValMarked_mutex) {
                _longValMarked = true;
            }
            _longVal = value;
            registInvoker();
        }
        
        synchronized public void setFloatVal(float value) {
            
            synchronized (_floatValMarked_mutex) {
                _floatValMarked = true;
            }
            _floatVal = value;
            registInvoker();
        }
        
        synchronized public void setDoubleVal(double value) {
            
            synchronized (_doubleValMarked_mutex) {
                _doubleValMarked = true;
            }
            _doubleVal = value;
            registInvoker();
        }
        
        synchronized public void setOctetSeqVal(byte[] values) {
            
            synchronized (_octetSeqVal_mutex) {
                _octetSeqVal = values;
            }
            registInvoker();
        }
        
        synchronized public void setShortSeqVal(short[] values) {
            
            synchronized (_shortSeqVal_mutex) {
                _shortSeqVal = values;
            }
            registInvoker();
        }
        
        synchronized public void setLongSeqVal(int[] values) {
            
            synchronized (_longSeqVal_mutex) {
                _longSeqVal = values;
            }
            registInvoker();
        }
        
        synchronized public void setFloatSeqVal(float[] values) {
            
            synchronized (_floatSeqVal_mutex) {
                _floatSeqVal = values;
            }
            registInvoker();
        }
        
        synchronized public void setDoubleSeqVal(double[] values) {
            
            synchronized (_doubleSeqVal_mutex) {
                _doubleSeqVal = values;
            }
            registInvoker();
        }
        
        private void reset() {
            
            
            synchronized (_octetValMarked_mutex) {
                _octetValMarked = false;
            }
            synchronized (_shortValMarked_mutex) {
                _shortValMarked = false;
            }
            synchronized (_longValMarked_mutex) {
                _longValMarked = false;
            }
            synchronized (_floatValMarked_mutex) {
                _floatValMarked = false;
            }
            synchronized (_doubleValMarked_mutex) {
                _doubleValMarked = false;
            }
            synchronized (_octetSeqVal_mutex) {
                _octetSeqVal = null;
            }
            synchronized (_shortSeqVal_mutex) {
                _shortSeqVal = null;
            }
            synchronized (_longSeqVal_mutex) {
                _longSeqVal = null;
            }
            synchronized (_floatSeqVal_mutex) {
                _floatSeqVal = null;
            }
            synchronized (_doubleSeqVal_mutex) {
                _doubleSeqVal = null;
            }
            
            _registered = false;
        }
        
        private void registInvoker() {
            
            if (! _registered) {
                _registered = true;
                SwingUtilities.invokeLater(this);
            }
        }
        
        private final Object _octetValMarked_mutex = new Object();
        private boolean _octetValMarked;
        private byte _octetVal;
        private final Object _shortValMarked_mutex = new Object();
        private boolean _shortValMarked;
        private short _shortVal;
        private final Object _longValMarked_mutex = new Object();
        private boolean _longValMarked;
        private int _longVal;
        private final Object _floatValMarked_mutex = new Object();
        private boolean _floatValMarked;
        private float _floatVal;
        private final Object _doubleValMarked_mutex = new Object();
        private boolean _doubleValMarked;
        private double _doubleVal;
        
        private final Object _octetSeqVal_mutex = new Object();
        private byte[] _octetSeqVal;
        private final Object _shortSeqVal_mutex = new Object();
        private short[] _shortSeqVal;
        private final Object _longSeqVal_mutex = new Object();
        private int[] _longSeqVal;
        private final Object _floatSeqVal_mutex = new Object();
        private float[] _floatSeqVal;
        private final Object _doubleSeqVal_mutex = new Object();
        private double[] _doubleSeqVal;
        
        private boolean _registered;
    }
}
