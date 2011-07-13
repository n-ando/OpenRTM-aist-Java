package jp.go.aist.rtm.RTC.util;

/**
* {@.ja 時間を表現するクラス}
* {@.en Class that expresses time}
*/
public class TimeValue {
    private final int TIMEVALUE_ONE_SECOND_IN_USECS =  1000000; // 1 [sec] = 1000000 [μsec]


    /**
     * {@.ja デフォルトコンストラクタ}
     * {@.en Default constructor}
     */
    public TimeValue() {
        this.tv_sec = 0;
        this.tv_usec = 0;
        normalize();
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * 
     * @param sec
     *   {@.ja 設定値(秒)}
     *   {@.en Second}
     * @param usec  
     *   {@.ja 設定値(マイクロ秒)}
     *   {@.en Micro second}
     */
    public TimeValue(long sec, long usec) {
        this.tv_sec = sec;
        this.tv_usec = usec;
    }
    /**
     * {@.ja コンストラクタ}
     * 
     * @param timeval
     *   {@.ja 設定値}
     *   {@.en Set value}
     */
    public TimeValue(double timeval) {
        double dbHalfAdj;
        if ( timeval >= 0 ) 
        {
            dbHalfAdj = +0.5;
        }
        else
        {
            dbHalfAdj = -0.5;
        }
        this.tv_sec = (int)timeval;
        this.tv_usec = (long)((timeval - (double)this.tv_sec)*TIMEVALUE_ONE_SECOND_IN_USECS + dbHalfAdj );
        normalize();
    }
  
    /**
     * {@.ja コピーコンストラクタ}
     * {@.en Copy constructor}
     * 
     * @param tm  
     *   {@.ja コピー元}
     *   {@.en Copy origin}
     */
    public TimeValue(TimeValue tm) {
        this.tv_sec = tm.tv_sec;
        this.tv_usec = tm.tv_usec;
    }
    
    /**
     * {@.ja 設定時間(秒)を取得する}
     * {@.en Gets the set time (second).}
     * 
     * @return 
     *   {@.ja 設定時間(秒)}
     *   {@.en set time (second)}
     */
    public long getSec() {
        return tv_sec;
    }
    
    /**
     * {@.ja 設定時間(マイクロ秒)を取得する}
     * {@.en Gets the set time (micro second).}
     * 
     * @return 
     *   {@.ja 設定時間(マイクロ秒)}
     *   {@.en set time (micro second)}
     */
    public long getUsec() {
        return tv_usec;
    }

    /**
     * {@.ja 時間を減算する}
     * {@.en Subtracts time.}
     * 
     * @param tm  
     *   {@.ja 減算する時間}
     *   {@.en Subtacted time}
     * 
     * @return 
     *   {@.ja 減算結果}
     *   {@.en Subtraction result}
     */
    public TimeValue minus(TimeValue tm) {
        TimeValue res = new TimeValue();
        if( this.tv_sec >= tm.tv_sec) {
            if( this.tv_usec >= tm.tv_usec ) {
                res.tv_sec = this.tv_sec - tm.tv_sec;
                res.tv_usec = this.tv_usec - tm.tv_usec;
            } else {
                res.tv_sec = this.tv_sec - tm.tv_sec - 1;
                res.tv_usec = (this.tv_usec + TIMEVALUE_ONE_SECOND_IN_USECS) - tm.tv_usec;
            }
        } else {
            if( tm.tv_usec >= this.tv_usec ) {
                res.tv_sec = - (tm.tv_sec - this.tv_sec);
                res.tv_usec = - (tm.tv_usec - this.tv_usec);
            } else {
                res.tv_sec = - (tm.tv_sec - this.tv_sec - 1);
                res.tv_usec = - (tm.tv_usec + TIMEVALUE_ONE_SECOND_IN_USECS) + this.tv_usec; 
            }
        }
        res.normalize();
        return res;
    }

    /**
     * {@.ja 時間を加算する}
     * {@.en Adds time.} 
     * 
     * @param tm  
     *   {@.ja 加算する時間}
     *   {@.en Added time}
     * 
     * @return 
     *   {@.ja 加算結果}
     *   {@.en Addition result}
     */
    public TimeValue plus(TimeValue tm) {
        TimeValue res = new TimeValue();
        res.tv_sec = this.tv_sec + tm.tv_sec;
        res.tv_usec = this.tv_usec + tm.tv_usec;
        if( res.tv_usec > TIMEVALUE_ONE_SECOND_IN_USECS ) {
            ++res.tv_sec;
            res.tv_usec -= TIMEVALUE_ONE_SECOND_IN_USECS;
        }
        res.normalize();
        return res;
    }

    /**
     * {@.ja 数字→時間へ変換する}
     * {@.en Converts the numerical itno time}
     * 
     * @param time  
     *   {@.ja 変換元の数値}
     *   {@.en Numerical value of conversion origin}
     * 
     * @return 
     *   {@.ja 時間変換結果}
     *   {@.en Conversion result}
     */
    public TimeValue convert(double time) {
        double dbHalfAdj;
        if ( time >= 0 ) 
        {
            dbHalfAdj = +0.5;
        }
        else
        {
            dbHalfAdj = -0.5;
        }

        this.tv_sec = (long)time;
        this.tv_usec = (long)((time - (double)this.tv_sec)*TIMEVALUE_ONE_SECOND_IN_USECS + dbHalfAdj);
        normalize();
        return this;
    }

    /**
     * {@.ja 時間→数字へ変換する}
     * {@.en Converts time into the numerical}
     * 
     * @return 
     *   {@.ja 数字変換結果}
     *   {@.en Conversion result}
     */
    public double toDouble(){
        normalize();
        return this.tv_sec + this.tv_usec/ (double)TIMEVALUE_ONE_SECOND_IN_USECS;
    }
    
    /**
     * {@.ja sec}
     * {@.en Gets sec}
     * return 
     *   {@.ja sec}
     *   {@.en sec}
     */
    public long sec() {
        return tv_sec;
    }
    /**
     * {@.ja usec}
     * {@.en Gets usec}
     * return 
     *   {@.ja usec}
     *   {@.en usec}
     */
    public long usec() {
        return tv_usec;
    }
    /**
     * {@.ja 保持している内容の符号を判定する}
     * {@.en Judges the sign of the maintained content}
     * 
     * @return 
     *   {@.ja 正ならば1を、負ならば-1を、0ならば0}
     *   {@.en positive is 1,negative is -1,0 is 0} 
     */
    public int sign() {
        normalize();
        if( tv_sec > 0 ) return 1;
        if( tv_sec < 0 ) return -1;
        if( tv_usec > 0 ) return 1;
        if( tv_usec < 0 ) return -1;
        return 0;
    }
    /**
     * {@.ja 値の表現を正準形式に正規化する}
     * {@.en Regularizes the value}
     * 
     */
    private void normalize() {
        if( tv_usec >= TIMEVALUE_ONE_SECOND_IN_USECS ) {
            do {
                ++tv_sec;
                tv_usec -= TIMEVALUE_ONE_SECOND_IN_USECS;
            } while (tv_usec >= TIMEVALUE_ONE_SECOND_IN_USECS);
        } else if (tv_usec <= -TIMEVALUE_ONE_SECOND_IN_USECS) {
            do {
                --tv_sec;
                tv_usec += TIMEVALUE_ONE_SECOND_IN_USECS;
            } while (tv_usec <= -TIMEVALUE_ONE_SECOND_IN_USECS);
        }

        if (tv_sec >= 1 && tv_usec < 0) {
            --tv_sec;
            tv_usec += TIMEVALUE_ONE_SECOND_IN_USECS;
        } else if (tv_sec < 0 && tv_usec > 0) {
            ++tv_sec;
            tv_usec -= TIMEVALUE_ONE_SECOND_IN_USECS;
        }
    }

    /**
    * {@.ja 設定された時間(秒単位)}
    */   
    private long tv_sec;
    /**
     * {@.ja 設定された時間(マイクロ秒単位)}
     */   
    private long tv_usec;
}
