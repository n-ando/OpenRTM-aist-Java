package jp.go.aist.rtm.RTC.buffer;

import jp.go.aist.rtm.RTC.util.DataRef;

/**
 * <p>バッファのインタフェースです。</p>
 *
 * @param <DataType> バッファ内のデータ型を指定します。
 */
public interface BufferBase<DataType> {

    /**
     * <p>バッファ長を取得します。</p>
     * 
     * @return バッファ長
     */
    public int length();

    /**
     * <p>バッファにデータを書き込みます。</p>
     * 
     * @param value 書き込むデータ
     * @return 書き込みに成功した場合はtrueを、さもなくばfalseを返します。
     */
    public boolean write(final DataType value);

    /**
     * <p>バッファからデータを読み込みます。</p>
     * 
     * @param valueRef 読み込んだデータを受け取るためのDataRefオブジェクト
     * @return 読み込みに成功した場合はtrueを、さもなくばfalseを返します。
     */
    public boolean read(DataRef<DataType> valueRef);

    /**
     * <p>バッファがフルかどうか判定します。</p>
     * 
     * @return バッファがフルならばtrue、さもなくばfalseを返します。
     */
    public boolean isFull();

    /**
     * <p>バッファが空かどうか判定します。</p>
     * 
     * @return バッファが空ならばtrue、さもなくばfalseを返します。
     */
    public boolean isEmpty();

    /**
     * <p>バッファにデータを書き込みます。</p>
     * 
     * @param data 書き込むデータ
     */
    public void put(final DataType data);

    /**
     * <p>バッファからデータを読み込みます。</p>
     * 
     * @return 読み込んだデータ
     */
    public DataType get();

    /**
     * <p>バッファ中に、まだ読み取られていないデータがあるかどうか判定します。</p>
     * 
     * @return 読み取られていないデータがあればtrueを、さもなくばfalseを返します。
     */
    public boolean isNew();
}
