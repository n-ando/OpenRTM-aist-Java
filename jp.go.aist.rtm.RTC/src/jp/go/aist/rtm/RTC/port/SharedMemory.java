package jp.go.aist.rtm.RTC.port;

import java.lang.Long;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import OpenRTM.CdrDataHolder;
import OpenRTM.PortSharedMemory;

import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.LongHolder;
import jp.go.aist.rtm.RTC.util.ORBUtil;

import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.InputStream;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinNT.HANDLE;
/**
 * {@.ja SharedMemory クラス}
 * {@.en SharedMemory class}
 * <p>
 * {@.ja 共有メモリ操作クラス
 * CORBAによる通信により、mmapの初期化、終了などがリモートに操作できる}
 * {@.en This is the class to operate a shared memory.
 * This class can operate following operationis  using CORBA communication.
 * <li>Initialization of mmap 
 * <li>Finalization of mmap </li></ul>}
 *
 */


//public abstract class SharedMemory implements PortSharedMemory {
//public class SharedMemory implements OpenRTM.PortSharedMemory {
public class SharedMemory extends OpenRTM.PortSharedMemoryPOA {
    private static final int DEFAULT_SIZE = 8;
    private static final int DEFAULT_MEMORY_SIZE = 2*1024*1024;

    static private String SHARED_NAME = "/dev/shm/";

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     */
    public SharedMemory(){
        rtcout = new Logbuf("SharedMemory");
        m_memory_size = DEFAULT_MEMORY_SIZE;
        m_endian  = true;
        m_os = System.getProperty("os.name").toLowerCase();
        m_hMapping = null;
        m_pMappingView = null;
    }

  
    /**
     *
     * {@.ja 文字列で指定したデータサイズを数値に変換する}
     * {@.en Changes a string to the value.}
     * <p>
     * 1M -> 1048576
     * 1k -> 1024
     * 100 -> 100 
     *
     * @param size_str
     *  {@.ja データサイズ(文字列)}
     *  {@.en String}
     * 
     * @return 
     *  {@.ja データサイズ(数値)}
     *  {@.en value}
  # int string_to_MemorySize(string size_str);
     */
    public long string_to_MemorySize(String size_str){
        if( size_str==null || size_str.equals("") ) {
            return DEFAULT_MEMORY_SIZE;
        }
        String str = size_str.toUpperCase();
        if(str.indexOf('M')>0){
            return (1048576 * Long.parseLong(str.split("M")[0]));
        }
        else if(str.indexOf('K')>0){
            return (1024 * Long.parseLong(str.split("K")[0]));
        }
        return (Long.parseLong(str));
    }


    /**
     * 
     * {@.ja 共有メモリの初期化}
     * {@.en Initializes a shared memory.}
     * 
     *  # windowsではページングファイル上に領域を確保する
     *  # Linuxでは/dev/shm以下にファイルを作成する
     *  # 作成したファイルの内容を仮想アドレスにマッピングする
     *
     * @param memory_size 
     *  {@.ja 共有メモリのサイズ}
     *  {@.en Size of a shared momory}
     * @param shm_address 
     *  {@.ja 空間名}
     *  {@.en name of memory}
     * # void create_memory(int memory_size, string shm_address);
     */
    public void create_memory (long memory_size, String shm_address){
        rtcout.println(Logbuf.TRACE, 
                "create():memory_size="
                + memory_size +",shm_address=" + shm_address);
        m_memory_size = memory_size;
        m_shm_address = shm_address;

        String address;
        if(m_os.startsWith("windows")){
            HANDLE fileHandle = new HANDLE(new Pointer(0xffffffff));
            WinNT.SECURITY_ATTRIBUTES psa = null;
            try{
                m_hMapping = Kernel32.INSTANCE.CreateFileMappingA(
                            fileHandle,
                            psa,
                            0x04,//PAGE_READWRITE
                            0,
                            (int)m_memory_size, 
                            m_shm_address
                        );
                m_pMappingView = Kernel32.INSTANCE.MapViewOfFile(
                                   m_hMapping,
                                   0x001f,//FILE_MAP_ALL_ACCESS
                                   0,
                                   0,
                                   (int)m_memory_size
                               );
            }
            catch(Exception ex) {
                rtcout.println(Logbuf.ERROR,"Open error  "+ex.toString() );
            }
        }
        else {
            address = SHARED_NAME+m_shm_address;
            RandomAccessFile file = null;
            try{
                file = new RandomAccessFile(address, "rw");
                file.setLength(m_memory_size);
                file.close();
            }
            catch(Exception ex) {
                rtcout.println(Logbuf.ERROR,"Open error  "+ex.toString() );
            }
            if(file != null){
                try{
                    file.close();
                }
                catch(Exception ex) {
                    rtcout.println(Logbuf.ERROR,"Close error  "+ex.toString() );
                }
            }
        }
/*
        try{
            RandomAccessFile file = new RandomAccessFile(address, "rw");
            file.setLength(m_memory_size);
        }
        catch(Exception ex) {
            rtcout.println(Logbuf.ERROR,"Open error  "+ex.toString() );
        }
*/
        if(m_smInterface!=null){
            m_smInterface.open_memory(m_memory_size, m_shm_address);
        }
        

    }
    /**
     * 
     * {@.ja 共有メモリのマッピングを行う}
     * {@.en Open a shared memory.}
     *
     * @param memory_size 
     *  {@.ja 共有メモリのサイズ}
     *  {@.en size of shared momoery}
     * @param shm_address 
     *  {@.ja 空間名}
     *  {@.en name of memory}
  # void open_memory(int memory_size, string shm_address);
     */
    public void open_memory (long memory_size, String shm_address){
        rtcout.println(Logbuf.TRACE, 
                "open():memory_size="
                + memory_size +",shm_address=" + shm_address);
        m_memory_size = memory_size;
        m_shm_address = shm_address;
        String address;
        if(m_os.startsWith("windows")){
            HANDLE fileHandle = new HANDLE(new Pointer(0xffffffff));
            WinNT.SECURITY_ATTRIBUTES psa = null;
            try{
                m_hMapping = Kernel32.INSTANCE.CreateFileMappingA(
                            fileHandle,
                            psa,
                            0x04,//PAGE_READWRITE
                            0,
                            (int)m_memory_size, 
                            m_shm_address
                        );
                m_pMappingView = Kernel32.INSTANCE.MapViewOfFile(
                                   m_hMapping,
                                   0x001f,//FILE_MAP_ALL_ACCESS
                                   0,
                                   0,
                                   (int)m_memory_size
                               );
            }
            catch(Exception ex) {
                rtcout.println(Logbuf.ERROR,"Open error  "+ex.toString() );
            }
        }
        else {
            address = SHARED_NAME+m_shm_address;
            RandomAccessFile file = null;
            try{
                file = new RandomAccessFile(address, "rw");
                file.setLength(m_memory_size);
                file.close();
            }
            catch(Exception ex) {
                rtcout.println(Logbuf.ERROR,"Open error  "+ex.toString() );
            }
            if(file != null){
                try{
                    file.close();
                }
                catch(Exception ex) {
                    rtcout.println(Logbuf.ERROR,"Close error  "+ex.toString() );
                }
            }
        }
/*
        try{
            RandomAccessFile file = new RandomAccessFile(address, "rw");
            file.setLength(m_memory_size);
        }
        catch(Exception ex) {
            rtcout.println(Logbuf.ERROR,"Open error  "+ex.toString() );
        }
*/
    }
    /**
     * 
     * {@.ja マッピングした共有メモリをアンマップする}
     * {@.en Close a shared memory.}
     *
     * @param unlink 
     *  {@.ja Linuxで/dev/shm以下に作成したファイルを削除する場合にTrueにする}
     *  {@.en }
    *
  # void close_memory(boolean unlink);
     */
    public void close_memory(boolean unlink){
        if(m_os.startsWith("windows")){
            Kernel32.INSTANCE.UnmapViewOfFile(m_pMappingView);
            Kernel32.INSTANCE.CloseHandle(m_hMapping);
        }
        else {
            File file = new File(SHARED_NAME+m_shm_address);
            file.delete();
            if(m_smInterface!=null){
                m_smInterface.close_memory(false);
            }
        }
    }  
    
    public void close_memory(){
        close_memory(false);
    }


  
    /**
     * 
     * {@.ja データを書き込む}
     * {@.en Writes in data.}
     * <p>
     * {@.ja 先頭8byteにデータサイズを書き込み、その後ろにデータを書き込む
     * 設定したデータサイズが共有メモリのサイズを上回った場合、
     * 共有メモリの初期化を行う}
     *
     * @param data 
     *  {@.ja 書き込むデータ} 
     *  {@.en data}
     *
     * # void write(cdrMemoryStream& data);
     */
    public void write(CdrDataHolder data){
        rtcout.println(Logbuf.TRACE, "write()");
        String address;
        if(m_os.startsWith("windows")){
            HANDLE fileHandle = new HANDLE(new Pointer(0xffffffff));
            WinNT.SECURITY_ATTRIBUTES psa = null;
            try{
                EncapsOutputStreamExt cdr 
                    = new EncapsOutputStreamExt(ORBUtil.getOrb(),m_endian);
                cdr.write_ulonglong(data.value.length);
                byte[] ch = cdr.getByteArray();

                m_pMappingView.write((long)0, ch, 0, ch.length);

                m_pMappingView.write((long)8, data.value, 0, data.value.length);
            }
            catch(Exception ex) {
                rtcout.println(Logbuf.ERROR,"write error  "+ex.toString() );
            }
        }
        else {
            address = SHARED_NAME+m_shm_address;
            RandomAccessFile file = null;
            try{
                file = new RandomAccessFile(address, "rw");
                FileChannel channel = file.getChannel();
                int length = (int)channel.size();
                MappedByteBuffer buffer
                    = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
                buffer.order(ByteOrder.LITTLE_ENDIAN);


                EncapsOutputStreamExt cdr 
                    = new EncapsOutputStreamExt(ORBUtil.getOrb(),m_endian);
                cdr.write_ulonglong(data.value.length);
                byte[] ch = cdr.getByteArray();
                buffer.put(ch, 0, ch.length);

                buffer.put(data.value);


                channel.close();
                file.close();
            }
            catch(Exception ex) {
                rtcout.println(Logbuf.ERROR,"write error  "+ex.toString() );
            }
            if(file != null){
                try{
                    file.close();
                }
                catch(Exception ex) {
                    rtcout.println(Logbuf.ERROR,"close error  "+ex.toString() );
                }
            }
        }
    }
    /**
     * 
     * {@.ja データを読み込む}
     * {@.en Read Data.}
     *
     * @param data
     *   {@.ja 読み込んだデータを受け取るためのオブジェクト}
     *   {@.en Readout data stored into the buffer.}
     *
  # void read(::OpenRTM::CdrData_out data);
     */
    public void read(CdrDataHolder data){
        rtcout.println(Logbuf.TRACE, "read()");
        String address;
        if(m_os.startsWith("windows")){
            address = m_shm_address;
            HANDLE fileHandle = new HANDLE(new Pointer(0xffffffff));
            WinNT.SECURITY_ATTRIBUTES psa = null;
            try{
                byte[] len_data = new byte[8];
                len_data = m_pMappingView.getByteArray(0,8);
                EncapsOutputStreamExt cdr 
                    = new EncapsOutputStreamExt(ORBUtil.getOrb(),m_endian);
                cdr.write_octet_array(len_data, 0, len_data.length);
                InputStream instream = cdr.create_input_stream();
	        long len = instream.read_ulonglong();
                data.value = new byte[(int)len];
                ByteBuffer buffer =  m_pMappingView.getByteBuffer(8, len);
                buffer.get(data.value);


            }
            catch(Exception ex) {
                rtcout.println(Logbuf.ERROR,"read error  "+ex.toString() );
            }
        }
        else {
            address = SHARED_NAME+m_shm_address;
            RandomAccessFile file = null;
            try {
                file = new RandomAccessFile(address, "rw");
                FileChannel channel = file.getChannel();
                int length = (int)channel.size();
                MappedByteBuffer buffer
                    = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                byte[] len_data = new byte[8];
                buffer.get(len_data,0,len_data.length);

                EncapsOutputStreamExt cdr 
                    = new EncapsOutputStreamExt(ORBUtil.getOrb(),m_endian);
                cdr.write_octet_array(len_data, 0, len_data.length);
                InputStream instream = cdr.create_input_stream();
	        long len = instream.read_ulonglong();
                data.value = new byte[(int)len];
                buffer.get(data.value);
                channel.close();
                file.close();
            }
            catch(Exception ex) {
                rtcout.println(Logbuf.ERROR,"read error  "+ex.toString() );
            }
            if(file != null){
                try {
                    file.close();
                }
                catch(Exception ex) {
                    rtcout.println(Logbuf.ERROR,"close error  "+ex.toString() );
                }
            }
        }

    }

    /**
     * 
     * {@.ja 通信先のCORBAインターフェースを登録する}
     * {@.en Registers CORBA interfaces.}
     *  <p>
     * {@.ja 登録する事により共有メモリの初期化したときに、
     * 通信先でもマッピングをやり直すことができる}
     *
     * @param sm
     *   {@.ja SharedMemoryのオブジェクトリファレンス}
     *   {@.en Object reference of shared momory}
  # void close(int memory_size, string shm_address);
     */
    public void setInterface(OpenRTM.PortSharedMemory sm){
        m_smInterface = sm;
    }
    /**
     * 
     * {@.ja エンディアンを設定する}
     * {@.en Sets an endian.}
     * 
     * @param isLittleEndian
     *   {@.ja true: little, false: big}
     *   {@.en true: little, false: big}
     * 
     * PortStatus setEndian();
     */
    public void setEndian(boolean isLittleEndian){
        m_endian = isLittleEndian;
        if(m_smInterface!=null){
            m_smInterface.setEndian(isLittleEndian);
        }
      
    }
    /**
     * 
     * {@.ja データの送信を知らせる}
     * {@.en Put data.}
  # PortStatus put();
     */
    public OpenRTM.PortStatus put(){
        return OpenRTM.PortStatus.UNKNOWN_ERROR;
    }

    /**
     * 
     * {@.ja データの送信を要求する}
     * {@.en Get data.}
  #
  # PortStatus get();
     */
    public OpenRTM.PortStatus get(){
        return OpenRTM.PortStatus.UNKNOWN_ERROR;
    }

    private Logbuf rtcout;
    private String m_shm_address = new String();
    //private int m_memory_size;
    private long m_memory_size;
    private OpenRTM.PortSharedMemory m_smInterface;
    private String m_os;
    private HANDLE m_hMapping;
    private Pointer m_pMappingView;
    protected boolean m_endian;
    
}

