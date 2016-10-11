package jp.go.aist.rtm.RTC.port;

import java.lang.Long;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import OpenRTM.CdrDataHolder;
import OpenRTM.PortSharedMemory;

import jp.go.aist.rtm.RTC.log.Logbuf;

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


public abstract class SharedMemory implements PortSharedMemory {
    private static final int DEFAULT_SIZE = 8;
    private static final int DEFAULT_MEMORY_SIZE = 2*1024*1024;


    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     */
    public SharedMemory(){
        rtcout = new Logbuf("SharedMemory");
        m_memory_size = DEFAULT_MEMORY_SIZE;

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
            return (1024  * 1024 * Long.parseLong(str.split("M")[0]));
        }
        else if(str.indexOf('K')>0){
            return (1024 * Long.parseLong(str.split("K")[0]));
        }
        return (Long.parseLong(str));
/*
    memory_size = SharedMemory.default_memory_size
    if size_str:
      if size_str[-1] == "M":
        memory_size = 1024 * 1024 * int(size_str[0:-1])
      elif size_str[-1] == "k":
        memory_size = 1024 * int(size_str[0:-1])
      else:
        memory_size = int(size_str[0:-1])
    return memory_size
*/
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
    public void create_memory (int memory_size, String shm_address){
        rtcout.println(Logbuf.TRACE, 
                "create():memory_size="
                + memory_size +",shm_address=" + shm_address);
        m_memory_size = memory_size;
        m_shm_address = shm_address;
        
/*
    
    if self._shmem is None:
      self._rtcout.RTC_TRACE("create():memory_size="+str(memory_size)+",shm_address="+str(shm_address))
      self._memory_size = memory_size
      self._shm_address = shm_address

      if platform.system() == "Windows":
        self._shmem = mmap.mmap(0, self._memory_size, self._shm_address, mmap.ACCESS_WRITE)
      else:
        O_RDWR = 2
        O_CREAT = 64

        S_IRUSR = 256
        S_IWUSR = 128
        S_IRGRP = 32
        S_IWGRP = 16
        S_IROTH = 4

        self.fd = self.rt.shm_open(self._shm_address,O_RDWR | O_CREAT,S_IRUSR|S_IWUSR|S_IRGRP|S_IWGRP|S_IROTH)
        if self.fd < 0:
          return self.UNKNOWN_ERROR
        self.rt.ftruncate(self.fd, self._memory_size)
        self._shmem = mmap.mmap(self.fd, self._memory_size, mmap.MAP_SHARED)
        self.rt.close( self.fd )

      
      if self._smInterface:
        self._smInterface.open_memory(self._memory_size, self._shm_address)
*/

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
    public void open_memory (int memory_size, String shm_address){
        rtcout.println(Logbuf.TRACE, 
                "open():memory_size="
                + memory_size +",shm_address=" + shm_address);
        m_memory_size = memory_size;
        m_shm_address = shm_address;
        try{
            RandomAccessFile file = new RandomAccessFile(m_shm_address, "rw");
            file.setLength(m_memory_size);
        }
        catch(Exception ex) {
            rtcout.println(Logbuf.ERROR,"Open error  "+ex.toString() );
        }
/*
    self._rtcout.RTC_TRACE("open():memory_size="+str(memory_size)+",shm_address="+str(shm_address))
    self._memory_size = memory_size
    self._shm_address = shm_address
    if self._shmem is None:
      if platform.system() == "Windows":
        self._shmem = mmap.mmap(0, self._memory_size, self._shm_address, mmap.ACCESS_READ)
      else:
        O_RDWR = 2
        self.fd = self.rt.shm_open(self._shm_address,O_RDWR,0)
        if self.fd < 0:
          return self.UNKNOWN_ERROR
        self.rt.ftruncate(self.fd, self._memory_size)
        self._shmem = mmap.mmap(self.fd, self._memory_size, mmap.MAP_SHARED)
        self.rt.close( self.fd )
    
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
        File file = new File(m_shm_address);
        file.delete();
/*
    self._rtcout.RTC_TRACE("open()")
    if self._shmem:
      self._shmem.close()
      if platform.system() == "Windows":
        pass
      else:
        if unlink:
           self.rt.shm_unlink(self._shm_address)
      self._shmem = None

      if self._smInterface:
        self._smInterface.close_memory(False)
*/
    }  
    
    public void close_memory(){
        close_memory(false);
    }


  
    /**
     * 
     * {@.ja データを書き込む}
     * {@.en Wwrites in data.}
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
        try{
            RandomAccessFile file = new RandomAccessFile(m_shm_address, "rw");
            FileChannel channel = file.getChannel();
            int length = (int)channel.size();
            MappedByteBuffer buffer
                = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
/*
            OutPort out = (OutPort)m_outport;
            OutputStream cdr 
                = new EncapsOutputStreamExt(m_orb,m_isLittleEndian);
            out.write_stream(data,cdr);
*/

//            buffer.putInt((offset * 4/* size of int */), value);


            channel.close();
            file.close();
        }
        catch(Exception ex) {
            rtcout.println(Logbuf.ERROR,"write error  "+ex.toString() );
        }
/*
    self._rtcout.RTC_TRACE("write()")
    
    if self._shmem:
      data_size = len(data)

      
      if data_size + SharedMemory.default_size > self._memory_size:
        self._memory_size = data_size + SharedMemory.default_size

        if self._smInterface:
          self._smInterface.close_memory(False)


        self.close_memory(True)
        self.create_memory(self._memory_size, self._shm_address)

        
        
      data_size_cdr = cdrMarshal(CORBA.TC_ulong, data_size)
      
      self._shmem.seek(os.SEEK_SET)
      self._shmem.write(data_size_cdr)
      self._shmem.write(data)
*/
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
        try {
            RandomAccessFile file = new RandomAccessFile(m_shm_address, "rw");
            FileChannel channel = file.getChannel();
            int length = (int)channel.size();
            MappedByteBuffer buffer
                = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            //int value = buffer.getInt(offset * 4/* size of int */);
            channel.close();
            file.close();
        }
        catch(Exception ex) {
            rtcout.println(Logbuf.ERROR,"read error  "+ex.toString() );
        }

/*
    self._rtcout.RTC_TRACE("read()")
    if self._shmem:
      
      self._shmem.seek(os.SEEK_SET)
      
      data_size_cdr = self._shmem.read(SharedMemory.default_size)
      data_size = cdrUnmarshal(CORBA.TC_ulong, data_size_cdr)
      
      
      
      shm_data = self._shmem.read(data_size)
      
      return shm_data
    return ""
*/
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
    public void setInterface (OpenRTM.PortSharedMemory sm){
        //self._smInterface = sm
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
    private int m_memory_size;
    
}
