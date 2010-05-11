package jp.go.aist.rtm.RTC.util;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;

/**
 * <p>ORBオブジェクトを生成、管理するクラスです。</p>
 */
public class ORBUtil {

    private static ORB orb = null;

    /**
     * <p>ORBオブジェクトを取得します。</p>
     * 
     * @return ORBオブジェクト
     */
    public static ORB getOrb() {
        return getOrb(null);
    }

    public static void clearOrb() {
        orb = null;        
    }

    public static ORB getOrb(String[] args) {
        return getOrb(args, null);
    }
    /**
     * <p>指定された引数に基づいてORBオブジェクトを生成後、取得します。<br />
     * すでにORBオブジェクトが生成済みの場合は、それが取得されます。</p>
     * 
     * @param args ORBオブジェクト生成時の引数
     * @return ORBオブジェクト
     */
    public static ORB getOrb(String[] args, Properties prop) {
        
        if (orb == null) {
            orb = ORB.init(args, prop);
            try {
                if (orb instanceof com.sun.corba.se.spi.orb.ORB) {
                    Logger logger = ((com.sun.corba.se.spi.orb.ORB) orb).getLogger("");
                    logger.setLevel(Level.SEVERE); // log 
                }
            } catch (Exception e) {
                e.printStackTrace(); // system error

            } catch (NoClassDefFoundError e) {
                e.printStackTrace(); // system error
            }
        }
        
        return orb;
    }
}
