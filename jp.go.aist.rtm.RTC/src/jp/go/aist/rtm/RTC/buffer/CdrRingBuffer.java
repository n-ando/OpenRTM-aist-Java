package jp.go.aist.rtm.RTC.buffer;

import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;

import org.omg.CORBA.portable.OutputStream;

/**
 *  <p> CdrRingBuffer </p>
 *
 */
public class CdrRingBuffer implements ObjectCreator<BufferBase<OutputStream>>, ObjectDestructor {
    
    /**
     * {@.ja RingBufferを生成する。}
     * {@.en Creats RingBuffer.}
     * 
     * @return 
     *   {@.ja 生成したインスタンスのRingBuffer}
     *   {@.en Object Created RingBuffer}
     *
     */
    public BufferBase<OutputStream> creator_() {
        return new RingBuffer<OutputStream>();
    }
    /**
     * {@.ja インスタンスを破棄する。}
     * {@.en Destroys the object.}
     * 
     * @param obj    
     *   {@.ja 破壊するインスタンス}
     *   {@.en The target instances for destruction}
     *
     */
    public void destructor_(Object obj) {
        obj = null;
    }

    /**
     * {@.ja 初期化処理。}
     * {@.en Initialization}
     * <p>
     * {@.ja ファクトリへオブジェクトを追加する。}
     * {@.en Adds the object to the factory.}
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

