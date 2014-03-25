package jp.go.aist.rtm.RTC.util.clock;

/**
 * {@.ja グローバルなクロック管理クラス。}
 * {@.en A global clock management class}
 *
 * <p>
 * {@.ja このクラスはグローバルにクロックオブジェクトを提供するシングルトン
 * クラスである。getClocK(クロック名) により IClock 型のクロックオブ
 * ジェクトを返す。利用可能なクロックは "system", "logical" および
 * "adjusted" の３種類である。}
 * {@.en This class is a singleton class that provides clock objects
 * globally. It provides a IClock object by getClock(<clock
 * type>). As clock types, "system", "logical" and "adjusted" are
 * available.}
 */
public class ClockManager {
    
    private static ClockManager __instance = new ClockManager();
    
    public static ClockManager getInstance() {
        return __instance;
    }
    
    public IClock getClock(String clocktype) {
        if( clocktype.equals("logical") ) {
            return m_logicalClock;
        } else if (clocktype.equals("adjusted") ) {
            return m_adjustedClock;
        } else {
            return m_systemClock;
        }
    }
    
    private ClockManager() {
    }
    
    private SystemClock m_systemClock = new SystemClock();
    private LogicalClock  m_logicalClock = new LogicalClock();
    private AdjustedClock m_adjustedClock = new AdjustedClock();

}
