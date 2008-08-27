package jp.go.aist.rtm.rtcbuilder._test.cxx;

import java.io.StringReader;

import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.IDLParser;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.op_dcl;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.specification;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.visitor.GJVoidDepthFirst;
import junit.framework.TestCase;

public class IDLParserTest extends TestCase {
public void testDefault() throws Exception {
		IDLParser target;

		 target = new IDLParser(new StringReader(""
		 // + "#include <ServiceClassParam.idl>\n"
		 + "interface MyService\n"
		 + " : RTM::ServiceClassParam\n"
		 + "{\n"
		 + " void setGain(in float gain);\n"
		 + " float getGain();\n"
		 + "};\n"));

//		target = new IDLParser(
//				new FileInputStream(
//						"C:\\Documents and Settings\\yamashita.TA\\workspace\\RtcLink\\idl\\all.idl"));

		specification spec = target.specification();
		spec.accept(new GJVoidDepthFirst<String>() {
			@Override
			public void visit(op_dcl n, String argu) {
				super.visit(n, argu);
			}
			
			public void visit(jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.NodeToken n, String argu) {
				System.out.println(n.getClass().getName() + ":" + n.kind + ":" + n.tokenImage);
			};
		}, null);
	}}
