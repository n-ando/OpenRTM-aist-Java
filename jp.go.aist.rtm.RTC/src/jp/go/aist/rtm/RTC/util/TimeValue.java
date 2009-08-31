package jp.go.aist.rtm.RTC.util;

/**
* <p>時間を表現するクラスです。</p>
*/
public class TimeValue {
    private final int TIMEVALUE_ONE_SECOND_IN_USECS =  1000000; // 1 [sec] = 1000000 [μsec]


    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public TimeValue() {
        this.tv_sec = 0;
        this.tv_usec = 0;
        normalize();
    }

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param sec  設定値(秒)
     * @param usec  設定値(マイクロ秒)
     */
    public TimeValue(long sec, long usec) {
        this.tv_sec = sec;
        this.tv_usec = usec;
    }
    /**
     * <p> TimeValue </p>
     * 
     * @param timeval
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
     * <p>コピーコンストラクタです。</p>
     * 
     * @param tm  コピー元
     */
    public TimeValue(TimeValue tm) {
        this.tv_sec = tm.tv_sec;
        this.tv_usec = tm.tv_usec;
    }
    
    /**
     * <p>設定時間(秒)を取得します。</p>
     * 
     * @return 設定時間(秒)
     */
    public long getSec() {
        return tv_sec;
    }
    
    /**
     * <p>設定時間(マイクロ秒)を取得します。</p>
     * 
     * @return 設定時間(マイクロ秒)
     */
    public long getUsec() {
        return tv_usec;
    }

    /**
     * <p>時間を減算します。</p>
     * 
     * @param tm  減算する時間
     * 
     * @return 減算結果
     */
    public TimeValue minus(TimeValue tm) {
        TimeValue res = new TimeValue();
        if( this.tv_sec>=tm.tv_sec) {
            if( this.tv_usec>=tm.tv_usec ) {
                res.tv_sec = this.tv_sec - tm.tv_sec;
                res.tv_usec = this.tv_usec - tm.tv_usec;
            } else {
                res.tv_sec = this.tv_sec - tm.tv_sec - 1;
                res.tv_usec = (this.tv_usec + 1000000) - tm.tv_usec;
            }
        } else {
            if( tm.tv_usec >= this.tv_usec ) {
                res.tv_sec = - (tm.tv_sec - this.tv_sec);
                res.tv_usec = - (tm.tv_usec - this.tv_usec);
            } else {
                res.tv_sec = -(tm.tv_sec - this.tv_sec - 1);
                res.tv_usec = - (tm.tv_usec + 1000000) + this.tv_usec; 
            }
        }
        res.normalize();
        return res;
    }

    /**
     * <p>時間を加算します。</p>
     * 
     * @param tm  加算する時間
     * 
     * @return 加算結果
     */
    public TimeValue plus(TimeValue tm) {
        TimeValue res = new TimeValue();
        res.tv_sec = this.tv_sec + tm.tv_sec;
        res.tv_usec = this.tv_usec + tm.tv_usec;
        if( res.tv_usec > 1000000 ) {
            ++res.tv_sec;
            res.tv_usec -= 1000000;
        }
        res.normalize();
        return res;
    }

    /**
     * <p>数字→時間へ変換します。</p>
     * 
     * @param time  変換元の数値
     * 
     * @return 時間変換結果
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
     * <p>時間→数字へ変換します。</p>
     * 
     * @return 数字変換結果
     */
    public double toDouble(){
        normalize();
        return this.tv_sec + this.tv_usec/1000000.0;
    }
    
    /**
     * <p> sec </p>
     */
    public long sec() {
        return tv_sec;
    }
    /**
     * <p> usec </p>
     */
    public long usec() {
        return tv_usec;
    }
    /**
     * <p>保持している内容の符号を判定する。</p>
     * 
     * @return 正ならば1を、負ならば-1を、0ならば0
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
     * <p>値の表現を正準形式に正規化する。</p>
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
    * <p>設定された時間(秒単位)</p>
    */   
    private long tv_sec;
    /**
     * <p>設定された時間(マイクロ秒単位)</p>
     */   
    private long tv_usec;
}
