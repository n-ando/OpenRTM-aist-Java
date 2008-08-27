package RTMExamples.GUIIn.model;

import java.util.Iterator;
import java.util.Vector;

public class PortValue<T> {

    public PortValue(String name, T data, boolean enabled) {
        
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("invalid name.");
        }
        
        if (data == null) {
            throw new IllegalArgumentException("data is null.");
        }

        _name = name;
        _data = data;
        _enabled = enabled;
    }
    
    public PortValue(final PortValue<T> rhs) {

        _name = rhs._name;
        _data = rhs._data;
        _enabled = rhs._enabled;
    }
    
    public String getName() {
        
        return _name;
    }
    
    public T getData() {
        
        return _data;
    }
    
    public void setData(T data) {
        
        _data = data;
        notifyChanged();
    }
    
    public boolean isEnabled() {
        
        return _enabled;
    }
    
    public void setEnabled(boolean enabled) {
        
        _enabled = enabled;
        notifyChanged();
    }
    
    public void addListener(PortValueListener listener) {
        
        if (! _listeners.contains(listener)) {
            _listeners.add(listener);
        }
    }
    
    public void removeListener(PortValueListener listener) {

        _listeners.remove(listener);
    }
    
    protected void notifyChanged() {

        for (Iterator<PortValueListener> it = _listeners.iterator(); it.hasNext(); ) {
            PortValueListener listener = it.next();
            listener.onChanged(this);
        }
    }
    
    protected String _name;
    protected T _data;
    protected boolean _enabled;
    
    protected Vector<PortValueListener> _listeners = new Vector<PortValueListener>();
}
