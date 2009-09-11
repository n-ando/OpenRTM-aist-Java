package jp.go.aist.rtm.RTC.port;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p>出力ポートのベース実装クラスです。
 * Publisherの登録やPublisherへのデータ更新通知などの実装を提供します。</p>
 */
public class OutPortBase extends PortBase {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param name ポート名
     */
    public OutPortBase(final String name) {
        this.m_name = name;
    }

    /**
     * <p>プロパティを取得する</p>
     */
    public Properties properties() {
        return new Properties();
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
        this.m_publishers.add(0, new Publisher(id, publisher));
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
/*
    public void update() { // notifyメソッドはObjectクラスで使用されているので、メソッド名を変更した。
        try {
            for (Iterator<Publisher> it = this.m_publishers.iterator(); it.hasNext(); ) {
                Publisher publisher = it.next();
                publisher.publisher.update();
            }
        } catch(Exception ex) {
        }
    }
*/
    
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
    
    protected ReturnCode_t publishInterfaces(ConnectorProfileHolder connector_profile) {
        return ReturnCode_t.RTC_OK;
    }
    protected ReturnCode_t subscribeInterfaces(
            final ConnectorProfileHolder connector_profile) {
        return ReturnCode_t.RTC_OK;
    }
    protected void unsubscribeInterfaces(final ConnectorProfile connector_profile) {
   }
  /**
   * <p> Activate all Port interfaces </p>
   */
  public void activateInterfaces() {
/*
    for (int i=0, len=m_connectors.size(); i < len; ++i) {
        m_connectors[i].activate();
    }
*/
  }
  
  /**
   * <p> Deactivate all Port interfaces </p>
   */
  public void deactivateInterfaces() {
/*
    for (int i=0, leni=m_connectors.size(); i < len; ++i) {
        m_connectors[i].deactivate();
    }
*/
  }
    /**
     * <p>データ更新通知先として登録されているPublisherオブジェクトのリストです。</p>
     */
    protected List<Publisher> m_publishers = new ArrayList<Publisher>();
    
}
