package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.port.OutPortBase;
import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;
import jp.go.aist.rtm.RTC.util.DataRef;
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
    }
//    class PublisherA
//        : public RTC::PublisherBase
//    {
//    public:
//        std::string& m_str;
//        PublisherA(std::string& str) : m_str(str) {};
//        virtual ~PublisherA(){m_str = "DeleteA";}
//        void update(){m_str += "A";}
//    };

    class PublisherB extends PublisherBase {

        public DataRef<String> m_str;
        
        public PublisherB(DataRef<String> str) {
            this.m_str = str;
        }
        public void update() {
            this.m_str.v += "B";
        }
    }
//    class PublisherB
//        : public RTC::PublisherBase
//    {
//    public:
//        std::string& m_str;
//        PublisherB(std::string& str) : m_str(str) {};
//        virtual ~PublisherB(){m_str = "DeleteB";}
//        void update(){m_str += "B";}
//    };

    class PublisherC extends PublisherBase {

        public DataRef<String> m_str;
        
        public PublisherC(DataRef<String> str) {
            this.m_str = str;
        }
        public void update() {
            this.m_str.v += "C";
        }
    }
//    class PublisherC
//        : public RTC::PublisherBase
//    {
//    public:
//        std::string& m_str;
//        PublisherC(std::string& str) : m_str(str) {};
//        virtual ~PublisherC(){m_str = "DeleteC";}
//        void update(){m_str += "C";}
//    };

    class PublisherD extends PublisherBase {

        public DataRef<String> m_str;
        
        public PublisherD(DataRef<String> str) {
            this.m_str = str;
        }
        public void update() {
            this.m_str.v += "D";
        }
    }
//    class PublisherD
//        : public RTC::PublisherBase
//    {
//    public:
//        std::string& m_str;
//        PublisherD(std::string& str) : m_str(str) {};
//        virtual ~PublisherD(){m_str = "DeleteD";}
//        void update(){m_str += "D";}
//    };

    final String outport_name = "MyOutPort";
//    const char* outport_name = "MyOutPort";

    class OutPortMock extends OutPortBase {
        
        public OutPortMock(final String name) {
            super(name);
        }
    }
//    class OutPort
//        : public RTC::OutPortBase
//    {
//    public:
//        OutPort(const char* name)
//            : OutPortBase(name)
//        {
//        }
//    };

    private PublisherA m_pubA;
    private PublisherB m_pubB;
    private PublisherC m_pubC;
    private PublisherD m_pubD;
    private OutPortMock m_outport;
    private DataRef<String> m_str;
//    PublisherA* m_pubA;
//    PublisherB* m_pubB;
//    PublisherC* m_pubC;
//    PublisherD* m_pubD;
//    OutPort* m_outport;
//    std::string m_str;

    public OutPortBaseTest() {
        
        this.m_str = new DataRef<String>("");
        this.m_pubA = new PublisherA(this.m_str);
        this.m_pubB = new PublisherB(this.m_str);
        this.m_pubC = new PublisherC(this.m_str);
        this.m_pubD = new PublisherD(this.m_str);
        this.m_outport = new OutPortMock(this.outport_name);
    }
//    OutPortBaseTests()
//    {
//        m_pubA = new PublisherA(m_str);
//        m_pubB = new PublisherB(m_str);
//        m_pubC = new PublisherC(m_str);
//        m_pubD = new PublisherD(m_str);
//        m_outport = new OutPort(outport_name);
//    }

    protected void setUp() throws Exception {
        super.setUp();
    }
//    virtual void setUp()
//    {
//    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }
//    virtual void tearDown()
//    { 
//    }

    /**
     * <p>ポート名取得のテストです。</p>
     * <p>name()メソッドによりポート名を正しく取得できることをテストします。</p>
     */
    public void test_name() {
        
        assertEquals(this.outport_name, this.m_outport.name());
    }
//    void test_name()
//    {
//        CPPUNIT_ASSERT(!strcmp(outport_name, m_outport->name()));
//    }

    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach()メソッドを用いて複数のPublisherを順番に登録した後にupdate()を呼び出し、
     * 登録されている各Publisherが登録順にコールバックされることを確認します。</p>
     */
    public void test_attach() {
        
        this.m_outport.attach("A", this.m_pubA);
        this.m_outport.attach("B", this.m_pubB);
        this.m_outport.attach("C", this.m_pubC);
        this.m_outport.attach("D", this.m_pubD);
        this.m_outport.update();
        
        assertEquals(this.m_str, "ABCD");
    }
//    void test_attach()
//    {
//        m_outport->attach("A", m_pubA);
//        m_outport->attach("B", m_pubB);
//        m_outport->attach("C", m_pubC);
//        m_outport->attach("D", m_pubD);
//        m_outport->notify();
//
//        CPPUNIT_ASSERT(m_str == "ABCD");
//    }

    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach_back()メソッドを用いて複数のPublisherを順番に登録した後にupdate()を呼び出し、
     * 登録されている各Publisherが登録順にコールバックされることを確認します。</p>
     */
    public void test_attach_back() {
        
        this.m_outport.attach_back("A", this.m_pubA);
        this.m_outport.attach_back("B", this.m_pubB);
        this.m_outport.attach_back("C", this.m_pubC);
        this.m_outport.attach_back("D", this.m_pubD);
        this.m_outport.update();

        assertEquals(this.m_str, "ABCD");
    }
//    void test_attach_back()
//    {
//        m_outport->attach_back("A", m_pubA);
//        m_outport->attach_back("B", m_pubB);
//        m_outport->attach_back("C", m_pubC);
//        m_outport->attach_back("D", m_pubD);
//        m_outport->notify();
//
//        CPPUNIT_ASSERT(m_str == "ABCD");
//    }

    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach_front()メソッドを用いて複数のPublisherを順番に登録した後にupdate()を呼び出し、
     * 登録されている各Publisherが登録順の逆順にコールバックされることを確認します。</p>
     */
    public void test_attach_front() {
        
        this.m_outport.attach_front("A", this.m_pubA);
        this.m_outport.attach_front("B", this.m_pubB);
        this.m_outport.attach_front("C", this.m_pubC);
        this.m_outport.attach_front("D", this.m_pubD);
        this.m_outport.update();

        assertEquals(this.m_str, "DCBA");
    }
    
//    void test_attach_front()
//    {
//        m_outport->attach_front("A", m_pubA);
//        m_outport->attach_front("B", m_pubB);
//        m_outport->attach_front("C", m_pubC);
//        m_outport->attach_front("D", m_pubD);
//        m_outport->notify();
//
//        CPPUNIT_ASSERT(m_str == "DCBA");
//    }

    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach_back()メソッドとattach_front()メソッドを用いて複数のPublisherを登録した後に
     * update()を呼び出し、登録されている各Publisherが意図どおりの順にコールバックされることを確認します。</p>
     */
    public void test_attach_mix() {
        
        this.m_outport.attach_back ("A", this.m_pubA); // A
        this.m_outport.attach_back ("B", this.m_pubB); // AB
        this.m_outport.attach_front("C", this.m_pubC); // CAB
        this.m_outport.attach_front("D", this.m_pubD); // DCAB
        this.m_outport.update();

        assertEquals(this.m_str, "DCAB");
    }

//    void test_attach_mix()
//    {
//        m_outport->attach_back ("A", m_pubA); // A
//        m_outport->attach_back ("B", m_pubB); // AB
//        m_outport->attach_front("C", m_pubC); // CAB
//        m_outport->attach_front("D", m_pubD); // DCAB
//        m_outport->notify();
//
//        CPPUNIT_ASSERT(m_str == "DCAB");
//    }

    /**
     * <p>Publisher登録解除のテストです。</p>
     * <p>はじめに複数のPublisherを登録します。その後、１つずつ登録解除していき、
     * 意図どおりに指定したPublihserが登録解除されていることを確認します。</p>
     */
    public void test_detach() {
        
        this.m_outport.attach("A", m_pubA);
        this.m_outport.attach("B", m_pubB);
        this.m_outport.attach("C", m_pubC);
        this.m_outport.attach("D", m_pubD);
        
        this.m_outport.update();
        assertEquals(this.m_str, "ABCD");
        
        this.m_str.v = "";
        this.m_outport.detach("A");
        this.m_outport.update();
        
        assertEquals(this.m_str, "BCD");
        
        this.m_str.v = "";
        this.m_outport.detach("B");
        this.m_outport.update();
        
        assertEquals(this.m_str, "CD");
        
        this.m_str.v = "";
        this.m_outport.detach("D");
        this.m_outport.update();
        
        assertEquals(this.m_str, "C");

        this.m_str.v = "";
        this.m_outport.detach("C");
        this.m_outport.update();
        
        assertEquals(this.m_str, "");
    }

//    void test_detach()
//    {
//        RTC::PublisherBase* pub;
//        m_outport->attach("A", m_pubA);
//        m_outport->attach("B", m_pubB);
//        m_outport->attach("C", m_pubC);
//        m_outport->attach("D", m_pubD);
//        
//        m_outport->notify();
//        CPPUNIT_ASSERT(m_str == "ABCD");
//        
//        m_str.clear();
//        pub = m_outport->detach("A");
//        m_outport->notify();
//        
//        CPPUNIT_ASSERT(m_str == "BCD");
//        
//        delete pub;
//        CPPUNIT_ASSERT(m_str == "DeleteA");
//        
//        m_str.clear();
//        pub = m_outport->detach("B");
//        m_outport->notify();
//        
//        CPPUNIT_ASSERT(m_str == "CD");
//        
//        delete pub;
//        CPPUNIT_ASSERT(m_str == "DeleteB");
//        
//        m_str.clear();
//        pub = m_outport->detach("D");
//        m_outport->notify();
//        
//        CPPUNIT_ASSERT(m_str == "C");
//        
//        delete pub;
//        CPPUNIT_ASSERT(m_str == "DeleteD");
//        
//        m_str.clear();
//        pub = m_outport->detach("C");
//        m_outport->notify();
//        
//        CPPUNIT_ASSERT(m_str == "");
//        
//        delete pub;
//        CPPUNIT_ASSERT(m_str == "DeleteC");
//    }
}
