//package jp.go.aist.rtm.systemeditor._test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import junit.framework.TestCase;
//import RTC.PortInterfacePolarity;
//import RTC.PortInterfaceProfile;
//
///**
// * テスト後は、ServiceConnectorCreaterDialogにコピーする
// */
//public class ServiceConnectorCreaterDialogTest extends TestCase {
//	public void testname() throws Exception {
//		int result;
//		PortInterfaceProfile[] profiles1;
//		PortInterfaceProfile[] profiles2;
//
//		profiles1 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeA", PortInterfacePolarity.PROVIDED) };
//		profiles2 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeA", PortInterfacePolarity.PROVIDED) };
//		result = countMatch(profiles1, profiles2);
//		assertEquals(0, result);
//
//		profiles1 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeA", PortInterfacePolarity.PROVIDED) };
//		profiles2 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeA", PortInterfacePolarity.REQUIRED) };
//		result = countMatch(profiles1, profiles2);
//		assertEquals(1, result);
//
//		profiles1 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeA", PortInterfacePolarity.REQUIRED) };
//		profiles2 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeA", PortInterfacePolarity.PROVIDED) };
//		result = countMatch(profiles1, profiles2);
//		assertEquals(1, result);
//
//		profiles1 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeA", PortInterfacePolarity.REQUIRED) };
//		profiles2 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeB", PortInterfacePolarity.PROVIDED) };
//		result = countMatch(profiles1, profiles2);
//		assertEquals(0, result);
//
//		profiles1 = new PortInterfaceProfile[] {
//				new PortInterfaceProfile("", "typeA",
//						PortInterfacePolarity.REQUIRED),
//				new PortInterfaceProfile("", "typeB",
//						PortInterfacePolarity.REQUIRED) };
//		profiles2 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeB", PortInterfacePolarity.PROVIDED) };
//		result = countMatch(profiles1, profiles2);
//		assertEquals(1, result);
//
//		profiles1 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeA", PortInterfacePolarity.REQUIRED) };
//		profiles2 = new PortInterfaceProfile[] {
//				new PortInterfaceProfile("", "typeB",
//						PortInterfacePolarity.PROVIDED),
//				new PortInterfaceProfile("", "typeA",
//						PortInterfacePolarity.PROVIDED) };
//		result = countMatch(profiles1, profiles2);
//		assertEquals(1, result);
//
//		profiles1 = new PortInterfaceProfile[] {
//				new PortInterfaceProfile("", "typeA",
//						PortInterfacePolarity.REQUIRED),
//				new PortInterfaceProfile("", "typeA",
//						PortInterfacePolarity.REQUIRED) };
//		profiles2 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeA", PortInterfacePolarity.PROVIDED) };
//		result = countMatch(profiles1, profiles2);
//		assertEquals(1, result);
//
//		profiles1 = new PortInterfaceProfile[] {
//				new PortInterfaceProfile("", "typeA",
//						PortInterfacePolarity.REQUIRED),
//				new PortInterfaceProfile("", "typeA",
//						PortInterfacePolarity.REQUIRED) };
//		profiles2 = new PortInterfaceProfile[] { new PortInterfaceProfile("",
//				"typeA", PortInterfacePolarity.PROVIDED) };
//		result = countMatch(profiles1, profiles2);
//		assertEquals(1, result);
//
//		profiles1 = new PortInterfaceProfile[] {
//				new PortInterfaceProfile("", "typeA",
//						PortInterfacePolarity.REQUIRED),
//				new PortInterfaceProfile("", "typeA",
//						PortInterfacePolarity.PROVIDED) };
//		profiles2 = new PortInterfaceProfile[] {
//				new PortInterfaceProfile("", "typeA",
//						PortInterfacePolarity.PROVIDED),
//				new PortInterfaceProfile("", "typeA",
//						PortInterfacePolarity.REQUIRED) };
//		result = countMatch(profiles1, profiles2);
//		assertEquals(2, result);
//
//	}
//
//	/**
//	 * PortInterfaceProfileのマッチ数を数える
//	 * 
//	 * @param profile1
//	 * @param profile2
//	 * @return
//	 */
//	public static int countMatch(PortInterfaceProfile[] profiles1,
//			PortInterfaceProfile[] profiles2) {
//		List<PortInterfaceProfile> list1 = Arrays.asList(profiles1);
//		List<PortInterfaceProfile> list2 = new ArrayList<PortInterfaceProfile>(
//				Arrays.asList(profiles2));
//
//		int result = 0;
//		for (PortInterfaceProfile profile1 : list1) {
//			for (PortInterfaceProfile profile2 : list2) {
//				if (isMatch(profile1, profile2)) {
//					++result;
//					list2.remove(profile2);
//					break;
//				}
//			}
//		}
//
//		return result;
//	}
//
//	/**
//	 * PortInterfaceProfileがマッチするかどうか
//	 * 
//	 * @param profile
//	 * @param profile2
//	 * @return マッチするかどうか
//	 */
//	private static boolean isMatch(PortInterfaceProfile profile1,
//			PortInterfaceProfile profile2) {
//		boolean result = false;
//		if (profile1.type_name.equals(profile2.type_name)
//				&& ((profile1.polarity.value() == PortInterfacePolarity.PROVIDED
//						.value() && profile2.polarity.value() == PortInterfacePolarity.REQUIRED
//						.value()) || (profile1.polarity.value() == PortInterfacePolarity.REQUIRED
//						.value() && profile2.polarity.value() == PortInterfacePolarity.PROVIDED
//						.value()))) {
//			result = true;
//		}
//		return result;
//	}
//}
