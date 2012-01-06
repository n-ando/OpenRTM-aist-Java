package jp.go.aist.rtm.RTC.util;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;

/**
 * {@.ja ORBオブジェクトを生成、管理するクラス}
 * {@.en Class that generates, and manages ORB object}
 */
public class ORBUtil {

    private static ORB orb = null;

    /**
     * {@.ja ORBオブジェクトを取得する}
     * {@.en Gets ORB object}
     * 
     * @return 
     *   {@.ja ORBオブジェクト}
     *   {@.en ORB object}
     */
    public static ORB getOrb() {
        return getOrb(null);
    }

    /**
     * {@.ja 管理しているORBオブジェクトをクリアする}
     * {@.en Clears the managed ORB object}
     * 
     */
    public static void clearOrb() {
        orb = null;        
    }

    /**
     * {@.ja ORBオブジェクトを取得する}
     * {@.en Gets ORB object}
     *
     * @param args 
     *   {@.ja アプリケーションの main メソッドのコマンド行引数}
     *   {@.en command-line arguments for the application's main method}
     * 
     * @return 
     *   {@.ja ORBオブジェクト}
     *   {@.en ORB object}
     */
    public static ORB getOrb(String[] args) {
        return getOrb(args, null);
    }
    /**
     * {@.ja 指定された引数に基づいてORBオブジェクトを生成後、取得する}
     * {@.en Generates the ORB object based on the specified argument, 
     * and gets it. }
     * <p>
     * {@.ja すでにORBオブジェクトが生成済みの場合は、それが取得される}
     * {@.en When the ORB object is generated, it is returned}
     * 
     * @param args 
     *   {@.ja ORBオブジェクト生成時の引数}
     *   {@.en Argument for ORB object generation}
     * @return 
     *    {@.ja ORBオブジェクト}
     *    {@.en ORB object}
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
