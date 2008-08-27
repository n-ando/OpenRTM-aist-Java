package jp.go.aist.rtm.rtctemplate;

import java.util.Arrays;

import jp.go.aist.rtm.rtctemplate.generator.param.DataPortParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;
import jp.go.aist.rtm.rtctemplate.template.TemplateUtil;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;
import org.apache.commons.cli.PosixParser;

/**
 * コマンドラインのパーサ
 */
public class CommandLineParser {
	private static final Options HELP_OPTIONS = new Options();
	{
		HELP_OPTIONS.addOption("h", "h", false, "Prit short help."); // duplicate
		HELP_OPTIONS.addOption("help", "help", false, "Print long help."); // duplicate
	}

	private static final Options OPTIONS = new Options();
	{
		OPTIONS.addOption("help", "help", false, "Print this help."); // duplicate

		// OPTIONS.addOption("cpp", "c++", false, "Generate C++ template
		// code.");
		// OPTIONS.addOption("python", "python", false,
		// "Generate Python template code.");
		// OPTIONS
		// .addOption("java", "java", false,
		// "Generate Java template code.");

		OPTIONS.addOption(OptionBuilder.withLongOpt("backend").withArgName(
				"backend").hasArgs().withDescription(
				"Specify template code generator.").create("b"));

		OPTIONS.addOption(OptionBuilder.withLongOpt("output").withDescription(
				"Output directory.").withValueSeparator().hasArg().create(
				"output"));

		OPTIONS.addOption(OptionBuilder.withLongOpt("module-name")
				.withDescription("Your module name.").withValueSeparator()
				.hasArg().create("name"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("module-desc")
				.withDescription("Module description.").withValueSeparator()
				.hasArg().create("desc"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("module-version")
				.withDescription("Module version.").withValueSeparator()
				.hasArg().create("version"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("module-vender")
				.withDescription("Module vender.").withValueSeparator()
				.hasArg().create("vender"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("module-category")
				.withDescription("Module category.").withValueSeparator()
				.hasArg().create("category"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("module-comp-type")
				.withDescription("Component type.").withValueSeparator()
				.hasArg().create("compType"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("module-act-type")
				.withDescription("Activity type.").withValueSeparator()
				.hasArg().create("actType"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("module-max-inst")
				.withDescription("Number of maximum instance.")
				.withValueSeparator().hasArg().create("maxInst"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("module-lang")
				.withDescription("Language.").withValueSeparator().hasArg()
				.create("lang"));

		OPTIONS.addOption(OptionBuilder.withLongOpt("inport").withArgName(
				"name:type").hasArgs().withDescription(
				"InPort's name and type.").create("inport"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("outport").withArgName(
				"name:type").hasArgs().withDescription(
				"OutPort's name and type.").create("outport"));

		OPTIONS.addOption(OptionBuilder.withLongOpt("service-idl")
				.withDescription("IDL file name for service.")
				.withValueSeparator().hasArg().create("serviceIdl"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("service").withArgName(
				"IF_name:name:type").hasArgs().withDescription(
				"Mapping: IF name, name and type").create("service"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("consumer-idl")
				.withDescription("IDL file name for consumer.")
				.withValueSeparator().hasArg().create("consumerIdl"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("consumer").withArgName(
				"IF_name:name:type").hasArgs().withDescription(
				"Mapping: IF name, name and type").create("consumer"));
		OPTIONS.addOption(OptionBuilder.withLongOpt("idl-include")
				.withDescription("Search path for IDL compile.")
				.withValueSeparator().hasArg().create("idlInclude"));

	}

	private static final Parser PARSER = new PosixParser();

	/**
	 * パースする
	 * 
	 * @param args
	 *            Javaコマンドライン引数
	 * @return パース結果
	 * @throws ParseException
	 *             コマンドライン形式に誤りがある場合など
	 */
	public GeneratorParam parse(String[] args) throws ParseException {

		if (args.length == 0) {
			System.out.println(TemplateUtil.getResourceContents(this.getClass()
					.getPackage().getName().replaceAll("\\.", "/")
					+ "/ShortHelp.txt"));
			return null;
		}

		CommandLine commandLine = commandLine = PARSER.parse(HELP_OPTIONS,
				args, true);
		if (commandLine != null) {
			if (commandLine.hasOption("h")) {
				System.out.println(TemplateUtil.getResourceContents(this
						.getClass().getPackage().getName().replaceAll("\\.",
								"/")
						+ "/ShortHelp.txt"));
				return null;
			}
			if (commandLine.hasOption("help")) {
				System.out.println(TemplateUtil.getResourceContents(this
						.getClass().getPackage().getName().replaceAll("\\.",
								"/")
						+ "/LongHelp.txt"));
				return null;
			}
		}

		commandLine = PARSER.parse(OPTIONS, args, true);

		GeneratorParam result = null;
		if (commandLine != null) {
			result = new GeneratorParam();

			RtcParam rtcParam = new RtcParam(result);
			result.getRtcParams().add(rtcParam);

			// if (commandLine.hasOption("cpp")) {
			// rtcParam.getLangList().add(RtcParam.LANG_CPP);
			// }
			// if (commandLine.hasOption("python")) {
			// rtcParam.getLangList().add(RtcParam.LANG_PYTHON);
			// }
			// if (commandLine.hasOption("java")) {
			// rtcParam.getLangList().add(RtcParam.LANG_JAVA);
			// }
			if (commandLine.hasOption("b")) {
				rtcParam.getLangList().addAll(Arrays.asList(commandLine.getOptionValues("b")));
			}
			if (commandLine.hasOption("output")) {
				result.setOutputDirectory(commandLine.getOptionValue("output"));
			} else {
				throw new MissingArgumentException("'--output'　is required.");
			}

			if (commandLine.hasOption("name")) {
				rtcParam.setName(commandLine.getOptionValue("name"));
			} else {
				throw new MissingArgumentException(
						"'--module-name' is required.");
			}

			if (commandLine.hasOption("desc")) {
				rtcParam.setDescription(commandLine.getOptionValue("desc"));
			}
			if (commandLine.hasOption("version")) {
				rtcParam.setVersion(commandLine.getOptionValue("version"));
			}
			if (commandLine.hasOption("vender")) {
				rtcParam.setVender(commandLine.getOptionValue("vender"));
			}
			if (commandLine.hasOption("category")) {
				rtcParam.setCategory(commandLine.getOptionValue("category"));
			}
			if (commandLine.hasOption("compType")) {
				rtcParam.setComponentType(commandLine
						.getOptionValue("compType"));
			}
			if (commandLine.hasOption("actType")) {
				rtcParam.setActivityType(commandLine.getOptionValue("actType"));
			}
			if (commandLine.hasOption("maxInst")) {
				try {
					int optionValue = Integer.parseInt(commandLine
							.getOptionValue("maxInst"));

					rtcParam.setMaxInstance(optionValue);
				} catch (Exception e) {
					throw new ParseException(
							"'--module-max-inst' is not Number.");
				}
			}
			if (commandLine.hasOption("lang") && rtcParam.getLanguage() == null) {
				rtcParam.setLanguage(commandLine.getOptionValue("lang"));
			}

			if (commandLine.hasOption("inport")) {
				for (String string : commandLine.getOptionValues("inport")) {
					String[] nameAndType = getPortStrings(string);
					rtcParam.getInports().add(
							new DataPortParam(nameAndType[0], nameAndType[1]));
				}
			}
			if (commandLine.hasOption("outport")) {
				for (String string : commandLine.getOptionValues("outport")) {
					String[] nameAndType = getPortStrings(string);
					rtcParam.getOutports().add(
							new DataPortParam(nameAndType[0], nameAndType[1]));
				}
			}

			if (commandLine.hasOption("serviceIdl")) {
				result.addProviderIDLPath(commandLine
						.getOptionValue("serviceIdl"));
			}
			if (commandLine.hasOption("service")) {
				for (String string : commandLine.getOptionValues("service")) {
					String[] ifNameAndNameAndType = getServiceNameStrings(string);
					rtcParam.getProviderReferences().add(
							new ServiceReferenceParam(ifNameAndNameAndType[0],
									ifNameAndNameAndType[1],
									ifNameAndNameAndType[2], rtcParam));
				}
			}
			if (commandLine.hasOption("consumerIdl")) {
				result.addConsumerIDLPath(commandLine
						.getOptionValue("consumerIdl"));
			}
			if (commandLine.hasOption("consumer")) {
				for (String string : commandLine.getOptionValues("consumer")) {
					String[] ifNameAndNameAndType = getServiceNameStrings(string);
					rtcParam.getConsumerReferences().add(
							new ServiceReferenceParam(ifNameAndNameAndType[0],
									ifNameAndNameAndType[1],
									ifNameAndNameAndType[2], rtcParam));
				}
			}
			if (commandLine.hasOption("idlInclude")) {
				result.setIncludeIDLPath(commandLine
						.getOptionValue("idlInclude"));
			}
		}

		return result;
	}

	/**
	 * ポート文字列を分解する
	 * 
	 * @param string
	 *            ポート文字列
	 * @return 分解結果
	 * @throws MissingArgumentException
	 *             不正な形式の場合
	 */
	private String[] getPortStrings(String string)
			throws MissingArgumentException {
		String[] nameAndType = string.split(":");
		if (nameAndType.length != 2) {
			throw new MissingArgumentException("Invalidated argument for:"
					+ "[--in<out>port]" + " args:" + Arrays.asList(nameAndType));
		}
		return nameAndType;
	}

	/**
	 * サービス参照文字列を分解する
	 * 
	 * @param string
	 *            ポート文字列
	 * @return 分解結果
	 * @throws MissingArgumentException
	 *             不正な形式の場合
	 */
	private String[] getServiceNameStrings(String string)
			throws MissingArgumentException {
		String[] ifNameAndNameAndType = string.split(":");
		if (ifNameAndNameAndType.length != 3) {
			throw new MissingArgumentException("Invalidated argument for:"
					+ "[--service]" + " args:"
					+ Arrays.asList(ifNameAndNameAndType));
		}
		return ifNameAndNameAndType;
	}
}
