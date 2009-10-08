package jp.go.aist.rtm.RTC.buffer;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;

/**
 *  <p> CdrRingBuffer </p>
 *
 */
public class CdrRingBuffer implements ObjectCreator<BufferBase<OutputStream>>, ObjectDestructor {
    
    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public BufferBase<OutputStream> creator_() {
        return new RingBuffer<OutputStream>();
    }
    /**
     * <p> destructor_ </p>
     * 
     * @param obj    The target instances for destruction
     *
     */
    public void destructor_(Object obj) {
        obj = null;
    }

    /**
     * <p> CdrRingBufferInit </p>
     *
     */
    public static void CdrRingBufferInit() {
        final BufferFactory<RingBuffer<OutputStream>,String> factory 
            = BufferFactory.instance();

        factory.addFactory("ring_buffer",
                    new CdrRingBuffer(),
                    new CdrRingBuffer());
    
    }
}

