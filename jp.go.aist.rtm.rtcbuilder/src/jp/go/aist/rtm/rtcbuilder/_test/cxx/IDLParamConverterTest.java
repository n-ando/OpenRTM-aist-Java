package jp.go.aist.rtm.rtcbuilder._test.cxx;

import java.io.StringReader;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.IDLParser;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.ParseException;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.specification;
import jp.go.aist.rtm.rtcbuilder.generator.IDLParamConverter;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceClassParam;
import junit.framework.TestCase;

public class IDLParamConverterTest extends TestCase {
	public void testDefault() throws Exception {
		specification input;
		List<ServiceClassParam> result;

		input = createSpecification("\n                                "
				+ "module  m1  {                                     "
				+ "  module  m2  {                                     "
				+ "    interface MyInterface\n                         "
				+ "    {\n                                             "
				+ "     void setGain(in float gain);\n                 "
				+ "     float getGain();\n                             "
				+ "    };\n                                            "
				+ "  };\n                                            "
				+ "};\n                                              ");
		
		result = IDLParamConverter.convert(input, "idlPath");
		assertEquals(1, result.size());
		assertEquals("m1::m2::MyInterface", result.get(0).getName());

		input = createSpecification("\n                                "
				+ "interface MyService\n                               "
				+ " : Hoge::HogeClass , SDOPackage::SDOService\n                         "
				+ "{\n                                                 "
				+ " void setGain(in float gain);\n                     "
				+ " float getGain();\n                                 "
				+ "};\n                                                ");
		result = IDLParamConverter.convert(input, "idlPath");
		assertEquals("MyService", result.get(0).getName());
		assertEquals("setGain", result.get(0).getMethods().get(0)
				.getName());
		assertEquals("void", result.get(0).getMethods().get(0)
				.getType());
		assertEquals("gain", result.get(0).getMethods().get(0).getArguments()
				.get(0).getName());
		assertEquals("float", result.get(0).getMethods().get(0).getArguments()
				.get(0).getType());

		assertEquals("getGain", result.get(0).getMethods().get(1)
				.getName());
		assertEquals("float", result.get(0).getMethods().get(1)
				.getType());

	}

	private specification createSpecification(String inputString)
			throws ParseException {
		StringReader input = new StringReader(inputString);

		IDLParser parser = new IDLParser(input);
		return parser.specification();
	}
}
