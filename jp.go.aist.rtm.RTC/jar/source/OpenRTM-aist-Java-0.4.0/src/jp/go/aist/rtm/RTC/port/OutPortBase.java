package jp.go.aist.rtm.RTC.port;

import java.util.Iterator;
import java.util.Vector;

import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;

/**
 * <p>出力ポートのベース実装クラスです。
 * Publisherの登録やPublisherへのデータ更新通知などの実装を提供します。</p>
 */
public class OutPortBase {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param name ポート名
     */
    public OutPortBase(final String name) {
        
        this.m_name = name;
    }
    
    /**
     * <p>ポート名を取得します。</p>
     * 
     * @return ポート名
     */
    public String name() {
        
        return this.m_name;
    }

    /**
     * <p>指定されたPublisherを、データ更新通知先として登録します。</p>
     * 
     * @param id 指定されたPublisherに割り当てるID
     * @param publisher 登録するPublisherオブジェクト
     */
    public void attach(final String id, PublisherBase publisher) {
        
        attach_back(id, publisher);
    }
    
    /**
     * <p>指定されたPublisherを、データ更新通知先リストの先頭に追加します。</p>
     * 
     * @param id 指定されたPublisherに割り当てるID
     * @param publisher 登録するPublisherオブジェクト
     */
    public void attach_front(final String id, PublisherBase publisher) {
        
        this.m_publishers.insertElementAt(new Publisher(id, publisher), 0);
    }
    
    /**
     * <p>指定されたPublisherを、データ更新通知先リストの最後尾に追加します。</p>
     * 
     * @param id 指定されたPublisherに割り当てるID
     * @param publisher 登録するPublisherオブジェクト
     */
    public void attach_back(final String id, PublisherBase publisher) {
        
        this.m_publishers.add(new Publisher(id, publisher));
    }
    
    /**
     * <p>指定されたPublisherを、データ更新先通知先から削除します。</p>
     * 
     * @param id 削除するPublisherに割り当てたID
     * @return 正常にデータ更新先通知先から削除できた場合は、そのPublisherオブジェクトを返します。<br />
     * 指定したIDに対応するPublisherオブジェクトが存在しない場合には、nullを返します。
     */
    public PublisherBase detach(final String id) {
        
        for (Iterator<Publisher> it = this.m_publishers.iterator(); it.hasNext(); ) {
            Publisher publisher = it.next();
            if (publisher.id.equals(id)) {
                it.remove();
                return publisher.publisher;
            }
        }
        
        return null;
    }
    
    /**
     * <p>登録されているすべてのPublisherオブジェクトに、データ更新を通知します。</p>
     */
    public void update() { // notifyメソッドはObjectクラスで使用されているので、メソッド名を変更した。
        
        for (Iterator<Publisher> it = this.m_publishers.iterator(); it.hasNext(); ) {
            Publisher publisher = it.next();
            publisher.publisher.update();
        }
    }
    
    /**
     * <p>ポート名です。</p>
     */
    protected String m_name = new String();
    
    protected class Publisher {
        
        public Publisher(final String _id, PublisherBase _publisher) {
            
            this.id = _id;
            this.publisher = _publisher;
        }
        
        public String id;
        public PublisherBase publisher;
    }
    
    /**
     * <p>データ更新通知先として登録されているPublisherオブジェクトのリストです。</p>
     */
    protected Vector<Publisher> m_publishers = new Vector<Publisher>();
}
