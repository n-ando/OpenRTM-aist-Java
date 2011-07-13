package jp.go.aist.rtm.RTC;

import java.util.Vector;
import java.lang.System;
import java.lang.Math;
import jp.go.aist.rtm.RTC.util.TimeValue;
  /**
   * {@.ja TimeMeasure クラス。}
   * {@.en TimeMeasure class}
   *
   * <p>
   * {@.ja このクラスは、コード実行時間の統計を取る為に使用します。
   * get_stat を使用してコード実行の最大・最小・平均・標準偏差時間を
   * 計測できます。}
   * {@.en This class is used for getting statistics of code execution time. 
   * Using get_stat you can get maximum, minimum, mean and standard
   * deviation time for code execution.}
   *
   */
public class TimeMeasure {

    /**
     * {@.ja 時間統計用クラス。}
     * {@.en Class for time statistics}
     */
    public class Statistics {
        /**
         * {@.ja コンストラクタ}
         * {@.en Constructor}
         */
        public Statistics() {
            max_interval = 0.0;
            min_interval = 0.0;
            mean_interval = 0.0;
            std_deviation = 0.0;
        }
        /**
         * {@.ja 最大値}
         * {@.en max interval}
         */
        public double max_interval;
        /**
         * {@.ja 最小値}
         * {@.en minimum interval}
         */
        public double min_interval;
        /**
         * {@.ja 平均値}
         * {@.en mean interval}
         */
        public double mean_interval;
        /**
         * {@.ja 標準偏差}
         * {@.en Standard deviation}
         */
        public double std_deviation;
    };

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja 時間統計のプロファイリング}
     * {@.en Time Statistics object for profiling.}
     *
     */
    public TimeMeasure(int buflen) {
        m_begin.convert(0.0);
        m_interval.convert(0.0);
        m_count = 0; 
        m_countMax = buflen + 1;
        m_recurred = false;
        m_record.ensureCapacity(m_countMax);
        for (int i = 0; i < m_countMax; ++i)
          {
            m_record.add(new TimeValue(0, 0));
          }
    }

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja 時間統計のプロファイリング}
     * {@.en Time Statistics object for profiling.}
     *
     */
    public TimeMeasure() {
        int buflen = 100;
        m_begin.convert(0.0);
        m_interval.convert(0.0);
        m_count = 0; 
        m_countMax = buflen + 1;
        m_recurred = false;
        m_record.ensureCapacity(m_countMax);
        for (int i = 0; i < m_countMax; ++i)
          {
            m_record.add(new TimeValue(0, 0));
          }
    }

    /**
     * {@.ja 時間統計の計測を開始する。}
     * {@.en Begin time measurement for time statistics.}
     *
     *
     */
    public void tick() {
        double dtm = (double)System.currentTimeMillis();
        m_begin.convert(dtm/1000);          // [TimeValue]

    }

    /**
     * {@.ja 時間統計の計測を終了する。}
     * {@.en Finish time measurement for time statistics}
     * <p>
     * {@.en End of time measurement for time statistics.}
     *
     */
    public void tack() {
        if (m_begin.sec() == 0) {
            return;
        }
        double dtm = (double)System.currentTimeMillis();
        m_interval.convert((dtm/1000) - m_begin.toDouble());
        m_record.set(m_count,m_interval);
        ++m_count;
        if (m_count == m_countMax)
          {
            m_count = 0;
            m_recurred = true;
          }
    }

    /**
     * {@.ja 経過時間を取得する。}
     * {@.en Get a interval time}
     *
     * @return 
     *   {@.ja TimeValue オブジェクト}
     *   {@.en TimeValue object}
     *
     */
    public TimeValue interval() {
        return m_interval;
    }

    /**
     * {@.ja 統計関連データの初期化。}
     * {@.en Initialize for statistics related data}
     */
    public void reset() {
        m_count = 0;
        m_recurred = false;
        m_begin.convert(0.0);
    }
  
    
    /**
     * {@.ja 時間統計バッファサイズを取得する。}
     * {@.en Get number of time measurement buffer}
     *
     * @return 
     *   {@.ja 計測件数}
     *   {@.en Measurement count}
     *
     */
    public int count() {
        return m_recurred ? m_record.size() : m_count;
    }
    
    /**
     * {@.ja 統計データの総計を取得する。}
     * {@.en Get total statistics}
     *
     *
     * @param s
     *   {@.ja Statistics クラス}
     *   {@.en Statistics Class}
     *
     * @return 
     *   {@.ja true: データあり, false: データなし}
     *   {@.en true: Data found, false: Data not found}
     *
     */
    public boolean getStatistics(Statistics s) {
        s.max_interval = (double)0;
        s.min_interval = Double.MAX_VALUE;
        double sum = 0;
        double sq_sum = 0;
        int len = count();
        if (len == 0) {
            return false;
        }

        for (int i = 0; i < len; ++i)
          {
            double trecord = m_record.get(i).toDouble();
            sum += trecord;

            if (trecord > s.max_interval) s.max_interval = trecord;
            if (trecord < s.min_interval) s.min_interval = trecord;
          }

        s.mean_interval = sum / len;
        for (int i = 0; i < len; ++i)
          {
            double trecord = m_record.get(i).toDouble();
            sq_sum += (trecord - s.mean_interval) * (trecord - s.mean_interval);
          }
        s.std_deviation = Math.sqrt(sq_sum / len );

        return true;
    }
    
    /**
     * {@.ja 統計結果を取得する。}
     * {@.en Get statistics result}
     *
     *
     * @return 
     *   {@.ja 統計結果}
     *   {@.en Statistics result}
     *
     */
    public Statistics getStatistics() {
        Statistics s = new Statistics();
        getStatistics(s);

        return s;
    }

    /**
     * {@.ja Statistics クラスの生成。}
     * {@.en Creation of Statistics class.}
     *
     * <p>
     * {@.ja Statisticsクラスを生成する。}
     * {@.en Creates Statistics class.}
     *
     */
    public Statistics createStatistics() {
        Statistics s = new Statistics();
        return s;
    }

    private Vector<TimeValue> m_record = new Vector<TimeValue>();
    private TimeValue m_begin = new TimeValue(0.0);
    private TimeValue m_interval = new TimeValue(0.0);

    private int m_count;
    private int m_countMax;
    private long m_cpuClock;

    private boolean m_recurred;
};
