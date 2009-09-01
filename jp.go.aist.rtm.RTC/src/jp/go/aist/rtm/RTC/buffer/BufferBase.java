package jp.go.aist.rtm.RTC.buffer;

import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.Properties;

import jp.go.aist.rtm.RTC.buffer.ReturnCode;

/**
 * <p>バッファのインタフェースです。</p>
 *
 * @param <DataType> バッファ内のデータ型を指定します。
 */
public interface BufferBase<DataType> {

    /**
     * <p> init </p>
     *
     * @param prop
     */
    public void init(final Properties prop);
    /**
     * <p> reset </p>
     *
     * @return ReturnCode
     */
    public ReturnCode reset();
    /**
     * <p> wptr </p>
     *
     * @param  n 
     * @return DataType
     */
    public DataType wptr(int n);
    /**
     * <p> wptr </p>
     *
     * @return DataType
     */
    public DataType wptr();
    /**
     * <p> advanceWptr </p>
     *
     * @param  n
     * @return ReturnCode
     */
    public ReturnCode advanceWptr(int n);
    /**
     * <p> advanceWptr </p>
     *
     * @return ReturnCode
     */
    public ReturnCode advanceWptr();
    /**
     * <p> writable </p>
     *
     * @return size_t
     */
    public int writable();
    /**
     * <p> full </p>
     * Check on whether the buffer is full
     *
     * @return boolean 
     */
    public boolean full();
    /**
     * <p> rptr </p>
     *
     * @param  n
     * @return DataType 
     */
    public DataType rptr(int n);
    /**
     * <p> rptr </p>
     *
     * @return DataType 
     */
    public DataType rptr();
    /**
     * <p> advanceRptr </p>
     *
     * @return ReturnCode 
     */
    public ReturnCode advanceRptr();
    /**
     * <p> advanceRptr </p>
     *
     * @param  n 
     * @return ReturnCode 
     */
    public ReturnCode advanceRptr(int n);
    /**
     * <p> readable </p>
     *
     * @return size_t 
     */
    public int readable();
    /**
     * <p> empty </p>
     * Check on whether the buffer is empty.
     *
     * @return boolean 
     */
    public boolean empty();


    /**
     * <p>バッファ長を取得します。</p>
     * 
     * @return バッファ長
     */
    public int length();
    public ReturnCode length(int n);

    /**
     * <p>バッファにデータを書き込みます。</p>
     * 
     * @param value 書き込むデータ
     * @return 書き込みに成功した場合はtrueを、さもなくばfalseを返します。
     */
    public ReturnCode write(final DataType value);
    public ReturnCode write(final DataType value,
                             long sec, long nsec);


    /**
     * <p>バッファからデータを読み込みます。</p>
     * 
     * @param valueRef 読み込んだデータを受け取るためのDataRefオブジェクト
     * @return 読み込みに成功した場合はtrueを、さもなくばfalseを返します。
     */
    public boolean read(DataRef<DataType> valueRef);
    /**
     * <p>バッファにデータを書き込みます。</p>
     * 
     * @param data 書き込むデータ
     * @return ReturnCode
     */
    public ReturnCode put(final DataType data);

    /**
     * <p>バッファからデータを読み込みます。</p>
     * 
     * @return 読み込んだデータ
     */
    public DataType get();
    public ReturnCode get(DataType value);

}
