package jp.go.aist.rtm.rtcbuilder.ui.parts;

public class SingleLabelItem {
	private String labeltext = "";

	public SingleLabelItem(String name) {
		this.labeltext = name;
	}
	public String getLabeltext() {
		return this.labeltext;
	}

	public void setLabeltext(String filename) {
		this.labeltext = filename;
	}
}
