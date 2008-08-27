package jp.go.aist.rtm.rtcbuilder.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.Node;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.NodeToken;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.base_type_spec;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.identifier;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.interface_dcl;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.interface_header;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.module;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.op_dcl;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.param_dcl;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.scoped_name;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.simple_type_spec;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.specification;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.type_declarator;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.visitor.DepthFirstVisitor;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.visitor.GJNoArguDepthFirst;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.visitor.GJVoidDepthFirst;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceArgumentParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceClassParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceMethodParam;

/**
 * IDLの構文解析木から、ジェネレータのインプットとなる情報に変換するクラス
 * <p>
 */
public class IDLParamConverter {

	/**
	 * IDLの構文解析木から、ジェネレータのインプットとなるServiceParamに変換する
	 * 
	 * @param spec
	 * @return ServiceParam
	 */
	public static List<ServiceClassParam> convert(specification spec,
			final String idlPath) {
		final List<ServiceClassParam> result = new ArrayList<ServiceClassParam>();
		spec.accept(new GJVoidDepthFirst<String>() {
			@Override
			public void visit(interface_dcl n, String argu) {
				final InterfaceInfomation interfaceInfomation = new InterfaceInfomation();

				interface_header interfaceHeader = n.interface_header;

				interfaceInfomation.name = interfaceHeader.identifier.nodeToken.tokenImage;

				n.interface_header.accept(
						new GJVoidDepthFirst<InterfaceInfomation>() {
							@Override
							public void visit(scoped_name n,
									InterfaceInfomation argu) {
								interfaceInfomation.superInterfaceList
										.add(node2String(n));
							}
						}, interfaceInfomation);

				final ServiceClassParam service = new ServiceClassParam();
				service.setIdlPath(idlPath);
				service.setName(getModuleName(n) + interfaceInfomation.name);

				n.interface_body.accept(new GJVoidDepthFirst() {
					@Override
					public void visit(op_dcl n, Object argu) {
						final ServiceMethodParam serviceMethodParam = new ServiceMethodParam();
						serviceMethodParam
								.setName(n.identifier.nodeToken.tokenImage);
						serviceMethodParam.setType(node2String(n.op_type_spec));

						n.parameter_dcls.accept(new GJVoidDepthFirst() {
							@Override
							public void visit(param_dcl n, Object argu) {
								ServiceArgumentParam serviceArgumentParam = new ServiceArgumentParam();
								serviceArgumentParam
										.setName(n.simple_declarator.identifier.nodeToken.tokenImage);
								serviceArgumentParam
										.setType(node2String(n.param_type_spec));
								serviceArgumentParam.setDirection(node2String(n.param_attribute));

								serviceMethodParam.getArguments().add(
										serviceArgumentParam);
							}
						}, null);

						service.getMethods().add(serviceMethodParam);
					}
				}, null);

				result.add(service);
			}

		}, null);

		return result;
	}

	/**
	 * IDLの構文解析木から、sequence型のtypedefを探索する
	 * 
	 * @param spec
	 * @return HashMap
	 */
	public static Map<String,String> convert_typedef(specification spec,
			final String idlPath) {
		final HashMap<String, String> result = new HashMap<String, String>();
		final Vector<String> typeKeys = new Vector<String>();
		final Vector<String> typeValues = new Vector<String>();
		spec.accept(new GJVoidDepthFirst<String>() {
			@Override
			public void visit(type_declarator n, String argu) {
				n.declarators.accept(new DepthFirstVisitor(){
					@Override
					public void visit(identifier n) {
						typeKeys.add(node2String(n));
					}
				});
				n.type_spec.accept(new DepthFirstVisitor(){
					@Override
					public void visit(simple_type_spec n) {
						n.nodeChoice.accept(new DepthFirstVisitor(){
							@Override
							public void visit(simple_type_spec n) {
								typeValues.add(node2String(n) + "[]");
							}
							@Override
							public void visit(base_type_spec n) {
								typeValues.add(node2String(n));
							}
							@Override
							public void visit(scoped_name n) {
								typeValues.add(node2String(n));
							}
						});
						
					}
				});
			}
		}, null);
		for(int intIdx=0;intIdx<typeKeys.size();intIdx++) {
			result.put(typeKeys.elementAt(intIdx),typeValues.elementAt(intIdx));
		}
		return result;
	}
	/**
	 * インタフェースのモジュール名を取得する
	 * 
	 * @param n
	 * @return
	 */
	private static String getModuleName(interface_dcl n) {
		String result = "";

		Node node = n;
		while (node != null) {
			if (node instanceof module) {
				result = node2String(((module) node).identifier) + "::" + result;
			}
			node = node.getParent();
		}

		return result;
	}

	/**
	 * ノードを文字列に変換する
	 * 
	 * @param n
	 *            ノード
	 * @return 変換結果の文字列
	 */
	public static String node2String(Node n) {
		final StringBuffer result = new StringBuffer();
		n.accept(new GJNoArguDepthFirst() {
			@Override
			public Object visit(NodeToken n) {
				return result.append(n.tokenImage);
			}
		});

		return result.toString();
	}

	/**
	 * IDLParamConverterクラスが内部で一時的に使用する、IDLのインタフェース表現。
	 * <p>
	 * 内部クラスであり大きな問題もないので、属性をpublicとしている。
	 */
	public static class InterfaceInfomation {
		public String name;

		public List<String> superInterfaceList = new ArrayList<String>();
	}
}
