package jp.go.aist.rtm.RTC.jfsm.machine;

import java.lang.reflect.InvocationTargetException;

public class EventBase {
    protected final String handlerName;
    protected final Class<?>[] args;
    protected final Object[] kwargs;

    public EventBase(String handlerName, Class<?>[] args, Object... kwargs) {
        this.handlerName = handlerName;
        this.args = args;
        this.kwargs = kwargs;
    }

    void dispatch(StateInfo info) {
       try {
           info.getInstance().getClass().getMethod(handlerName, args).invoke(info.getInstance(), kwargs);
       } catch (InvocationTargetException | SecurityException e) {
           throw new RuntimeException("exception in onExit", e);
       } catch (IllegalAccessException e) {
           throw new RuntimeException("failed to invoke onExit() - perhaps non public?", e);
       } catch (NoSuchMethodException e) {
           throw new RuntimeException(e);
       }
   }
}
