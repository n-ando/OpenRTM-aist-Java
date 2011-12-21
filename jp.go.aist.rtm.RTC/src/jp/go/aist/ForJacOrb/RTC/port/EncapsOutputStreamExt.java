package jp.go.aist.rtm.RTC.port;
/**
 * {@.ja EncapsOutputStreamの拡張クラス。}
 * {@.en Expansion class of EncapsOutputStream}
 */

import org.jacorb.orb.CDROutputStream;
import org.omg.CORBA.ORB;


public class EncapsOutputStreamExt extends CDROutputStream {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * <p>
     *
     */
    public EncapsOutputStreamExt(org.omg.CORBA.ORB orb, boolean isLittleEndian) {
        super(orb);
    }

    /**
     * {@.ja ストリームの内容を配列で取得する。}
     * {@.en Gets the content of the stream in the array.}
     * <p>
     */
    public final byte[] getByteArray(){
        return getBufferCopy();
    }
}
