package jp.go.aist.rtm.rtcbuilder.ui.editors.xmlEditor;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class XMLPartitionScanner extends RuleBasedPartitionScanner {

	public final static String XML_COMMENT = "__xml_comment";
	public final static String XML_TAG = "__xml_tag";
	public final static String XML_DOCTAG = "__xml_doctag";

	public XMLPartitionScanner() {

		IToken xmlComment = new Token(XML_COMMENT);
		IToken tag = new Token(XML_TAG);
		IToken doctag = new Token(XML_DOCTAG);

		IPredicateRule[] rules = new IPredicateRule[3];

		rules[0] = new MultiLineRule("<!--", "-->", xmlComment);
		rules[1] = new MultiLineRule("<doc_", "/>", doctag);
		rules[2] = new TagRule(tag);

		setPredicateRules(rules);
	}
}
