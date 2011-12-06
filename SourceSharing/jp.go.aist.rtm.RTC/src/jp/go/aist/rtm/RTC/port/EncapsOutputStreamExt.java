package jp.go.aist.rtm.RTC.port;

import com.sun.corba.se.impl.encoding.EncapsOutputStream; 
import com.sun.corba.se.spi.orb.ORB;

import jp.go.aist.rtm.RTC.util.ORBUtil;

public class EncapsOutputStreamExt extends EncapsOutputStream {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * <p>
     *
     */
    public EncapsOutputStreamExt(org.omg.CORBA.ORB orb, boolean isLittleEndian) {
        super((ORB)orb,isLittleEndian);
    }

    public final byte[] getByteArray(){
        return toByteArray();
    }
}
