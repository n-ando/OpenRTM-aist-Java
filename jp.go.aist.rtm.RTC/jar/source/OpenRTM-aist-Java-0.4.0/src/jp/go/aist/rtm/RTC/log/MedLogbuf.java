package jp.go.aist.rtm.RTC.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * <p>仲介ロガーバッファクラス<br />
 *
 * LogBuf への仲介をします。
 * 受け取った文字列にヘッダなどを付加し Logbuf へ渡します。</p>
 */
public class MedLogbuf{

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public MedLogbuf(){
    }
    
    /**
     * <p>コンストラクタです。</p>
     * 
     * @param logbuf　出力先ログバッファ
     */
    public MedLogbuf(Logbuf logbuf){
        m_logbuf = logbuf;
    }
    
    /**
     * <p>ヘッダに付加する日時フォーマットを指定します。<br />
     *
     * フォーマット指定文字列は以下のとおり。<p>
     * 
     * <ul>
     * <li>時刻
     * <li>'H'  24 時間制の時。必要に応じて 0 を先頭に追加し、2 桁で表現します (00 - 23)。</li>  
     * <li>'I'  12 時間制の時。必要に応じて 0 を先頭に追加し、2 桁で表現します (00 - 12)。</li>
     * <li>'k'  24 時間制の時 (0 - 23)</li>  
     * <li>'l'  12 時間制の時 (1 - 12)</li>  
     * <li>'M'  分。必要に応じて 0 を先頭に追加し、2 桁で表現します (00 - 59)。</li>  
     * <li>'S'  秒。必要に応じて 0 を先頭に追加し、2 桁で表現します (00 - 60)。「60」はうるう年での秒のサポートに必要な特殊な値です。</li>  
     * <li>'L'  ミリ秒。必要に応じて 0 を先頭に追加し、3 桁で表現します (000 - 999)。</li>  
     * <li>'N'  ナノ秒。必要に応じて 0 を先頭に追加し、9 桁で表現します (000000000 - 999999999)。</li>  
     * <li>'p'  午前または午後を表すロケール固有の小文字のマーカ (例、「am」や「pm」)。変換接頭辞の T を使用すると、結果は大文字で強制出力されます。</li>  
     * <li>'z'  RFC 822 に準拠した、GMT からの数値タイムゾーンオフセット (例、-0800)</li>  
     * <li>'Z'  タイムゾーンの省略形を表す文字列。Formatter のロケールは、引数のロケール (存在する場合) よりも優先されます。</li>  
     * <li>'s'  1970 年 1 月 1 日 00:00:00 UTC のエポック開始からの秒 (Long.MIN_VALUE/1000 から Long.MAX_VALUE/1000 まで)</li>  
     * <li>'Q'  1970 年 1 月 1 日 00:00:00 UTC のエポック開始からのミリ秒 (Long.MIN_VALUE から Long.MAX_VALUE まで)</li>  
     * </li>
     *
     * <li>日付 
     * <li>'B'  ロケール固有の月の完全な名前 (例、「January」、「February」)</li>  
     * <li>'b'  ロケール固有の月の省略名 (例、「Jan」、「Feb」)</li>  
     * <li>'h'  'b' と同じ</li>  
     * <li>'A'  ロケール固有の曜日の完全な名前 (例、「Sunday」、「Monday」)</li>  
     * <li>'a'  ロケール固有の曜日の短縮名 (例、「Sun」、「Mon」)</li>  
     * <li>'C'  4 桁の年を 100 で割った値。必要に応じて 0 を先頭に追加し、2 桁で表示します (00 - 99)。</li>  
     * <li>'Y'  年。必要に応じて 0 を先頭に追加し、4 桁以上で表現します。たとえば、0092 は、グレゴリオ歴の 92 CE と等価です。</li>  
     * <li>'y'  年の下 2 桁。必要に応じて 0 を先頭に追加します (00 - 99)。</li>  
     * <li>'j'  年の何日目かを表す日。必要に応じて 0 を先頭に追加し、3 桁で表現します。たとえば、グレゴリオ歴の場合、001 - 366 になります。</li>  
     * <li>'m'  月。必要に応じて 0 を先頭に追加し、2 桁で表現します (01 - 13)。</li>  
     * <li>'d'  月の何日目かを表す日。必要に応じて 0 を先頭に追加し、2 桁で表現します (01 -31)。</li>  
     * <li>'e'  月の何日目かを表す日。最大 2 桁で表現します (1 - 31)。</li>  
     * </li>
     *
     * <li>一般の日付/時刻変換
     * <li>'R'  「%tH:%tM」として 24 時間制で書式設定された時刻</li>  
     * <li>'T'  「%tH:%tM:%tS」として 24 時間制で書式設定された時刻</li>  
     * <li>'r'  「%tI:%tM:%tS %Tp」として 12 時間制で書式設定された時刻。午前および午後マーカ (%Tp) の位置は、ロケールにより異なります。</li>  
     * <li>'D'  「%tm/%td/%ty」として書式設定された日付</li>  
     * <li>'F'  「%tY-%tm-%td」として書式設定された、ISO 8601 に準拠した日付</li>  
     * <li>'c'  「%ta %tb %td %tT %tZ %tY」として書式設定された日付および時刻 (例、「Sun Jul 20 16:17:00 EDT 1969」)</li>
     * </li>
     * </ul>  
     * 
     * @param fmt　日付フォーマット文字列
     */
    public void setDateFmt(final String fmt) {
        m_DateFmt = fmt;
    }
    
    /**
     * <p>ヘッダに付加する日時フォーマットを取得します。</p>
     * 
     * @return 日時フォーマット文字列
     */
   public String getDateFmt() {
       return m_DateFmt;
   }

   /**
     * <p>ヘッダの日時の後に付加する文字列を設定します。</p>
     * 
     * @param suffix　ヘッダ文字列
     *
     */
    public void setSuffix(final String suffix) {
        m_Suffix = suffix;
    }

    /**
     * <p>ヘッダの日時の後に付加する文字列を取得します。</p>
     * 
     * @return ヘッダ文字列
     */
   public String getSuffix() {
       return m_Suffix;
   }

   /**
    * <p>ログに出力します。</p>
    * 
    * @return ログ内容
    */
   public void log(String string) {
       SimpleDateFormat formatter = new SimpleDateFormat(m_DateFmt);
       formatter.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
       m_logbuf.log(formatter.format(new Date()) + " " + m_Suffix + " " + string);
   }
    /**
     * <p>ログ出力先</p>
     */
    private Logbuf m_logbuf;
    /**
     * <p>ログに付加する日付形式の書式</p>
     */
    private String m_DateFmt = "yyyy/MM/dd HH:mm:ss";
    /**
     * <p>日付の後に付加するヘッダ</p>
     */
    private String m_Suffix = " ";
}
