package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.OutPortBase;
import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
 * <p>OutPortBaseクラスのためのテストケースです。</p>
 */
public class OutPortBaseTest extends TestCase {

    class PublisherA extends PublisherBase {

        public DataRef<String> m_str;
        
        public PublisherA(DataRef<String> str) {
            this.m_str = str;
        }
        public void update() {
            this.m_str.v += "A";
        }
        public ReturnCode init(Properties prop) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setConsumer(InPortConsumer consumer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setBuffer(BufferBase<OutputStream> buffer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data, long sec, long usec) {
            return ReturnCode.PORT_OK;
        }
        public boolean isActive() {
            return true;
        }
        public ReturnCode activate() {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode deactivate() {
            return ReturnCode.PORT_OK;
        }
    }

    class PublisherB extends PublisherBase {

        public DataRef<String> m_str;
        
        public PublisherB(DataRef<String> str) {
            this.m_str = str;
        }
        public void update() {
            this.m_str.v += "B";
        }
        public ReturnCode init(Properties prop) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setConsumer(InPortConsumer consumer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setBuffer(BufferBase<OutputStream> buffer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data, long sec, long usec) {
            return ReturnCode.PORT_OK;
        }
        public boolean isActive() {
            return true;
        }
        public ReturnCode activate() {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode deactivate() {
            return ReturnCode.PORT_OK;
        }
    }

    class PublisherC extends PublisherBase {

        public DataRef<String> m_str;
        
        public PublisherC(DataRef<String> str) {
            this.m_str = str;
        }
        public void update() {
            this.m_str.v += "C";
        }
        public ReturnCode init(Properties prop) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setConsumer(InPortConsumer consumer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setBuffer(BufferBase<OutputStream> buffer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data, long sec, long usec) {
            return ReturnCode.PORT_OK;
        }
        public boolean isActive() {
            return true;
        }
        public ReturnCode activate() {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode deactivate() {
            return ReturnCode.PORT_OK;
        }
    }

    class PublisherD extends PublisherBase {

        public DataRef<String> m_str;
        
        public PublisherD(DataRef<String> str) {
            this.m_str = str;
        }
        public void update() {
            this.m_str.v += "D";
        }
        public ReturnCode init(Properties prop) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setConsumer(InPortConsumer consumer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setBuffer(BufferBase<OutputStream> buffer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data, long sec, long usec) {
            return ReturnCode.PORT_OK;
        }
        public boolean isActive() {
            return true;
        }
        public ReturnCode activate() {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode deactivate() {
            return ReturnCode.PORT_OK;
        }
    }

    final String outport_name = "MyOutPort";

    class OutPortMock extends OutPortBase {
        
        public OutPortMock(final String name) {
//            super(name);
            super(name,"dummy");
        }
    }

    private PublisherA m_pubA;
    private PublisherB m_pubB;
    private PublisherC m_pubC;
    private PublisherD m_pubD;
    private OutPortMock m_outport;
    private DataRef<String> m_str;

    public OutPortBaseTest() {
        
        this.m_str = new DataRef<String>("");
        this.m_pubA = new PublisherA(this.m_str);
        this.m_pubB = new PublisherB(this.m_str);
        this.m_pubC = new PublisherC(this.m_str);
        this.m_pubD = new PublisherD(this.m_str);
        this.m_outport = new OutPortMock(this.outport_name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>ポート名取得のテストです。</p>
     * <p>name()メソッドによりポート名を正しく取得できることをテストします。</p>
     */
    public void test_name() {
        assertEquals(this.outport_name, this.m_outport.name());
    }

    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach()メソッドを用いて複数のPublisherを順番に登録した後にupdate()を呼び出し、
     * 登録されている各Publisherが登録順にコールバックされることを確認します。</p>
     */
    public void test_attach() {
/*
        this.m_outport.attach("A", this.m_pubA);
        this.m_outport.attach("B", this.m_pubB);
        this.m_outport.attach("C", this.m_pubC);
        this.m_outport.attach("D", this.m_pubD);
//        this.m_outport.update();
        
        assertEquals(this.m_str, "ABCD");
*/
    }

    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach_back()メソッドを用いて複数のPublisherを順番に登録した後にupdate()を呼び出し、
     * 登録されている各Publisherが登録順にコールバックされることを確認します。</p>
     */
    public void test_attach_back() {
/*
        this.m_outport.attach_back("A", this.m_pubA);
        this.m_outport.attach_back("B", this.m_pubB);
        this.m_outport.attach_back("C", this.m_pubC);
        this.m_outport.attach_back("D", this.m_pubD);
//        this.m_outport.update();

        assertEquals(this.m_str, "ABCD");
*/
    }

    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach_front()メソッドを用いて複数のPublisherを順番に登録した後にupdate()を呼び出し、
     * 登録されている各Publisherが登録順の逆順にコールバックされることを確認します。</p>
     */
    public void test_attach_front() {
/*
        this.m_outport.attach_front("A", this.m_pubA);
        this.m_outport.attach_front("B", this.m_pubB);
        this.m_outport.attach_front("C", this.m_pubC);
        this.m_outport.attach_front("D", this.m_pubD);
//        this.m_outport.update();

        assertEquals(this.m_str, "DCBA");
*/
    }
    
    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach_back()メソッドとattach_front()メソッドを用いて複数のPublisherを登録した後に
     * update()を呼び出し、登録されている各Publisherが意図どおりの順にコールバックされることを確認します。</p>
     */
    public void test_attach_mix() {
/*
        this.m_outport.attach_back ("A", this.m_pubA); // A
        this.m_outport.attach_back ("B", this.m_pubB); // AB
        this.m_outport.attach_front("C", this.m_pubC); // CAB
        this.m_outport.attach_front("D", this.m_pubD); // DCAB
//        this.m_outport.update();

        assertEquals(this.m_str, "DCAB");
*/
    }

    /**
     * <p>Publisher登録解除のテストです。</p>
     * <p>はじめに複数のPublisherを登録します。その後、１つずつ登録解除していき、
     * 意図どおりに指定したPublihserが登録解除されていることを確認します。</p>
     */
    public void test_detach() {
/*
        
        this.m_outport.attach("A", m_pubA);
        this.m_outport.attach("B", m_pubB);
        this.m_outport.attach("C", m_pubC);
        this.m_outport.attach("D", m_pubD);
        
//        this.m_outport.update();
        assertEquals(this.m_str, "ABCD");
        
        this.m_str.v = "";
        this.m_outport.detach("A");
//        this.m_outport.update();
        
        assertEquals(this.m_str, "BCD");
        
        this.m_str.v = "";
        this.m_outport.detach("B");
//        this.m_outport.update();
        
        assertEquals(this.m_str, "CD");
        
        this.m_str.v = "";
        this.m_outport.detach("D");
//        this.m_outport.update();
        
        assertEquals(this.m_str, "C");

        this.m_str.v = "";
        this.m_outport.detach("C");
//        this.m_outport.update();
        
        assertEquals(this.m_str, "");
*/
    }
}
