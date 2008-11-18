package jp.go.aist.rtm.RTC.util;

/**
* <p>時間を表現するクラスです。</p>
*/
public class TimeValue {

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public TimeValue() {
        this.tv_sec = 0;
        this.tv_usec = 0;
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
        this.tv_sec = (long)time;
        this.tv_usec = (long)((time - this.tv_sec)*1000000);
        return this;
    }

    /**
     * <p>時間→数字へ変換します。</p>
     * 
     * @return 数字変換結果
     */
    public double toDouble(){
        return this.tv_sec + this.tv_usec/1000000.0;
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
