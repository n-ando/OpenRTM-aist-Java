package jp.go.aist.rtm.RTC.buffer;

import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.Properties;

import jp.go.aist.rtm.RTC.buffer.ReturnCode;

  /**
   * {@.ja BufferBase インターフェース。}
   * {@.en BufferBase Interface.}
   * 
   * <p>
   * {@.ja 種々のバッファのための抽象インターフェースクラス。
   * 具象バッファクラスは、以下の純粋仮想関数の実装を提供しなければならない。
   * \<DataType\>としてバッファ内で保持するデータ型を指定する。
   *
   * publicインターフェースとして以下のものを提供する。<ul>
   * <li> length(): バッファの長さを返す
   * <li> length(n): バッファ長をnにセットする
   * <li> reset(): バッファのポインタをリセットする</li></ul>
   *
   * 書込み関連<ul>
   * <li> wptr(n=0): 現在の書き込み対象の要素のn個先のポインタを返す。
   * <li> advanceWptr(n=1): 書込みポインタをn進める。
   * <li> put(): 現在の書き込み位置に書き込む、ポインタは進めない。
   * <li> write(): バッファに書き込む。ポインタは1つすすむ。
   * <li> writable(): 書込み可能な要素数を返す。
   * <li> full(): バッファがフル状態。</li></ul>
   *
   * 読み出し関連<ul>
   * <li> rptr(n=0): 現在の読み出し対象のn個先のポインタを返す。
   * <li> advanceRptr(n=1): 読み出しポインタをn進める。
   * <li> get(): 現在の読み出し位置から読む。ポインタは進めない。
   * <li> read(): バッファから読み出す。ポインタは1つすすむ。
   * <li> readable(): 読み出し可能要素数を返す。
   * <li> empty(): バッファが空状態。</li></ul>}
   * {@.en This is the abstract interface class for various Buffer.
   * Concrete buffer classes must implement the following pure virtual
   * functions.
   * The users specify data type to hold it in a buffer as \<DataType\>.
   *
   * This class provides public interface as follows.<ul>
   * <li> write(): Write data into the buffer.
   * <li> read(): Read data from the buffer.
   * <li> length(): Get the buffer length.
   * <li> isFull(): Check on whether the buffer is full.
   * <li> isEmpty(): Check on whether the buffer is empty.</li></ul>
   *
   * This class provides protected interface as follows.<ul>
   * <li> put(): Store data into the buffer.
   * <li> get(): Get data from the buffer.</li></ul>}
   *
   *
   * @param <DataType>
   *   {@.ja バッファに格納するデータ型}
   *   {@.en DataType Data type to be stored to the buffer.}
   */
public interface BufferBase<DataType> {

    /**
     * {@.ja バッファの設定}
     * {@.en Set the buffer}
     *
     * @param prop
     *   {@.ja プロパティ}
     *   {@.en Properties}
     */
    public void init(final Properties prop);
    /**
     * {@.ja バッファの状態をリセットする。}
     * {@.en Reset the buffer status}
     * 
     * <p>
     * {@.ja バッファの読み出しポインタと書き込みポインタの位置をリセットする。}
     * {@.en Pure virtual function to reset the buffer status.}
     * 
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         NOT_SUPPORTED: リセット不可能
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK: Successful
     *         NOT_SUPPORTED: The buffer status cannot be reset.
     *         BUFFER_ERROR: Failed}
     */ 
    public ReturnCode reset();
    /**
     * {@.ja バッファの現在の書込み要素のポインタ。}
     * {@.en Get the writing pointer}
     * 
     * <p>
     * {@.ja バッファの現在の書込み要素のポインタまたは、n個先のポインタを返す}
     * {@.en Pure virtual function to get the writing pointer.}
     * 
     * @param  n 
     *   {@.ja 書込みポインタ + n の位置のポインタ}
     *   {@.en writeing pinter + n previous pointer}
     * @return 
     *   {@.ja 書込み位置のポインタ}
     *   {@.en writing pointer}
     */ 
    public DataType wptr(int n);
    /**
     * {@.ja バッファの現在の書込み要素のポインタ。}
     * {@.en Get the writing pointer}
     * 
     * <p>
     * {@.ja バッファの現在の書込む}
     * {@.en Pure virtual function to get the writing pointer.}
     * 
     * @return 
     *   {@.ja 書込み位置のポインタ}
     *   {@.en writing pointer}
     */ 
    public DataType wptr();
    /**
     * {@.ja 書込みポインタを進める。}
     * {@.en Forward n writing pointers.}
     * 
     * <p>
     * {@.ja 現在の書き込み位置のポインタを n 個進める。}
     * {@.en Pure virtual function to forward n writing pointers.}
     * 
     * @param  n 
     *   {@.ja 書込みポインタ + n の位置のポインタ}
     *   {@.en writeing pinter + n previous pointer}
     *
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK: Successful
     *         BUFFER_ERROR: Failed}
     */ 
    public ReturnCode advanceWptr(int n);
    /**
     * {@.ja 書込みポインタを進める。}
     * {@.en Forward n writing pointers.}
     * 
     * <p>
     * {@.ja 現在の書き込み位置のポインタを 1 個進める。}
     * {@.en Pure virtual function to forward 1 writing pointers.}
     * 
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK: Successful
     *         BUFFER_ERROR: Failed}
     */ 
    public ReturnCode advanceWptr();
    /**
     * {@.ja バッファに書込み可能な要素数。}
     * {@.en Get a writable number.}
     * 
     * <p>
     * {@.ja バッファに書込み可能な要素数を返す。}
     * {@.en Pure virtual function to get a writable number.}
     * 
     * @return 
     *   {@.ja 書き込み可能な要素数}
     *   {@.en value writable number}
     */
    public int writable();
    /**
     * {@.ja バッファfullチェック。}
     * {@.en Check on whether the buffer is full.}
     * 
     * <p>
     * {@.ja バッファfullチェック用純粋仮想関数}
     * {@.en Pure virtual function to check on whether the buffer is full.}
     *
     * @return 
     *   {@.ja fullチェック結果(true:バッファfull，false:バッファ空きあり)}
     *   {@.en True if the buffer is full, else false.}
          */
    public boolean full();
    /**
     * {@.ja バッファの現在の読み出し要素のポインタ。}
     * {@.en Get the reading pointer}
     * 
     * <p>
     * {@.ja バッファの現在の読み出し要素のポインタまたは、
     * n個先のポインタを返す}
     * {@.en Pure virtual function to get the reading pointer.}
     * 
     * @param  n 
     *   {@.ja 読み出しポインタ + n の位置のポインタ}
     *   {@.en reading pinter + n previous pointer}
     * @return 
     *   {@.ja 読み出し位置のポインタ}
     *   {@.en reading pointer}
     */ 
    public DataType rptr(int n);
    /**
     * {@.ja バッファの現在の読み出し要素のポインタ。}
     * {@.en Get the reading pointer}
     * 
     * <p>
     * {@.ja バッファの現在の読み出返す}
     * {@.en Pure virtual function to get the reading pointer.}
     * 
     * @return 
     *   {@.ja 読み出し位置のポインタ}
     *   {@.en reading pointer}
     */ 
    public DataType rptr();
    /**
     * {@.ja  読み出しポインタを進める。}
     * {@.en Forward n reading pointers.}
     * 
     * <p>
     * {@.ja 現在の読み出し位置のポインタを 1 個進める。}
     * {@.en Pure virtual function to forward 1 reading pointers.}
     * 
     *
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK: Successful
     *         BUFFER_ERROR: Failed}
     */ 
    public ReturnCode advanceRptr();
    /**
     * {@.ja  読み出しポインタを進める。}
     * {@.en Forward n reading pointers.}
     * 
     * <p>
     * {@.ja 現在の読み出し位置のポインタを n 個進める。}
     * {@.en Pure virtual function to forward n reading pointers.}
     * 
     * @param n 
     *   {@.ja 読み出しポインタ + n の位置のポインタ}
     *   {@.en reading pinter + n previous pointer}
     *
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK: Successful
     *         BUFFER_ERROR: Failed}
     */ 
    public ReturnCode advanceRptr(int n);
    /**
     * {@.ja バッファから読み出し可能な要素数。}
     * {@.en Write data into the buffer}
     * 
     * <p>
     * {@.ja バッファから読み出し可能な要素数を返す。}
     * {@.en Pure virtual function to get a reading number.}
     * 
     * @return 
     *   {@.ja 読み出し可能な要素数}
     *   {@.en readable number}
     */
    public int readable();
    /**
     * {@.ja バッファemptyチェック。}
     * {@.en Check on whether the buffer is empty.}
     * 
     * <p>
     * {@.ja バッファemptyチェック用純粋仮想関数}
     * {@.en Pure virtual function to check on whether the buffer is empty.}
     *
     * @return 
     *   {@.ja emptyチェック結果(true:バッファempty，false:バッファデータあり)}
     *   {@.en True if the buffer is empty, else false.}
     *
     */
    public boolean empty();


    /**
     * {@.ja バッファの長さを取得する。}
     * {@.en Get the buffer length}
     * 
     * <p>
     * {@.ja バッファ長を取得するための純粋仮想関数}
     * {@.en Pure virtual function to get the buffer length.}
     * 
     * @return 
     *   {@.ja バッファ長}
     *   {@.en Buffer length}
     * 
     */
    public int length();
    /**
     * {@.ja バッファの長さをセットする。}
     * {@.en Set the buffer length}
     * 
     * <p>
     * {@.ja バッファ長を設定する。設定不可な場合はNOT_SUPPORTEDが返る。}
     * {@.en Pure virtual function to set the buffer length.}
     * 
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         NOT_SUPPORTED: バッファ長変更不可
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK: Successful
     *         NOT_SUPPORTED: The buffer length cannot be set.
     *         BUFFER_ERROR: Failed}
     */    
    public ReturnCode length(int n);

    /**
     * {@.ja バッファにデータを書き込む。}
     * {@.en Write data into the buffer}
     * 
     * <p>
     * {@.ja バッファにデータを書き込む。書き込みポインタの位置は1つすすむ。}
     * {@.en Pure virtual function to write data into the buffer.}
     * 
     * @param value 
     *   {@.ja 書き込み対象データ}
     *   {@.en value Target data to write.}
     *
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK: Successful
     *         BUFFER_ERROR: Failed}
     */
    public ReturnCode write(final DataType value);
    /**
     * {@.ja バッファにデータを書き込む。}
     * {@.en Write data into the buffer}
     * 
     * <p>
     * {@.ja バッファにデータを書き込む。書き込みポインタの位置は1つすすむ。}
     * {@.en Pure virtual function to write data into the buffer.}
     * 
     * @param value 
     *   {@.ja 書き込み対象データ}
     *   {@.en value Target data to write.}
     * @param sec 
     *   {@.ja タイムアウト時間 sec  (default -1: 無効)}
     *   {@.en TimeOut sec order}
     * @param nsec 
     *   {@.ja タイムアウト時間 nsec (default 0)}
     *   {@.en TimeOut nsec order}
     *
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK: Successful
     *         BUFFER_ERROR: Failed}
     */
    public ReturnCode write(final DataType value,
                            int sec, int nsec);


    /**
     * {@.ja バッファからデータを読み出す。}
     * {@.en Read data from the buffer}
     * 
     * <p>
     * {@.ja バッファからデータを読み出すための純粋仮想関数}
     * {@.en Pure virtual function to read data from the buffer.}
     * 
     * @param valueRef 
     *   {@.ja 読み込んだデータを受け取るためのDataRefオブジェクト}
     *   {@.en Readout data stored into the buffer.}
     *
     * @return 
     *   {@.ja データ読み出し結果}
     *   {@.en Result of having read}
     *
     */
    public ReturnCode read(DataRef<DataType> valueRef);
    /**
     * {@.ja バッファからデータを読み出す。}
     * {@.en Read data from the buffer}
     * 
     * <p>
     * {@.ja バッファからデータを読み出すための純粋仮想関数}
     * {@.en Pure virtual function to read data from the buffer.}
     * 
     * @param valueRef 
     *   {@.ja 読み込んだデータを受け取るためのDataRefオブジェクト}
     *   {@.en Readout data stored into the buffer.}
     * @param sec 
     *   {@.ja タイムアウト時間 sec  (default -1: 無効)}
     *   {@.en TimeOut sec order}
     * @param nsec 
     *   {@.ja タイムアウト時間 nsec (default 0)}
     *   {@.en TimeOut nsec order}
     *
     * @return 
     *   {@.ja データ読み出し結果}
     *   {@.en Result of having read}
     *
     */
    public ReturnCode read(DataRef<DataType> valueRef, int sec, int nsec);
    /**
     * {@.ja バッファにデータを書き込む。}
     * {@.en Write data into the buffer}
     * 
     * <p>
     * {@.ja バッファにデータを書き込む。書き込みポインタの位置は変更されない。}
     * {@.en Pure virtual function to write data into the buffer.}
     * 
     * @param data 
     *   {@.ja 書き込むデータ}
     *   {@.en value Target data to write.}
     *
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK: Successful
     *         BUFFER_ERROR: Failed}
     */
    public ReturnCode put(final DataType data);

    /**
     * {@.ja バッファからデータを読み出す。}
     * {@.en Read data from the buffer}
     * 
     * <p>
     * {@.ja バッファからデータを読みだす。読み出しポインタの位置は
     * 変更されない。}
     * {@.en Pure virtual function to read data form the buffer.}
     * 
     * @return 
     *   {@.ja 読み込んだデータ}
     *   {@.en value Data to read.}
     */
    public DataType get();
    /**
     * {@.ja バッファからデータを読み出す。}
     * {@.en Read data from the buffer}
     * 
     * <p>
     * {@.ja バッファからデータを読みだす。読み出しポインタの位置は
     * 変更されない。}
     * {@.en Pure virtual function to read data form the buffer.}
     * 
     * @param value 
     *   {@.ja 読み出しデータ}
     *   {@.en value Data to read.}
     *
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK: Successful
     *         BUFFER_ERROR: Failed}
     */
    public ReturnCode get(DataRef<DataType> value);

}
