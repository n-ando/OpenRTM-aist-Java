package jp.go.aist.rtm.toolscommon.synchronizationframework.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * マッピングルールを定義するクラス
 */
public class MappingRule {
	private MappingRule superMappingRule;

	private ClassMapping classMapping;

	private AttributeMapping[] attributeMappings;

	private ReferenceMapping[] referenceMappings;

	/**
	 * コンストラクタ
	 * <p>
	 * 継承元のマッピングルールを指定すると、属性と参照が継承される。継承元が存在しない場合にはnull。
	 * 
	 * @param superMappingRule
	 *            継承元のマッピングルール
	 * @param classMapping
	 *            クラスマッピング
	 * @param attributeMappings
	 *            属性マッピング
	 * @param referenceMappings
	 *            参照マッピング
	 */
	public MappingRule(MappingRule superMappingRule, ClassMapping classMapping,
			AttributeMapping[] attributeMappings,
			ReferenceMapping[] referenceMappings) {
		this.superMappingRule = superMappingRule;
		this.classMapping = classMapping;
		this.attributeMappings = attributeMappings;
		this.referenceMappings = referenceMappings;
	}

	/**
	 * 属性マッピングを取得する
	 * 
	 * @return 属性マッピング
	 */
	public AttributeMapping[] getAttributeMappings() {
		return attributeMappings;
	}

	/**
	 * クラスマッピングを取得する
	 * 
	 * @return クラスマッピング
	 */
	public ClassMapping getClassMapping() {
		return classMapping;
	}

	/**
	 * 参照マッピングを取得する
	 * 
	 * @return 参照マッピング
	 */
	public ReferenceMapping[] getReferenceMappings() {
		return referenceMappings;
	}

	/**
	 * 属性マッピングを取得する
	 * 
	 * @return 属性マッピング
	 */
	public AttributeMapping[] getAllAttributeMappings() {
		List result = new ArrayList();

		MappingRule temp = this;
		while (temp != null) {
			result.addAll(Arrays.asList(temp.getAttributeMappings()));
			temp = temp.getSuperMappingRule();
		}

		return (AttributeMapping[]) result.toArray(new AttributeMapping[result
				.size()]);
	}

	/**
	 * 継承元を含むすべての参照マッピングを取得する
	 * @return 参照マッピング
	 */
	public ReferenceMapping[] getAllReferenceMappings() {
		List result = new ArrayList();

		MappingRule temp = this;
		while (temp != null) {
			result.addAll(Arrays.asList(temp.getReferenceMappings()));
			temp = temp.getSuperMappingRule();
		}

		return (ReferenceMapping[]) result.toArray(new ReferenceMapping[result
				.size()]);
	}

	/**
	 * 継承元のマッピングルールを取得する
	 * @return 継承元のマッピングルール
	 */
	public MappingRule getSuperMappingRule() {
		return superMappingRule;
	}

	/**
	 * 継承元を含むすべての属性マッピングを取得する
	 * @return 属性マッピング
	 */
	public ReferenceMapping[] getAllContainerReferenceMappings() {
		List result = new ArrayList();
		for (ReferenceMapping referrenceMapping : getAllReferenceMappings()) {
			if (referrenceMapping.getLocalFeature().isContainment()) {
				result.add(referrenceMapping);
			}
		}

		return (ReferenceMapping[]) result.toArray(new ReferenceMapping[result
				.size()]);
	}

}
