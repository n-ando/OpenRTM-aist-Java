package jp.go.aist.rtm.rtcbuilder._test.com;

import jp.go.aist.rtm.rtcbuilder.ui.StringUtil;
import junit.framework.TestCase;

public class StringUtilTest extends TestCase {
	public void testSplit() throws Exception{
		String original = "1234567890";
		String result = StringUtil.splitString(original, 5, " * ", 0);
		String expected = "12345\r\n * 67890";
		assertEquals(expected, result);
	}

	public void testSplit2() throws Exception{
		String original = "12345678901";
		String result = StringUtil.splitString(original, 5, " * ", 0);
		String expected = "12345\r\n * 67890\r\n * 1";
		assertEquals(expected, result);
	}

	public void testSplit3() throws Exception{
		String original = "ÇPÇQÇRÇSÇTÇUÇVÇWÇX";
		String result = StringUtil.splitString(original, 5, " * ", 0);
		String expected = "ÇPÇQÇR\r\n * ÇSÇTÇU\r\n * ÇVÇWÇX";
		assertEquals(expected, result);
	}

	public void testSplit4() throws Exception{
		String original = "ÇPÇQÇRÇSÇTÇUÇVÇWÇXÇO";
		String result = StringUtil.splitString(original, 5, " * ", 0);
		String expected = "ÇPÇQÇR\r\n * ÇSÇTÇU\r\n * ÇVÇWÇX\r\n * ÇO";
		assertEquals(expected, result);
	}

	public void testSplit5() throws Exception{
		String original = "1ÇQÇR4ÇTÇU7ÇWÇXÇO";
		String result = StringUtil.splitString(original, 5, " * ", 0);
		String expected = "1ÇQÇR\r\n * 4ÇTÇU\r\n * 7ÇWÇX\r\n * ÇO";
		assertEquals(expected, result);
	}

	public void testSplit6() throws Exception{
		String original = "12ÇRÇSÇT67ÇWÇXÇO";
		String result = StringUtil.splitString(original, 5, " * ", 0);
		String expected = "12ÇRÇS\r\n * ÇT67ÇW\r\n * ÇXÇO";
		assertEquals(expected, result);
	}

	public void testSplit7() throws Exception{
		String original = "12ÇRÇSÇT67ÇWÇXÇO";
		String result = StringUtil.splitString(original, 5, " * ", 3);
		String expected = "12\r\n * ÇRÇSÇT\r\n * 67ÇWÇX\r\n * ÇO";
		assertEquals(expected, result);
	}

	public void testSplit8() throws Exception{
		String original = "1234567";
		String result = StringUtil.splitString(original, 10, " * ", 1);
		String expected = "1234567";
		assertEquals(expected, result);
	}

	public void testSplit9() throws Exception{
		String original = "123456<br>";
		String result = StringUtil.splitString(original, 8, " * ", 0);
		String expected = "123456<br>";
		assertEquals(expected, result);
	}
	
	public void testSplit10() throws Exception{
		String original = "<li><a href=\"/news-and-topics/\" title=\"êVíÖèÓïÒ\">êVíÖèÓïÒ</a></li>";
		String result = StringUtil.splitString(original, 5, " * ", 0);
		String expected = "<li><a href=\"/news-and-topics/\" title=\"êVíÖèÓïÒ\">\r\n * êVíÖèÓ\r\n * ïÒ</a>\r\n * </li>";
		assertEquals(expected, result);
	}

//	public void testSplit11() throws Exception{
//		String original = "<li><a href=\"/news-and-topics/\" title=\"êVíÖèÓïÒ\">êVíÖèÓïÒ</a></li>";
//		String result = StringUtil.splitString(original, 50, " * ", 0);
//		String expected = "<li><a href=\"/news-and-topics/\" title=\"êVíÖèÓïÒ\">\r\n * êVíÖèÓïÒ</a></li>";
//		assertEquals(expected, result);
//	}

	public void testSplit12() throws Exception{
		String original = "<li><a href=\"/news-and-topics/\" title=\"êVíÖèÓïÒ\">êVíÖèÓïÒ</a></li>";
		String result = StringUtil.splitString(original, 4, " * ", 0);
		String expected = "<li>\r\n * <a href=\"/news-and-topics/\" title=\"êVíÖèÓïÒ\">\r\n * êVíÖ\r\n * èÓïÒ\r\n * </a>\r\n * </li>";
		assertEquals(expected, result);
	}
	
	public void testSplit13() throws Exception{
		String original = "<li><span><br></span></li>";
		String result = StringUtil.splitString(original, 15, " * ", 0);
		String expected = "<li><span><br></span>\r\n * </li>";
		assertEquals(expected, result);
	}
	
	public void testSplit14() throws Exception{
		String original = "<li><span><br></span></li>";
		String result = StringUtil.splitString(original, 14, " * ", 0);
		String expected = "<li><span><br>\r\n * </span></li>";
		assertEquals(expected, result);
	}
	
	public void testSplit15() throws Exception{
		String original = "<li><span><br></span></li>";
		String result = StringUtil.splitString(original, 2, " * ", 0);
		String expected = "<li>\r\n * <span>\r\n * <br>\r\n * </span>\r\n * </li>";
		assertEquals(expected, result);
	}

	public void testSplit9_2() throws Exception{
		String original = "123456<br>";
		String result = StringUtil.splitString(original, 8, " * ", 3);
		String expected = "12345\r\n * 6<br>";
		assertEquals(expected, result);
	}

	public void testSplit10_2() throws Exception{
	String original = "<li><a href=\"/news-and-topics/\" title=\"êVíÖèÓïÒ\">êVíÖèÓïÒ</a></li>";
	String result = StringUtil.splitString(original, 5, " * ", 1);
	String expected = "<li>\r\n * <a href=\"/news-and-topics/\" title=\"êVíÖèÓïÒ\">\r\n * êVíÖèÓ\r\n * ïÒ</a>\r\n * </li>";
	assertEquals(expected, result);
}
	
	public void testSplit11_2() throws Exception{
		String original = "<li><a href=\"/news-and-topics/\" title=\"êVíÖèÓïÒ\">êVíÖèÓïÒ</a></li>";
		String result = StringUtil.splitString(original, 50, " * ", 45);
		String expected = "<li><a href=\"/news-and-topics/\" title=\"êVíÖèÓïÒ\">\r\n * êVíÖèÓïÒ</a></li>";
		assertEquals(expected, result);
	}
	
	public void testSplit13_2() throws Exception{
		String original = "<li><span><br></span></li>";
		String result = StringUtil.splitString(original, 15, " * ", 5);
		String expected = "<li><span>\r\n * <br></span></li>";
		System.out.println(expected);
		System.out.println(result);
		assertEquals(expected, result);
	}
	
	public void testSplit14_2() throws Exception{
		String original = "<li><span><br></span></li>";
		String result = StringUtil.splitString(original, 14, " * ", 4);
		String expected = "<li><span>\r\n * <br></span></li>";
		System.out.println(expected);
		System.out.println(result);
		assertEquals(expected, result);
	}
	
	public void testSplit15_2() throws Exception{
		String original = "<li><span><br></span></li>";
		String result = StringUtil.splitString(original, 2, " * ", 1);
		String expected = "<li>\r\n * <span>\r\n * <br>\r\n * </span>\r\n * </li>";
		assertEquals(expected, result);
	}
	
	
	public void testSplit16() throws Exception{
		String original = "ÇPÇQÇRÇSÇTÇUÇVÇWÇXÇOÇPÇQÇRÇSÇTÇUÇVÇWÇXÇO<br>";
		String result = StringUtil.splitString(original, 16, " * ", 0);
		String expected = "ÇPÇQÇRÇSÇTÇUÇVÇW\r\n * ÇXÇOÇPÇQÇRÇSÇTÇU\r\n * ÇVÇWÇXÇO<br>";
		assertEquals(expected, result);
	}
}
