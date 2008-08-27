package jp.go.aist.rtm.rtcbuilder.generator.param;

import java.io.Serializable;

/**
 * ActionsèÓïÒÇï\Ç∑ÉNÉâÉX
 */
public class ActionsParam implements Serializable {
	
	private boolean implemented;
	private String pre_condition;
	private String overview;
	private String post_condition;

	public ActionsParam() {
		this.implemented = false;
		this.pre_condition = "";
		this.overview = "";
		this.post_condition = "";
	}
	public ActionsParam(boolean implemented, String overview, String pre_con, String post_con) {
		this.implemented = implemented;
		this.pre_condition = pre_con;
		this.overview = overview;
		this.post_condition = post_con;
	}

	public boolean getImplemented() {
		return this.implemented;
	}
	public String getOverView() {
		if(this.overview==null) this.overview = "";
		return this.overview;
	}
	public String getPreCondition() {
		if(this.pre_condition==null) this.pre_condition = "";
		return this.pre_condition;
	}
	public String getPostCondition() {
		if(this.post_condition==null) this.post_condition = "";
		return this.post_condition;
	}

	public void setImplemaented(String implemented) {
		if( implemented.toUpperCase().equals("TRUE") )
			this.implemented = true;
		else
			this.implemented = false;
	}
	public void setImplemaented(boolean implemented) {
		this.implemented = implemented;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public void setPreCondition(String precondition) {
		this.pre_condition = precondition;
	}
	public void setPostCondition(String postcondition) {
		this.post_condition = postcondition;
	}
}
