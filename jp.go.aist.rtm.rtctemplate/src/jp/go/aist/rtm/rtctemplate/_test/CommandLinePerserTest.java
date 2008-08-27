package jp.go.aist.rtm.rtctemplate._test;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.CommandLineParser;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import junit.framework.TestCase;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;

public class CommandLinePerserTest extends TestCase {

	private static final String[] DEFAULT_ARG = new String[] {
			"--output=c:\\tmp\\hoge", "-b","C++", "--backend=Python",
			"--backend=Java", "--module-name=moduleName",
			"--module-desc=description", "--module-version=version",
			"--module-vender=vender", "--module-category=category",
			"--module-comp-type=componentType",
			"--module-act-type=activityType", "--module-max-inst=777",
			"--module-lang=language", "--inport=name0:type0",
			"--inport=name1:type1", "--outport=name0:type0",
			"--outport=name1:type1", "--service-idl=c:\\idl\\hoge.idl",
			"--service=if_name0:name0:type0", "--service=if_name1:name1:type1",
			"--consumer-idl=c:\\idl\\choge.idl",
			"--consumer=cif_name0:cname0:ctype0",
			"--consumer=cif_name1:cname1:ctype1", "--idl-include=c:\\idl" };

	private CommandLineParser target;

	@Override
	protected void setUp() throws Exception {
		target = new CommandLineParser();
	}

	public void testDefault1() throws Exception {
		GeneratorParam result;
		String[] args;

		args = new String[] {};
		result = target.parse(args);

		args = new String[] { "--h" };
		result = target.parse(args);

		args = new String[] { "--help" };
		result = target.parse(args);

		args = new String[DEFAULT_ARG.length];
		System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		result = target.parse(args);

		assertEquals(RtcParam.LANG_CPP, result.getRtcParams().get(0)
				.getLangList().get(0));
		assertEquals(RtcParam.LANG_PYTHON, result.getRtcParams().get(0)
				.getLangList().get(1));
		assertEquals(RtcParam.LANG_JAVA, result.getRtcParams().get(0)
				.getLangList().get(2));
		assertEquals("c:\\tmp\\hoge", result.getOutputDirectory());
		assertEquals("moduleName", result.getRtcParams().get(0).getName());
		assertEquals("description", result.getRtcParams().get(0)
				.getDescription());
		assertEquals("version", result.getRtcParams().get(0).getVersion());
		assertEquals("vender", result.getRtcParams().get(0).getVender());
		assertEquals("category", result.getRtcParams().get(0).getCategory());
		assertEquals("componentType", result.getRtcParams().get(0)
				.getComponentType());
		assertEquals("activityType", result.getRtcParams().get(0)
				.getActivityType());
		assertEquals(777, result.getRtcParams().get(0).getMaxInstance());
//		assertEquals("language", result.getRtcParams().get(0).getLanguage());
		assertEquals("name0", result.getRtcParams().get(0).getInports().get(0)
				.getName());
		assertEquals("type0", result.getRtcParams().get(0).getInports().get(0)
				.getType());
		assertEquals("name1", result.getRtcParams().get(0).getInports().get(1)
				.getName());
		assertEquals("type1", result.getRtcParams().get(0).getInports().get(1)
				.getType());
		assertEquals("name0", result.getRtcParams().get(0).getOutports().get(0)
				.getName());
		assertEquals("type0", result.getRtcParams().get(0).getOutports().get(0)
				.getType());
		assertEquals("name1", result.getRtcParams().get(0).getOutports().get(1)
				.getName());
		assertEquals("type1", result.getRtcParams().get(0).getOutports().get(1)
				.getType());
		assertEquals("c:\\idl\\hoge.idl", result.getProviderIDLPathes());
		assertEquals("if_name1", result.getRtcParams().get(0)
				.getProviderReferences().get(1).getInterfaceName());
		assertEquals("name1", result.getRtcParams().get(0)
				.getProviderReferences().get(1).getName());
		assertEquals("type1", result.getRtcParams().get(0)
				.getProviderReferences().get(1).getType());
		assertEquals("c:\\idl\\choge.idl", result.getConsumerIDLPathes());
		assertEquals("cif_name1", result.getRtcParams().get(0)
				.getConsumerReferences().get(1).getInterfaceName());
		assertEquals("cname1", result.getRtcParams().get(0)
				.getConsumerReferences().get(1).getName());
		assertEquals("ctype1", result.getRtcParams().get(0)
				.getConsumerReferences().get(1).getType());
		assertEquals("c:\\idl", result.getIncludeIDLPath());
	}

	public void testDefault2() throws Exception {
		GeneratorParam result;
		String[] args;

		args = new String[] { "--output=c:\\tmp\\hoge", "--inport=name0" };
		try {
			result = target.parse(args);
			fail();
		} catch (Exception e) {
			// success
		}

		args = new String[] { "--output=c:\\tmp\\hoge", "--outport=name0" };
		try {
			result = target.parse(args);
			fail();
		} catch (Exception e) {
			// success
		}

		args = new String[] { "--output=c:\\tmp\\hoge", "--service=type0" };
		try {
			result = target.parse(args);
			fail();
		} catch (Exception e) {
			// success
		}
	}

	public void testDefault3() throws Exception {
		GeneratorParam result;
		String[] args;

		args = new String[DEFAULT_ARG.length];
		System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		args = remove(args, "--output=c:\\tmp\\hoge");
		try {
			result = target.parse(args);
			fail();
		} catch (MissingArgumentException e) {
			assertEquals("'--output'Å@is required.", e.getMessage());
			// success
		}

		args = new String[DEFAULT_ARG.length];
		System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		args = remove(args, "--module-name=moduleName");
		try {
			result = target.parse(args);
			fail();
		} catch (MissingArgumentException e) {
			assertEquals("'--module-name' is required.", e.getMessage());
			// success
		}

		// args = new String[DEFAULT_ARG.length];
		// System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		// args = remove(args, "--module-desc=description");
		// try {
		// result = target.parse(args);
		// fail();
		// } catch (MissingArgumentException e) {
		// assertEquals("'--module-desc' is required.", e.getMessage());
		// // success
		// }
		//
		// args = new String[DEFAULT_ARG.length];
		// System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		// args = remove(args, "--module-version=version");
		// try {
		// result = target.parse(args);
		// fail();
		// } catch (MissingArgumentException e) {
		// assertEquals("'--module-version' is required.", e.getMessage());
		// // success
		// }
		//
		// args = new String[DEFAULT_ARG.length];
		// System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		// args = remove(args, "--module-vender=vender");
		// try {
		// result = target.parse(args);
		// fail();
		// } catch (MissingArgumentException e) {
		// assertEquals("'--module-author' is required.", e.getMessage());
		// // success
		// }
		//
		// args = new String[DEFAULT_ARG.length];
		// System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		// args = remove(args, "--module-category=category");
		// try {
		// result = target.parse(args);
		// fail();
		// } catch (MissingArgumentException e) {
		// assertEquals("'--module-category' is required.", e.getMessage());
		// // success
		// }
		//
		// args = new String[DEFAULT_ARG.length];
		// System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		// args = remove(args, "--module-comp-type=componentType");
		// try {
		// result = target.parse(args);
		// fail();
		// } catch (MissingArgumentException e) {
		// assertEquals("'--module-comp-type' is required.", e.getMessage());
		// // success
		// }
		//
		// args = new String[DEFAULT_ARG.length];
		// System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		// args = remove(args, "--module-act-type=activityType");
		// try {
		// result = target.parse(args);
		// fail();
		// } catch (MissingArgumentException e) {
		// assertEquals("'--module-act-type' is required.", e.getMessage());
		// // success
		// }
		//
		// args = new String[DEFAULT_ARG.length];
		// System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		// args = remove(args, "--module-max-inst=777");
		// try {
		// result = target.parse(args);
		// fail();
		// } catch (MissingArgumentException e) {
		// assertEquals("'--module-max-inst' is required.", e.getMessage());
		// // success
		// }
		//
		// args = new String[DEFAULT_ARG.length];
		// System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		// args = remove(args, "--module-max-inst=777");
		// try {
		// result = target.parse(args);
		// fail();
		// } catch (MissingArgumentException e) {
		// assertEquals("'--module-max-inst' is required.", e.getMessage());
		// // success
		// }

		args = new String[DEFAULT_ARG.length];
		System.arraycopy(DEFAULT_ARG, 0, args, 0, DEFAULT_ARG.length);
		args = remove(args, "--module-max-inst=777");
		args = add(args, "--module-max-inst=XXX");

		try {
			result = target.parse(args);
			fail();
		} catch (ParseException e) {
			assertEquals("'--module-max-inst' is not Number.", e.getMessage());
			// success
		}

	}

	public String[] remove(String[] from, String target) {
		List result = new ArrayList();
		for (String string : from) {
			if (string.equals(target) == false) {
				result.add(string);
			}
		}

		return (String[]) result.toArray(new String[result.size()]);
	}

	public String[] add(String[] from, String target) {
		String[] result = new String[from.length + 1];
		System.arraycopy(from, 0, result, 0, from.length);
		result[from.length] = target;
		return result;
	}
}
