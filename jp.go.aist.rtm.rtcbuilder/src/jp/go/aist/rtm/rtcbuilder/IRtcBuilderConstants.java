package jp.go.aist.rtm.rtcbuilder;


public interface IRtcBuilderConstants {

    /**
     * OpenNewEditor ActionSet のID
     */
    public static final String NEWEDITOR_ACTION_ID = RtcBuilderPlugin.PLUGIN_ID + ".ui.actionSet";

	public static final String OPEN_RTM_VERSION = "0.4.1";

	/**
	 * サービス実装のデフォルトサフィックス
	 */
	public static final String DEFAULT_SVC_IMPL_SUFFIX = "SVC_impl";

	/**
	 * サービススタブのデフォルトサフィックス
	 */
	public static final String DEFAULT_SVC_STUB_SUFFIX = "Stub";

	/**
	 * サービススケルトンのデフォルトサフィックス
	 */
	public static final String DEFAULT_SVC_SKEL_SUFFIX = "Skel";
	
	public static final String SPEC_SUFFIX = "RTC";
	public static final String SPEC_MAJOR_SEPARATOR = ":";
	public static final String SPEC_MINOR_SEPARATOR = ".";
	//
	public static final String SPEC_DATA_INPORT_KIND = "DataInPort";
	public static final String SPEC_DATA_OUTPORT_KIND = "DataOutPort";

	public static final String DEFAULT_RTC_XML = "RTC.xml";
	public static final String YAML_EXTENSION = "yaml";
	public static final String XML_EXTENSION = "xml";

	public static final String[] COMPONENT_TYPE_ITEMS = new String[] {
		"STATIC", "UNIQUE", "COMMUTATIVE" };

	public static final String[] ACTIVITY_TYPE_ITEMS = new String[] {
			"PERIODIC", "SPORADIC", "EVENT_DRIVEN" };

	public static final String[] COMPONENT_KIND_ITEMS = new String[] {
		"DataFlowComponent", 
		"FiniteStateMachineComponent",
		"DataFlowFiniteStateMachineComponent",
		"FiniteStateMachineMultiModeComponent",
		"DataFlowMultiModeComponent",
		"DataFlowFiniteStateMachineMultiModeComponent"};

	public static final String[] EXECUTIONCONTEXT_TYPE_ITEMS = new String[] {
		"PeriodicExecutionContext", "ExtTrigExecutionContext" };

	public static final String[] ACTION_TYPE_ITEMS = new String[] {
		"on_initialize", "on_finalize", "on_startup", "on_shutdown", "on_activated",
		"on_deactivated", "on_execute", "on_aborting", "on_error", "on_reset",
		"on_state_update", "on_rate_changed" };
	
	public static final String TAG_BACKEND = "backend";
	public static final String TAG_SVC_IDL = "svc-idl";
	public static final String TAG_SVC_NAME = "svc-name";
	public static final String TAG_INPORT_TYPE = "type";
	public static final String TAG_OUTPORT_TYPE = "type";
	public static final String TAG_SERVICE_PORT_PORTNAME = "portname";
	public static final String TAG_SERVICE_PORT_TYPE = "type";

	public static final String LANG_CPP = "C++";
	public static final String LANG_CPPWIN = "C++(Windows)";
	public static final String LANG_CSHARP = "C#";
	public static final String LANG_RUBY = "Ruby";

	public static final String LANG_CPP_ARG = "cxx";
	public static final String LANG_CPPWIN_ARG = "cxxwin";
	public static final String DOC_DEFAULT_PREFIX = " * "; 
	public static final String DOC_DESC_PREFIX = "   * ";
	public static final String DOC_UNIT_PREFIX = "   *         "; 
	public static final String DOC_RANGE_PREFIX = "   *          ";
	public static final String DOC_CONSTRAINT_PREFIX = "   *               "; 
	public static final String DOC_NUMBER_PREFIX = "   *           ";
	public static final String DOC_SEMANTICS_PREFIX = "   *              ";
	public static final String DOC_CYCLE_PREFIX = "   *                    "; 
	public static final String DOC_INTERFACE_PREFIX = "   *            ";
	public static final String DOC_INTERFACE_DETAIL_PREFIX = "   *                  "; 
	//
	public static final String DOC_README_PREFIX = "   "; 
	public static final String DOC_README_COPYRIGHT_PREFIX = "  "; 
	public static final String DOC_README_MODULE_PREFIX = "             "; 
	public static final String DOC_README_ACTIVITY_PREFIX = "\t               "; 
	public static final String DOC_README_PORT_PREFIX = "\t             "; 
	public static final String DOC_README_PORT_DETAIL_PREFIX = "\t\t                 "; 
	public static final String DOC_README_INTERFACE_PREFIX = "\t\t               "; 
	//
	public static final String DOC_DESC_PREFIX_JAVA = "     * ";
	public static final String DOC_UNIT_PREFIX_JAVA = "     *         "; 
	public static final String DOC_RANGE_PREFIX_JAVA = "     *          ";
	public static final String DOC_CONSTRAINT_PREFIX_JAVA = "     *               "; 
	public static final String DOC_NUMBER_PREFIX_JAVA = "     *           ";
	public static final String DOC_SEMANTICS_PREFIX_JAVA = "     *              ";
	public static final String DOC_CYCLE_PREFIX_JAVA = "     *                    "; 
	public static final String DOC_INTERFACE_PREFIX_JAVA = "     *            ";
	public static final String DOC_INTERFACE_DETAIL_PREFIX_JAVA = "     *                  ";
	//
	public static final String DOC_DEFAULT_PREFIX_PY = " ";
	public static final String DOC_MODULE_PREFIX_PY = "\t";
	public static final String DOC_DESC_PREFIX_PY = "\t\t";
	public static final String DOC_UNIT_PREFIX_PY = "\t\t         ";
	public static final String DOC_NUMBER_PREFIX_PY = "\t\t           ";
	public static final String DOC_SEMANTICS_PREFIX_PY = "\t\t              ";
	public static final String DOC_FREQUENCY_PREFIX_PY = "\t\t              ";
	public static final String DOC_CYCLE_PREFIX_PY = "\t\t                    ";
	public static final String DOC_DETAIL_PREFIX_PY = "\t\t                  ";
	public static final String DOC_RANGE_PREFIX_PY = "\t\t          ";
	public static final String DOC_CONSTRAINT_PREFIX_PY = "\t\t               ";
	public static final String DOC_PRE_PREFIX_PY = "\t\t     ";
	public static final String DOC_POST_PREFIX_PY = "\t\t      ";
	public static final String DOC_ACTIVITY_PREFIX_PY = "\t#\t";
	public static final String DOC_PRESH_PREFIX_PY = "\t#\t     ";
	public static final String DOC_POSTSH_PREFIX_PY = "\t#\t      ";
	//
	public static final int DOC_DEFAULT_WIDTH = 80; 
	public static final int DOC_AUTHOR_OFFSET = 11; 
	public static final int DOC_DEFAULT_OFFSET = 3; 
	public static final int DOC_DESC_OFFSET = 5;
	public static final int DOC_PRE_OFFSET = 10;
	public static final int DOC_POST_OFFSET = 11;
	public static final int DOC_UNIT_OFFSET = 13;
	public static final int DOC_RANGE_OFFSET = 14;
	public static final int DOC_CONSTRAINT_OFFSET = 19;
	public static final int DOC_NUMBER_OFFSET = 15;
	public static final int DOC_SEMANTICS_OFFSET = 18;
	public static final int DOC_CYCLE_OFFSET = 24;
	public static final int DOC_INTERFACE_OFFSET = 16;
	public static final int DOC_INTERFACE_DETAIL_OFFSET = 22;
	//
	public static final int DOC_DESC_OFFSET_JAVA = 7;
	public static final int DOC_PRE_OFFSET_JAVA = 12;
	public static final int DOC_POST_OFFSET_JAVA = 13;
	public static final int DOC_UNIT_OFFSET_JAVA = 15;
	public static final int DOC_RANGE_OFFSET_JAVA = 16;
	public static final int DOC_CONSTRAINT_OFFSET_JAVA = 21;
	public static final int DOC_NUMBER_OFFSET_JAVA = 17;
	public static final int DOC_SEMANTICS_OFFSET_JAVA = 20;
	public static final int DOC_CYCLE_OFFSET_JAVA = 26;
	public static final int DOC_INTERFACE_OFFSET_JAVA = 18;
	public static final int DOC_INTERFACE_DETAIL_OFFSET_JAVA = 24;
	//
	public static final int DOC_AUTHOR_OFFSET_PY = 9;
	public static final int DOC_DEFAULT_OFFSET_PY = 1; 
	public static final int DOC_DESC_OFFSET_PY = 4;
	public static final int DOC_NUMBER_OFFSET_PY = 15;
	public static final int DOC_FREQUENCY_OFFSET_PY = 18;
	public static final int DOC_CYCLE_OFFSET_PY = 24;
	public static final int DOC_DETAIL_OFFSET_PY = 22;
	public static final int DOC_RANGE_OFFSET_PY = 14;
	public static final int DOC_CONSTRAINT_OFFSET_PY = 19;
	public static final int DOC_PRE_OFFSET_PY = 9;
	public static final int DOC_POST_OFFSET_PY = 10;
	public static final int DOC_ACTIVITY_OFFSET_PY = 4;
	//
	public static final int DOC_README_MODULE_OFFSET = 13; 
	public static final int DOC_README_ACTIVITY_OFFSET = 17;
	public static final int DOC_README_PORT_OFFSET = 15;
	public static final int DOC_README_PORT_DETAIL_OFFSET = 21;
	public static final int DOC_README_INTERFACE_OFFSET = 19;
	//
	public static final int ACTIVITY_INITIALIZE = 0; 
	public static final int ACTIVITY_FINALIZE = 1;
	public static final int ACTIVITY_STARTUP = 2;
	public static final int ACTIVITY_SHUTDOWN = 3; 
	public static final int ACTIVITY_ACTIVATED = 4;
	public static final int ACTIVITY_DEACTIVATED = 5; 
	public static final int ACTIVITY_EXECUTE = 6; 
	public static final int ACTIVITY_ABORTING = 7; 
	public static final int ACTIVITY_ERROR = 8;
	public static final int ACTIVITY_RESET = 9; 
	public static final int ACTIVITY_STATE_UPDATE = 10; 
	public static final int ACTIVITY_RATE_CHANGED = 11; 
	//
	public static final int PORT_SPACE_HEIGHT = 60;
	public static final int PORT_SPACE_WIDTH = 150;

}
