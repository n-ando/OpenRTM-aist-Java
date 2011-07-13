package jp.go.aist.rtm.RTC.util;

import java.util.Vector;


/**
* <p>タイマークラスです。</p>
*/
public class Timer implements Runnable {

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     * 
     * @param interval  
     *   {@.ja タイマー起動周期}
     *   {@.en Timer start cycle}
     */
    public Timer(TimeValue interval) {
        m_interval = new TimeValue(interval);
        m_running = false;
    }

    /**
     * {@.ja スレッドを生成。}
     * {@.en Creates the thread.}
     * <p>
     * {@.ja タイマー用スレッドを生成する。}
     * {@.en Creates the thread for the timer.}
     *
     * @return
     *   {@.ja 常に0を返す。}
     *   {@.en Always returns 0.}
     *
     */
    public int open() {
        Thread t = new Thread(this);
        t.start();
        return 0;
    }

    /**
     * {@.ja タイマーの周期処理。}
     * {@.en Processing at cycle of timer}
     * <p>
     * {@.ja invokeを起動する。}
     * {@.en Starts invoke(). }
     * 
     * @return 
     *   {@.ja 処理結果
     * 　　　　　正常終了　：0
     * 　　　　　異常発生時：例外}
     *   {@.en Result
     * 　　　　　Normal termination:Returns 0.
     * 　　　　　At abnormal generation:Throws out the exception.}
     */
    public int svc() {
        while(m_running) {
            try {
                invoke();
                if( m_interval.getSec() != 0) {
                    Thread.sleep(m_interval.getSec() * 1000);
                }
                Thread.sleep((int)m_interval.getUsec()/1000, ((int)m_interval.getUsec()%1000)*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * {@.ja タイマー周期処理の起動}
     * {@.en Starts the timer cycle processing.}
     */
    public void run() {
        this.svc();
    }

    /**
     * {@.ja タイマー起動}
     * {@.en Starts the timer.}
     */
    public synchronized void start(){
        if(!m_running) {
            m_running = true;
            this.open();
        }
    }

    /**
     * {@.ja タイマー停止}
     * {@.en Stops the timer.}
     */
    public synchronized void stop() {
        m_running = false;
    }
        
    /**
     * {@.ja タイマーの周期処理。(起動周期毎に実行)}
     * {@.en Processing at cycle of timer}
     * <p>
     * {@.ja 
     * <ul>
     * <li>登録されたタスク毎にタイマー残時間をチェック</li>
     * <li>起動周期を超えていた場合，各タスクのinvokeを起動</li>
     * <li>次回起動までの周期を設定</li></ul>}
     * {@.en 
     * <ul>
     * <li>Checks the timer remainder time of each registered task.</li>
     * <li>When the start cycle is exceeded, invoke of each task is started.
     * <li>Sets the cycle until the next starting. </ul>}
     */
    public void invoke(){
        if( m_tasks==null ) m_tasks = new Vector<Task>();
        for(int intIdx=0; intIdx<m_tasks.size(); ++intIdx) {
            m_tasks.elementAt(intIdx).remains = m_tasks.elementAt(intIdx).remains.minus(m_interval); 
            if( m_tasks.elementAt(intIdx).remains.sign() <= 0 ) {
                m_tasks.elementAt(intIdx).listener.invoke();
                m_tasks.elementAt(intIdx).remains = m_tasks.elementAt(intIdx).period;
            }
        }
    }
        
    /**
     * {@.ja タイマー処理用リスナーを登録する。}
     * {@.en Registers the listener for the timer processing.}
     * 
     * @param listener 
     *   {@.ja タイマー処理用リスナー}
     *   {@.en The listener for the timer processing.} 
     * @param tm  
     *   {@.ja 起動周期}
     *   {@.en Start cycle}
     */
    public synchronized ListenerBase registerListener(ListenerBase listener, TimeValue tm) {
        
        if( m_tasks==null ) m_tasks = new Vector<Task>();
        for(int intIdx=0; intIdx<m_tasks.size(); ++intIdx){
            if(m_tasks.elementAt(intIdx).listener.equals(listener)) {
                m_tasks.elementAt(intIdx).period = tm;
                m_tasks.elementAt(intIdx).remains = tm;
                return listener;
            }
        }
        m_tasks.add(new Task(listener, tm));
        return listener;
    }

    /**
     * {@.ja タイマーから起動するコールバック関数を登録する。}
     * {@.en Registers the callback function started with the timer.}
     * 
     * @param cbf  
     *   {@.ja 起動対象コールバック関数}
     *   {@.en the callback function}
     * @param tm  
     *   {@.ja 起動周期}
     *   {@.en cycle} 
     */
    public ListenerBase registerListenerObj(CallbackFunction cbf, TimeValue tm){
        return registerListener(new ListenerObject(cbf), tm);
    }

    /**
     * {@.ja タイマー処理用リスナーを削除する。}
     * {@.en Deletes the callback function for the timer processing.}
     * 
     * @param id  
     *   {@.ja 削除対象リスナーID}
     *   {@.en Listener ID}
     */
    public synchronized boolean unregisterListener(ListenerBase id) {
        for(int intidx=0; intidx<m_tasks.size(); ++intidx) {
            if( m_tasks.elementAt(intidx).listener.equals(id) ) {
                m_tasks.remove(m_tasks.elementAt(intidx));
                return true;
            }
        }
        return false;
    }

    /**
     * <p>タイマー起動周期</p>
     */   
    private TimeValue m_interval = new TimeValue();
    /**
     * <p>タイマー実行フラグ</p>
     */   
    private boolean m_running;
    /**
     * <p>タイマー処理登録用クラス</p>
     */   
    private class Task {
        public Task(ListenerBase l, TimeValue p){
            listener = l;
            period = new TimeValue(p);
            remains = new TimeValue(p);
        }
        public ListenerBase listener;
        TimeValue period;
        TimeValue remains;
    }
    /**
     * <p>タイマー処理登録クラス</p>
     */   
    private Vector<Task> m_tasks = new Vector<Task>();

}
