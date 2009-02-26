package jp.go.aist.rtm.RTC.util;

import java.util.Vector;


/**
* <p>タイマークラスです。</p>
*/
public class Timer implements Runnable {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param interval  タイマー起動周期
     */
    public Timer(TimeValue interval) {
        m_interval = new TimeValue(interval);
        m_running = false;
    }

    /**
     * <p>タイマー用スレッドを生成します。</p>
     */
    public int open() {
        Thread t = new Thread(this);
        t.start();
        return 0;
    }

    /**
     * <p>タイマーの周期処理です。
     * invokeを起動します。</p>
     * 
     * @return 処理結果
     * 　　　　　正常終了　：0
     * 　　　　　異常発生時：例外
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
     * <p>タイマーを起動します。</p>
     */
    public void run() {
        this.svc();
    }

    /**
     * <p>タイマーを起動します。</p>
     */
    public synchronized void start(){
        if(!m_running) {
            m_running = true;
            this.open();
        }
    }

    /**
     * <p>タイマーを停止します。</p>
     */
    public synchronized void stop() {
        m_running = false;
    }
        
    /**
     * <p>タイマーの周期処理です。(起動周期毎に実行)
     * <ul>
     * <li>登録されたタスク毎にタイマー残時間をチェック</li>
     * <li>起動周期を超えていた場合，各タスクのinvokeを起動</li>
     * <li>次回起動までの周期を設定</li>
     * </ul>
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
     * <p>タイマー処理用リスナーを登録します。</p>
     * 
     * @param listener  タイマー処理用リスナー
     * @param tm  起動周期
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
     * <p>タイマーから起動するコールバック関数を登録します。</p>
     * 
     * @param cbf  起動対象コールバック関数
     * @param tm  起動周期
     */
    public ListenerBase registerListenerObj(CallbackFunction cbf, TimeValue tm){
        return registerListener(new ListenerObject(cbf), tm);
    }

    /**
     * <p>タイマー処理用リスナーを削除します。</p>
     * 
     * @param id  削除対象リスナーID
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
