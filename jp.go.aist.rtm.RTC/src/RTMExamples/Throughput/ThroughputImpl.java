package RTMExamples.Throughput;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.port.ConnectorBase;
import jp.go.aist.rtm.RTC.port.ConnectorListener;
import jp.go.aist.rtm.RTC.port.ConnectorDataListener;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerT;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerType;
import jp.go.aist.rtm.RTC.port.ConnectorListenerType;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.util.clock.ClockManager;
import jp.go.aist.rtm.RTC.util.DoubleHolder;
import jp.go.aist.rtm.RTC.util.IntegerHolder;
import jp.go.aist.rtm.RTC.util.StringHolder;
import RTC.ReturnCode_t;
import RTC.TimedLong;
import RTC.TimedDoubleSeq;
import RTC.TimedFloatSeq;
import RTC.TimedLongSeq;
import RTC.TimedShortSeq;
import RTC.TimedOctetSeq;

import org.omg.CORBA.portable.OutputStream;

/**
 * {@.ja Clock Throughput Component}
 * <p>
 * {@.ja 時間計測用RTC
 *
 * データポートのスループットを計測するコンポーネント。interface_type,
 * subscription_type 等 ConnectorProfile パラメータやデータサイズ、サン
 * プル数などを変更して、その際の転送時間（最大、最小、平均、標準偏差）
 * およびスループットを測定してファイルに記録することができる。
 *
 * 基本的には、以下の(a)や(b)のような接続形態で使用する。
 * <pre>
 *  +-----------+
 *  |  ______   |    ______     ______
 *  +->|_____|>-+   >|_____|>-->|_____|>
 *       (a)                 (b)
 * </pre>
 * 同一コンポーネント内では(a)、同一プロセス内、同一ノード内のスループッ
 * トは (a)または(b)、異なるノード間のスループットを計測する際は (b)の
 * 接続形態で計測する。計測は以下の手順で行う。
 *
 * <ul>
 * <li># コンポーネントを起動する
 * <li># コンフィグレーションパラメータを設定する
 * <li># 必要なコネクタプロファイルを設定してポートを接続する
 * <li># コンポーネントをアクティベートする
 * </ul>
 *
 * 計測結果はデータを受け取ったコンポーネントがファイルに記録する。
 *
 * * コンフィギュレーションパラメータ
 * <ul>
 * <li> mode: 計測モード名。logincr, incr, const から選択可能。
 *  - logincr: logスケールでデータ数を増加させ計測。実際には、1, 2, 5,
 *             10, .. といった間隔でデータ数を増加させ、logスケールでプ
 *             ロットした際にほぼ等間隔となるように計測する。
 *  - incr: incrementパラメータで指定されたバイト数で、一定間隔でデータ
 *             数を増加させる。
 *  - const: データは増加させず一定サイズでスループットを計測する。
 *
 * <li> outputfile: 出力ファイル名。onActivated時、またはデータ受信時にファ
 *               イルがオープンされるので、それ以降にパラメータを設定し
 *               た場合は反映されない。
 * <li> increment: データ増分。
 *                 mode が incr の場合のデータ増分を byte で指定する。
 *
 * <li> maxsize: 最大データ個数を指定する。送信するシーケンスデータのサイ
 *            ズを指定する。実際のデータサイズは、この個数に1データ当た
 *            りのバイト数をかけたものとなる。
 * <li> maxsend: 最大送信数。データ送信回数の最大値を指定する。モードが
 *            logincr, incr の場合、データサイズ毎に maxsend 回数データ
 *            を送信する。
 * <li> maxsample: 最大サンプリング数。データを受信し、統計情報を計算する
 *            際の最大サンプル数を指定する。データ送信側の送信数がサン
 *            プル数より少ない場合、受信したサンプル数で統計情報を計算
 *            する。データ送信側の送信数がサンプル数より多い場合、古い
 *            情報は破棄され、最新の maxsample 個の計測データから統計情
 *            報を計算する。
 * </ul>}
 *
 *
 */
public class ThroughputImpl extends DataFlowComponentBase {

  public ThroughputImpl(Manager manager) {
    super(manager);

    // initialize of configuration-data.
    // <rtc-template block="init_conf_param">

    //データ型
    // - Name: datatype datatype
    // - DefaultValue: double
    // - Constraint: (octet,short,long,float,double)
    dataType = new StringHolder("double");

    //出力ファイル名。
    //onActivated時、またはデータ受信時にファイルがオープンされるので、
    //それ以降にパラメータを設定した場合は反映されない。
    // - Name: outputfile outputfile
    // - DefaultValue: test.dat
    outputFile = new StringHolder("test.dat");

    //データ増分。mode が incr の場合のデータ増分を byte で指定する。
    // - Name: increment increment
    // - DefaultValue: 100
    // - Unit: byte
    increment = new IntegerHolder(100);

    //onExecute内で待機する時間
    // - Name: sleep_time sleep_time
    // - DefaultValue: 0.1
    // - Unit: s
    sleepTime = new DoubleHolder(0.1);

    // - mode: 計測モード名。logincr, incr, const から選択可能。
    // - logincr: logスケールでデータ数を増加させ計測。
    //            実際には、1, 2, 5, 10, .. といった間隔でデータ数を増加させ、
    //            logスケールでプロットした際にほぼ等間隔となるように計測する。
    // - incr: incrementパラメータで指定されたバイト数で、一定間隔でデータ
    //         数を増加させる。
    // - const: データは増加させず一定サイズでスループットを計測する。
    // - Name: mode mode
    // - DefaultValue: logincr
    // - Constraint: (logincr,incr,const)
    mode = new StringHolder("logincr");

    //最大データ個数を指定する。
    //送信するシーケンスデータのサイズを指定する。
    //実際のデータサイズは、この個数に1データ当たりのバイト数を
    //かけたものとなる。
    // - Name: maxsize maxsize
    // - DefaultValue: 1000000
    maxSize = new IntegerHolder(1000000);

    //最大送信数。データ送信回数の最大値を指定する。
    //モードがlogincr, incrの場合、データサイズ毎に maxsend 回数データを
    //送信する。
    // - Name: maxsend maxsend
    // - DefaultValue: 1000
    maxSend = new IntegerHolder(1000);

    //最大サンプリング数。
    //データを受信し、統計情報を計算する際の最大サンプル数を指定する。
    //データ送信側の送信数がサンプル数より少ない場合、
    //受信したサンプル数で統計情報を計算する。
    //データ送信側の送信数がサンプル数より多い場合、古い情報は破棄され、
    //最新の maxsample 個の計測データから統計情報を計算する。
    // - Name: maxsample maxsample
    // - DefaultValue: 100
    maxSample = new IntegerHolder(100);

    //ファイル識別子
    // - Name: filesuffix filesuffix
    // - DefaultValue: -test
    fileSuffix = new StringHolder("-test");

    // </rtc-template>
    dataSize = 0;
    sendCount = 0;
    logMulCnt = 0;
    varSize = 0;


    inOctetRef = new DataRef<TimedOctetSeq>(inOctet);
    inOctetIn = new InPort<TimedOctetSeq>("in", inOctetRef);

    outOctetRef = new DataRef<TimedOctetSeq>(outOctet);
    outOctetOut = new OutPort<TimedOctetSeq>("out", outOctetRef);

    inShortRef = new DataRef<TimedShortSeq>(inShort);
    inShortIn = new InPort<TimedShortSeq>("in", inShortRef);

    outShortRef = new DataRef<TimedShortSeq>(outShort);
    outShortOut = new OutPort<TimedShortSeq>("out", outShortRef);

    inLongRef = new DataRef<TimedLongSeq>(inLong);
    inLongIn = new InPort<TimedLongSeq>("in", inLongRef);

    outLongRef = new DataRef<TimedLongSeq>(outLong);
    outLongOut = new OutPort<TimedLongSeq>("out", outLongRef);

    inFloatRef = new DataRef<TimedFloatSeq>(inFloat);
    inFloatIn = new InPort<TimedFloatSeq>("in", inFloatRef);

    outFloatRef = new DataRef<TimedFloatSeq>(outFloat);
    outFloatOut = new OutPort<TimedFloatSeq>("out", outFloatRef);

    inDoubleRef = new DataRef<TimedDoubleSeq>(inDouble);
    inDoubleIn = new InPort<TimedDoubleSeq>("in", inDoubleRef);

    outDoubleRef = new DataRef<TimedDoubleSeq>(outDouble);
    outDoubleOut = new OutPort<TimedDoubleSeq>("out", outDoubleRef);


  }

  // The initialize action (on CREATED->ALIVE transition)
  // formaer rtc_init_entry() 
  @Override
  protected ReturnCode_t onInitialize() {
    // Bind variables and configuration variable
    bindParameter("datatype", dataType, "double");
    bindParameter("outputfile", outputFile, "test.dat");
    bindParameter("increment", increment, "100");
    bindParameter("sleep_time", sleepTime, "0.1");
    bindParameter("mode", mode, "logincr");
    bindParameter("maxsize", maxSize, "1000000");
    bindParameter("maxsend", maxSend, "1000");
    bindParameter("maxsample", maxSample, "100");
    bindParameter("filesuffix", fileSuffix, "-test");

    String type = dataType.toString().trim().toLowerCase();
    try {
      if(type.equals("octet")){
        addInPort("in", inOctetIn);
        inOctetIn.addConnectorDataListener(
            ConnectorDataListenerType.ON_BUFFER_WRITE,
            new DataListener(this, inOctetRef.v.getClass()));
        inOctetIn.addConnectorListener(
            ConnectorListenerType.ON_CONNECT, new Listener(this));
        addOutPort("out", outOctetOut);
        varSize = Byte.SIZE;
      } else if(type.equals("short")){
        addInPort("in", inShortIn);
        inShortIn.addConnectorDataListener(
            ConnectorDataListenerType.ON_BUFFER_WRITE,
            new DataListener(this, inShortRef.v.getClass()));
        inShortIn.addConnectorListener(
            ConnectorListenerType.ON_CONNECT, new Listener(this));
        addOutPort("out", outShortOut);
        varSize = Short.SIZE;
      } else if(type.equals("long")){
        addInPort("in", inLongIn);
        inLongIn.addConnectorDataListener(
            ConnectorDataListenerType.ON_BUFFER_WRITE,
            new DataListener(this, inLongRef.v.getClass()));
        inLongIn.addConnectorListener(
            ConnectorListenerType.ON_CONNECT, new Listener(this));
        addOutPort("out", outLongOut);
        varSize = Integer.SIZE;
      } else if(type.equals("float")){
        addInPort("in", inFloatIn);
        inFloatIn.addConnectorDataListener(
            ConnectorDataListenerType.ON_BUFFER_WRITE,
            new DataListener(this, inFloatRef.v.getClass()));
        inFloatIn.addConnectorListener(
            ConnectorListenerType.ON_CONNECT, new Listener(this));
        addOutPort("out", outFloatOut);
        varSize = Float.SIZE;
      } else if(type.equals("double")){
        addInPort("in", inDoubleIn);
        inDoubleIn.addConnectorDataListener(
            ConnectorDataListenerType.ON_BUFFER_WRITE,
            new DataListener(this, inDoubleRef.v.getClass()));
        inDoubleIn.addConnectorListener(
            ConnectorListenerType.ON_CONNECT, new Listener(this));
        addOutPort("out", outDoubleOut);
        varSize = Double.SIZE;
      } else {
        return ReturnCode_t.RTC_OK;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    //System.out.println("varsize: "+varSize);

    for(int ic=0;ic<maxSample.getValue();++ic){
      record.add(new RTC.Time(0,0));
    }
      

    return super.onInitialize();
  }
  // The finalize action (on ALIVE->END transition)
  // formaer rtc_exiting_entry()
//  @Override
//  protected ReturnCode_t onFinalize() {
//    return super.onFinalize();
//  }
  //
  // The startup action when ExecutionContext startup
  // former rtc_starting_entry()
//  @Override
//  protected ReturnCode_t onStartup(int ec_id) {
//    return super.onStartup(ec_id);
//  }
  //
  // The shutdown action when ExecutionContext stop
  // former rtc_stopping_entry()
//  @Override
//  protected ReturnCode_t onShutdown(int ec_id) {
//    return super.onShutdown(ec_id);
//  }
  //
  // The activated action (Active state entry action)
  // former rtc_active_entry()
  @Override
  protected ReturnCode_t onActivated(int ec_id) {
    dataSize = 1;
    setDataSize(dataSize);

    sendCount = 0;
    logMulCnt = 0;

    return super.onActivated(ec_id);
  }
  //
  // The deactivated action (Active state exit action)
  // former rtc_active_exit()
  @Override
  protected ReturnCode_t onDeactivated(int ec_id) {
    try{
      outputStream.close();
    } catch(Exception ex){
      System.out.println(ex.toString());
      System.out.println("入出力エラー");
    }
    dataSize = 1;
    setDataSize(dataSize);

    sendCount = 0;
    logMulCnt = 0;


    if (getInPortConnectorSize() == 0) {
      super.exit();
    }
    return super.onDeactivated(ec_id);
  }
  //
  // The execution action that is invoked periodically
  // former rtc_active_do()
  @Override
  protected ReturnCode_t onExecute(int ec_id) {

    double logMul[] = {2.0, 2.5, 2.0};

    // Setting data length
    if (getDataSize() != dataSize) { 
      setDataSize(dataSize); 
    }
    writeData();
    sendCount++;

    if (sendCount % maxSample.getValue() != 0) { 
      return ReturnCode_t.RTC_OK;
    }

    // increment data size
    if (mode.toString().equals("logincr")) {
/*
      System.out.print("sendcount: " + sendCount);
      System.out.print("\tmaxsample: " + maxSample.getValue());
      System.out.print("\tlogmulcnt%3: " + logMulCnt % 3);
      System.out.println("\tlogmul[]: " + logMul[logMulCnt % 3] );
*/
      dataSize = (int)((double)dataSize * logMul[logMulCnt % 3]);
      logMulCnt = logMulCnt + 1;
    } else if (mode.toString().equals("incr")) {
      dataSize = dataSize + increment.getValue();
    } else {
      if((long)sendCount > maxSend.getValue()) {
        exit();
        return ReturnCode_t.RTC_OK;
      }
    }
/*
    System.out.println("######### data length changed #########");
    System.out.print("length(): " + getDataSize());
    System.out.print("\tm_datasize: " +  dataSize);
    System.out.print("\tm_maxsize: " +  maxSize.getValue());
    System.out.println("\tsendcount: " +  sendCount);
*/

    try{
      Thread.sleep((long)(sleepTime.getValue()*1000));
    }catch(InterruptedException ex){
    }

    // calculation is triggered data size change
    // to finish the last calculation, size 0 array is sent
    if(dataSize > maxSize.getValue()) {
      System.out.println("Exiting");
      setDataSize(1); // to finalize measurement
      writeData();
      deactivate(ec_id);
    }

    return super.onExecute(ec_id);
  }
  //
  // The aborting action when main logic error occurred.
  // former rtc_aborting_entry()
//  @Override
//  public ReturnCode_t onAborting(int ec_id) {
//      return super.onAborting(ec_id);
//  }
  //
  // The error action in ERROR state
  // former rtc_error_do()
//  @Override
//  public ReturnCode_t onError(int ec_id) {
//    return super.onError(ec_id);
//  }
  //
  // The reset action that is invoked resetting
  // This is same but different the former rtc_init_entry()
//  @Override
//  protected ReturnCode_t onReset(int ec_id) {
//    return super.onReset(ec_id);
//  }
// 
  // The state update action that is invoked after onExecute() action
  // no corresponding operation exists in OpenRTm-aist-0.2.0
//  @Override
//  protected ReturnCode_t onStateUpdate(int ec_id) {
//    return super.onStateUpdate(ec_id);
//  }
  //
  // The action that is invoked when execution context's rate is changed
  // no corresponding operation exists in OpenRTm-aist-0.2.0
//  @Override
//  protected ReturnCode_t onRateChanged(int ec_id) {
//    return super.onRateChanged(ec_id);
//  }
  public void writeData() {
    String type = dataType.toString().trim().toLowerCase();
    if(type.equals("octet")) {
      outOctetOut.setTimestamp(outOctet);
      outOctetOut.write();
    } else if (type.equals("short")) {
      outShortOut.setTimestamp(outShort);
      outShortOut.write();
    } else if (type.equals("long")) {
      outLongOut.setTimestamp(outLong);
      outLongOut.write();
    } else if (type.equals("float")) {
      outFloatOut.setTimestamp(outFloat);
      outFloatOut.write();
    } else if (type.equals("double")) {
      outDoubleOut.setTimestamp(outDouble);
      outDoubleOut.write();
    }
  }
  public void setDataSize(int size) {
    String type = dataType.toString().trim().toLowerCase();
    if(type.equals("octet")) {
      outOctet.data = new byte[size];
    } else if (type.equals("short")) {
      outShort.data = new short[size];
    } else if (type.equals("long")) {
      outLong.data = new int[size];
    } else if (type.equals("float")) {
      outFloat.data = new float[size];
    } else if (type.equals("double")) {
      outDouble.data = new double[size];
    }
  }
  public long getDataSize() {
    String type = dataType.toString().trim().toLowerCase();
    if(type.equals("octet")) {
      return outOctet.data.length;
    } else if (type.equals("short")) {
      return outShort.data.length;
    } else if (type.equals("long")) {
      return outLong.data.length;
    } else if (type.equals("float")) {
      return outFloat.data.length;
    } else if (type.equals("double")) {
      return outDouble.data.length;
    }
    return 0;
  }
  public long getInPortConnectorSize() {
    long count = 0;
    String type = dataType.toString().trim().toLowerCase();
    if (type.equals("octet")) {
      count = inOctetIn.get_connector_profiles().length;
    } else if (type.equals("short")) {
      count = inShortIn.get_connector_profiles().length;
    } else if (type.equals("long")) {
      count = inLongIn.get_connector_profiles().length;
    } else if (type.equals("float")) {
      count = inFloatIn.get_connector_profiles().length;
    } else if (type.equals("double")) {
      count = inDoubleIn.get_connector_profiles().length;
    }
    return count;
  }
  public void receiveData(final RTC.Time rtcTime, final long seqLength) {

    // data arrived -> getting time
    //TimeValue receivedTime 
    //    = ClockManager.getInstance().getClock("system").getTime();

    long nanotime = System.nanoTime();
    RTC.Time receivedTime = new RTC.Time((int)(nanotime/1000000000),
                                            (int)(nanotime%1000000000));
    //TimeValue receivedTime 
    //  = new TimeValue((nanotime/1000000000),(nanotime%1000000000));

    if (seqSize == 0) { 
      seqSize = seqLength; 
    }

    // calculate latency statistics
    /*
    System.out.println(
        "Time: " + rtcTime.sec + "[s]\t" + rtcTime.nsec + "[ns]");
    System.out.print("length(): " +  seqLength);
    System.out.print("\tseqSize: " +  seqSize);
    System.out.print("\trecordNum: " + recordNum);
    System.out.println("\trecordPtr: " + recordPtr);
    */

    if (seqLength != seqSize && recordNum != 0) {
      double maxLatency = 0.0, minLatency = 10000.0, meanLatency = 0.0;
      double variance = 0.0, stdDev = 0.0, throughput = 0.0;
      double sum = 0.0, sqSum = 0.0;
      int recordLen;
      if(recordNum > recordPtr ) {
         recordLen = maxSample.getValue();
      } else {
         recordLen = recordPtr;
      }
      /*
      System.out.print("%%%%% record_num: " + recordNum);
      System.out.print(" record_ptr: " + recordPtr);
      System.out.print(" record_len: " + recordLen);
      System.out.println(" maxsample: " + maxSample.getValue());
      */
      for (int ic = 0; ic < recordLen; ++ic) {
        double tmp = (double)record.get(ic).sec 
                        + (double)record.get(ic).nsec/(1000000000.0);
        sum = sum + tmp;
        sqSum = sqSum + (tmp * tmp);
        if (tmp > maxLatency) { 
          maxLatency = tmp; 
        } else if (tmp < minLatency) { 
          minLatency = tmp; 
        }
      }
      meanLatency = sum / recordLen;
      variance = (sqSum / recordLen) - (meanLatency * meanLatency);
      stdDev = Math.sqrt(variance);
      // Time tm (long, long) = 4byte + 4byte [Mbps]
      throughput 
          = (((seqSize * varSize) + (Integer.SIZE + Integer.SIZE)) 
              / meanLatency) 
              / (1024 * 1024);

      // size[byte], min[s], max[s], mean[s], stddev[s], throughput[Mbps]
      String str = new String();
      str = seqSize + "\t";
      str = str + minLatency + "\t" + maxLatency + "\t";
      str = str + meanLatency + "\t" + stdDev + "\t";
      str = str + throughput + System.getProperty("line.separator");
      try{
        outputStream.write(str);
        outputStream.flush();
      } catch(Exception ex){
        System.out.println(ex.toString());
      }
      /*
      System.out.println("==============================");
      System.out.print(seqSize + "\t");
      System.out.print(minLatency + "\t" + maxLatency + "\t");
      System.out.print(meanLatency + "\t" + stdDev + "\t");
      System.out.println(throughput);
      */
      // reset size/index variables
      recordNum = 0;
      recordPtr = 0;
      if (seqLength < seqSize) {
        super.exit();
/*
        coil::Async* async;
        async = coil::AsyncInvoker(this, std::mem_fun(&Throughput::exit));
        async->invoke();
*/
      }
    }
    // measuring latency
    int sec;
    int nsec;
    if(receivedTime.nsec>=rtcTime.nsec){
      sec = receivedTime.sec - rtcTime.sec;
      nsec = receivedTime.nsec - rtcTime.nsec;
    } else {
      sec = receivedTime.sec - rtcTime.sec - 1;
      nsec = (receivedTime.nsec+1000000000) - rtcTime.nsec;
    }
    receivedTime = new RTC.Time(sec,nsec);

    record.set(recordPtr,receivedTime);
    seqSize = seqLength;
    recordPtr = recordPtr + 1; 
    recordNum = recordNum + 1;
    if ((long)recordPtr == maxSample.getValue()) { 
      recordPtr = 0; 
    }
    return;
  }

  public void setConnectorProfile(final ConnectorBase.ConnectorInfo info) {
    outputFile = new StringHolder (
        dataType.toString() 
        + "-" + info.properties.getProperty("interface_type")
        + fileSuffix.toString() + ".dat");

    File file = new File(outputFile.toString());
    try{
      outputStream = new BufferedWriter(new FileWriter(file));
/*
  if (!m_fs.is_open())
    {
      m_fs.open(m_outputfile.c_str(), std::ios::out);
      if (!m_fs.is_open())
        {
          std::cerr << "File open failed!!" << std::endl;
          return;
        }
    }
*/
      // print connector profile as comment lines
      outputStream.write("# Profile::name:      " + info.name);
      outputStream.newLine();
      outputStream.write("# Profile::id:        " + info.id);
      outputStream.newLine();
      outputStream.write("# Profile::properties: ");
      outputStream.newLine();
      String str = new String();
      str = info.properties._dump(str,info.properties,0);
      String crlf = System.getProperty("line.separator");
      str = str.replace(crlf, crlf+"# ");
      outputStream.write("# "+str+crlf);
      // print header
      outputStream.write( 
          "size[byte]\tmin[s]\tmax[s]\tmean[s]\tstddev[s]\tthroughput[Mbps]");
      outputStream.newLine();
      outputStream.flush();

      for(int ic=0;ic<maxSample.getValue();++ic){
        record.add(new RTC.Time(0,0));
      }
    } catch(Exception ex) {
      System.out.println(ex.toString());
    } 
  }

  // DataInPort declaration
  // <rtc-template block="inport_declare">
    
  // </rtc-template>

  // DataOutPort declaration
  // <rtc-template block="outport_declare">
  //protected TimedLong m_out_val;
  //protected DataRef<TimedLong> m_out;
  //protected OutPort<TimedLong> m_outOut;

  protected TimedOctetSeq inOctet = new TimedOctetSeq();
  protected DataRef<TimedOctetSeq> inOctetRef;
  protected InPort<TimedOctetSeq> inOctetIn;

  protected TimedOctetSeq outOctet = new TimedOctetSeq();
  protected DataRef<TimedOctetSeq> outOctetRef;
  protected OutPort<TimedOctetSeq> outOctetOut;

  protected TimedShortSeq inShort = new TimedShortSeq();
  protected DataRef<TimedShortSeq> inShortRef;
  protected InPort<TimedShortSeq> inShortIn;

  protected TimedShortSeq outShort = new TimedShortSeq();
  protected DataRef<TimedShortSeq> outShortRef;
  protected OutPort<TimedShortSeq> outShortOut;

  protected TimedLongSeq inLong = new TimedLongSeq();
  protected DataRef<TimedLongSeq> inLongRef;
  protected InPort<TimedLongSeq> inLongIn;

  protected TimedLongSeq outLong = new TimedLongSeq();
  protected DataRef<TimedLongSeq> outLongRef;
  protected OutPort<TimedLongSeq> outLongOut;

  protected TimedFloatSeq inFloat = new TimedFloatSeq();
  protected DataRef<TimedFloatSeq> inFloatRef;
  protected InPort<TimedFloatSeq> inFloatIn;

  protected TimedFloatSeq outFloat = new TimedFloatSeq();
  protected DataRef<TimedFloatSeq> outFloatRef;
  protected OutPort<TimedFloatSeq> outFloatOut;

  protected TimedDoubleSeq inDouble = new TimedDoubleSeq();
  protected DataRef<TimedDoubleSeq> inDoubleRef;
  protected InPort<TimedDoubleSeq> inDoubleIn;

  protected TimedDoubleSeq outDouble = new TimedDoubleSeq();
  protected DataRef<TimedDoubleSeq> outDoubleRef;
  protected OutPort<TimedDoubleSeq> outDoubleOut;
    
  // </rtc-template>

  // CORBA Port declaration
  // <rtc-template block="corbaport_declare">
    
  // </rtc-template>

  // Service declaration
  // <rtc-template block="service_declare">
    
  // </rtc-template>

  // Consumer declaration
  // <rtc-template block="consumer_declare">
    
  // </rtc-template>

  // <rtc-template block="config_declare">
  protected StringHolder dataType =  new StringHolder();
  protected StringHolder outputFile =  new StringHolder();
  protected IntegerHolder increment = new IntegerHolder();
  protected DoubleHolder sleepTime = new DoubleHolder();
  protected StringHolder mode =  new StringHolder();
  protected IntegerHolder maxSize = new IntegerHolder();
  protected IntegerHolder maxSend = new IntegerHolder();
  protected IntegerHolder maxSample = new IntegerHolder();
  protected StringHolder fileSuffix = new StringHolder();
  // </rtc-template>
  
  private int dataSize;
  private BufferedWriter outputStream;
  private ArrayList<RTC.Time> record = new ArrayList<RTC.Time>();
  private long sendCount;
  private int logMulCnt;
  private long varSize;

  private long seqSize = 0;
  private int recordNum = 0;
  private int recordPtr = 0;

  class DataListener<DataType> extends ConnectorDataListenerT<DataType>{
    public DataListener(ThroughputImpl comp, Class cl){
      super(cl);
      throughputComp = comp;
    }
    @Override
    public ReturnCode operator(ConnectorBase.ConnectorInfo arg,
                 DataType data) {
      ConnectorBase.ConnectorInfo info =(ConnectorBase.ConnectorInfo)arg;
      Class cl = data.getClass();
      String str = cl.getName();
      RTC.Time tim = new RTC.Time(0,0);
      int leng = 0;
      try {
        tim = (RTC.Time)cl.getField("tm").get(data);
        Object obj  = cl.getField("data").get(data);
        leng = Array.getLength(obj);
      }
      catch(NoSuchFieldException e){
        //getField throws
      }
      catch(IllegalAccessException e){
        //set throws
      }
      throughputComp.receiveData(tim, leng);
      return ReturnCode.NO_CHANGE;
    }
    public ThroughputImpl throughputComp;
  }

  class Listener extends ConnectorListener{
    public Listener(final ThroughputImpl comp){
      throughputComp = comp;
    }

    @Override
    public ReturnCode operator(ConnectorBase.ConnectorInfo arg){
      System.out.println("------------------------------");
      System.out.println("       Connected !!");
      System.out.println("------------------------------");
      System.out.println("Profile::name:   "+arg.name);
      System.out.println("Profile::id:     "+arg.id);
      System.out.println("Profile::properties: ");
      String str = new String();
      System.out.println(
          "Profile::data_type:"+arg.properties.getProperty("data_type"));
      str = arg.properties._dump(str,arg.properties,0);
      System.out.println(str);
      System.out.println("------------------------------");
      throughputComp.setConnectorProfile(arg);
      return ReturnCode.NO_CHANGE;
    }
    public ThroughputImpl  throughputComp;
  }
  

}

