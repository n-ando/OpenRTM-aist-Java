package jp.go.aist.rtm.RTC;

import java.util.Vector;
import java.lang.System;
import java.lang.Math;
import jp.go.aist.rtm.RTC.util.TimeValue;
/**
* <p>PeriodicTask</p>
*/
  
  /*!
   * TimeMeasure object
   *
   * This object is used for getting statistics of code execution time. 
   * Using get_stat you can get maximum, minimum, mean and standard
   * deviation time for code execution.
   */
public class TimeMeasure {
    private final long ULLONG_MAX = 0xFFFFFFFFFFFFFFFFL;
    public class Statistics
    {
      public double max_interval;
      public double min_interval;
      public double mean_interval;
      public double std_deviation;
    };
    /*!
     * @brief Time statictics object for profiling.
     * 
     * Constructor
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
    /*!
     * @brief Time statictics object for profiling.
     * 
     * Constructor
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
    
    /*!
     * @brief Begin time measurement for time statistics.
     *
     * Begin time measurement for time statistics
     */
    public void tick() {
//        m_begin = gettimeofday(); // [TimeValue]
        m_begin.convert(System.currentTimeMillis()); // [TimeValue]
    }
    
    /*!
     * @brief Finish time measurement for time statistics.
     *
     * End of time measurement for time statistics
     */
    public void tack() {
        if (m_begin.sec() == 0) { return; }

//        m_interval = gettimeofday() - m_begin;
        m_interval.convert(System.currentTimeMillis() - m_begin.toDouble());
        m_record.set(m_count,m_interval);
        ++m_count;
        if (m_count == m_countMax)
          {
            m_count = 0;
            m_recurred = true;
          }
    }

    /**
     * <p> interval </p>
     *
     */
    public TimeValue interval() {
        return m_interval;
    }

    /**
     * <p> reset </p>
     *
     */
    public void reset() {
        m_count = 0;
        m_recurred = false;
        m_begin.convert(0.0);
    }
  
    
    /*!
     * Get number of time measurement buffer
     *
     * @brief Get number of time measurement buffer.
     *
     */
    public int count() {
        return m_recurred ? m_record.size() : m_count;
    }
    
    /*!
     * @brief Get total statistics.
     * Get total statistics
     * max_interval, min_interval, mean_interval [ns]
     */
    public boolean getStatistics(double max_interval,
                       double min_interval,
                       double mean_interval,
                       double stddev) {
        max_interval = (double)0;
        min_interval = (double)ULLONG_MAX;

        double sum = 0;
        double sq_sum = 0;
        int len = count();

        if (len == 0) return false;

        for (int i = 0; i < len; ++i)
          {
            double trecord = m_record.get(i).toDouble();
            sum += trecord;
            sq_sum += trecord * trecord;
        
            if (trecord > max_interval) max_interval = trecord;
            if (trecord < min_interval) min_interval = trecord;
          }
    
        mean_interval = sum / len;
        stddev = Math.sqrt(sq_sum / len - (mean_interval * mean_interval));

        return true;
    }
    
    /**
     * <p> getStatistics </p>
     *
     */
    public Statistics getStatistics() {
        Statistics s = new Statistics();
        getStatistics(s.max_interval, s.min_interval,
                      s.mean_interval, s.std_deviation);
        return s;
    }

    private Vector<TimeValue> m_record = new Vector<TimeValue>();
    private TimeValue m_begin = new TimeValue(0.0);
    private TimeValue m_interval;

    private int m_count;
    private int m_countMax;
    private long m_cpuClock;

    private boolean m_recurred;
  };

