package RTMExamples.SimpleService;


/**
* RTMExamples/SimpleService/MyServiceOperations.java .
* IDL-to-Java コンパイラ (ポータブル), バージョン "3.1" で生成
* 生成元: src/RTMExamples/MyService.idl
* 2008年7月17日 22時25分31秒 JST
*/

public interface MyServiceOperations 
{
  String echo (String msg);
  String[] get_echo_history ();
  void set_value (float value);
  float get_value ();
  float[] get_value_history ();
} // interface MyServiceOperations
