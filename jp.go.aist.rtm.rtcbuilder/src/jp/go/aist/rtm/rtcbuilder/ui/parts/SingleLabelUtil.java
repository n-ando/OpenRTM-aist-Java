package jp.go.aist.rtm.rtcbuilder.ui.parts;

import java.util.ArrayList;
import java.util.List;

public class SingleLabelUtil {

	public static void convertSingleItems2Strings(ArrayList<SingleLabelItem> sources, List<String> targets) {
		targets.clear();
		for( SingleLabelItem target : sources) {
			targets.add(new String(target.getLabeltext()));
		}
	}

	public static void convertStrings2SingleItems(List<String> sources, ArrayList<SingleLabelItem> targets) {
		targets.clear();
		for( String source : sources) {
			targets.add(new SingleLabelItem(source));
		}
	}
}
