//package jp.go.aist.rtm.systemeditor._test;
//
//import java.util.Arrays;
//import java.util.List;
//
//import jp.go.aist.rtm.systemeditor.model.component.impl.ConnectorProfileImpl;
//import junit.framework.TestCase;
//
//public class ConnectorProfileTest extends TestCase {
//	public void testDefalult() throws Exception {
//		List result;
//
//		result = ConnectorProfileImpl.getAllowList(Arrays.asList("hoge"), Arrays
//				.asList("hoge"));
//		assertEquals(Arrays.asList("hoge"), result);
//
//		result = ConnectorProfileImpl.getAllowList(Arrays.asList("hoge1","hoge2"), Arrays
//				.asList("hoge2","hoge1"));
//		assertEquals(Arrays.asList("hoge1","hoge2"), result);
//		
//		result = ConnectorProfileImpl.getAllowList(Arrays.asList("hoge1","hoge2"), Arrays
//				.asList("hoge2","any"));
//		assertEquals(Arrays.asList("hoge1","hoge2"), result);
//		
//		result = ConnectorProfileImpl.getAllowList(Arrays.asList("four","five"), Arrays
//				.asList("five","any"));
//		assertEquals(Arrays.asList("four","five"), result);
//		
//	}
//}
