package jp.go.aist.rtm.rtctemplate.generator.param;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.Util;
import jp.go.aist.rtm.rtctemplate.generator.param.idl.ServiceClassParam;

/**
 * ジェネレータの引数となるクラス
 */
public class GeneratorParam implements Serializable {
	public static final String OPEN_RTM_VERSION = "0.4.0";

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

	private String outputDirectory = null;

	private String openRtmVersion = OPEN_RTM_VERSION;

	private String includeIDLPath = null;

	private List<String> providerIDLPathes = new ArrayList<String>();

	private List<String> consumerIDLPathes = new ArrayList<String>();

	List<RtcParam> rtcParams = new ArrayList<RtcParam>();

	List<ServiceClassParam> serviceClassParams = new ArrayList<ServiceClassParam>();

	private String serviceImplSuffix = DEFAULT_SVC_IMPL_SUFFIX;

	private String serviceSkelSuffix = DEFAULT_SVC_SKEL_SUFFIX;

	private String serviceStubSuffix = DEFAULT_SVC_STUB_SUFFIX;

	public List<RtcParam> getRtcParams() {
		return rtcParams;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public String getProviderIDLPath(int intIdx) {
		return providerIDLPathes.get(intIdx);
	}

	public String getProviderIDLPathes() {
		if (providerIDLPathes.size() == 0)
			return null;
		else
			return providerIDLPathes.get(0);
	}

	// public String getProviderIDLPath() {
	// return providerIDLPathes;
	// }

	public void removeProviderIDLPath(int intIdx) {
		this.providerIDLPathes.remove(intIdx);
	}

	public void addProviderIDLPath(String idlPath) {
		this.providerIDLPathes.add(new String(idlPath));
	}

	// public void setProviderIDLPath(String idlPath) {
	// this.providerIDLPath = idlPath;
	// }

	public String getServiceImplSuffix() {
		return serviceImplSuffix;
	}

	public String getServiceSkelSuffix() {
		return serviceSkelSuffix;
	}

	public String getServiceStubSuffix() {
		return serviceStubSuffix;
	}

	// public void setServiceImplSuffix(String serviceImplSuffix) {
	// this.serviceImplSuffix = serviceImplSuffix;
	// }
	//
	// public void setServiceSkelSuffix(String serviceSkelSuffix) {
	// this.serviceSkelSuffix = serviceSkelSuffix;
	// }
	//
	// public void setServiceStubSuffix(String serviceStubSuffix) {
	// this.serviceStubSuffix = serviceStubSuffix;
	// }

	public String getProviderIDLContents(int intIdx) {
		String result = null;
		if (intIdx >= 0 && intIdx <= getProviderIDLPathParams().size()) {
			if (getProviderIDLPath(intIdx) != null) {
				result = Util.readFile(getProviderIDLPath(intIdx));
			}
		}
		return result;
	}

	public String getProviderIDLContents() {
		// String result = null;
		// if (getProviderIDLPath() != null) {
		// result = Util.readFile(getProviderIDLPath());
		// }
		//
		// return result;
		return getProviderIDLContents(0);
	}

	public String getConsumerIDLContents(int intIdx) {
		String result = null;
		if (intIdx >= 0 && intIdx <= getConsumerIDLPathParams().size()) {
			if (getConsumerIDLPath(intIdx) != null) {
				result = Util.readFile(getConsumerIDLPath(intIdx));
			}
		}
		return result;
	}

	public String getConsumerIDLContents() {
		// String result = null;
		// if (getConsumerIDLPath() != null) {
		// result = Util.readFile(getConsumerIDLPath());
		// }
		//
		// return result;
		return getConsumerIDLContents(0);
	}

	public File getIncludeIDLDic() {
		File result = null;
		if (getIncludeIDLPath() != null) {
			File file = new File(getIncludeIDLPath());
			if (file.exists()) {
				result = file;
			} else {
				throw new RuntimeException("Include IDL のディレクトリが見つかりません。");
			}
		}

		return result;
	}

	public List<ServiceClassParam> getServiceClassParams() {
		return serviceClassParams;
	}

	public String getConsumerIDLPath(int intIdx) {
		return consumerIDLPathes.get(intIdx);
	}

	public List<String> getProviderIDLPathParams() {
		return Collections.unmodifiableList(providerIDLPathes);
	}

	public List<String> getConsumerIDLPathParams() {
		return consumerIDLPathes;
	}

	public String getConsumerIDLPathes() {
		if (consumerIDLPathes.size() == 0)
			return null;
		else
			return consumerIDLPathes.get(0);
	}

	public void removeConsumerIDLPath(int intIdx) {
		this.consumerIDLPathes.remove(intIdx);
	}

	// public String getConsumerIDLPath() {
	// return consumerIDLPathes;
	// }

	public void addConsumerIDLPath(String consumerIdlPath) {
		this.consumerIDLPathes.add(new String(consumerIdlPath));
	}

	// public void setConsumerIDLPath(String consumerIdlPath) {
	// this.consumerIDLPath = consumerIdlPath;
	// }

	public String getIncludeIDLPath() {
		return includeIDLPath;
	}

	public void setIncludeIDLPath(String includeIdlPath) {
		this.includeIDLPath = includeIdlPath;
	}

	public String getOpenRtmVersion() {
		return openRtmVersion;
	}

}
