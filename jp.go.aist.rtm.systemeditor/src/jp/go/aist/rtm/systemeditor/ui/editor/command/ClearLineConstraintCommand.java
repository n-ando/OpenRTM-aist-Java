package jp.go.aist.rtm.systemeditor.ui.editor.command;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.go.aist.rtm.toolscommon.model.component.Connector;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorSource;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget;
import jp.go.aist.rtm.toolscommon.model.core.ModelElement;
import jp.go.aist.rtm.toolscommon.model.core.Visiter;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.gef.commands.Command;

/**
 * ラインの制約をクリアするコマンド
 */
public class ClearLineConstraintCommand extends Command {
	private ModelElement model;

	private Map<Connector, Map> oldRoutingConstraint = new IdentityHashMap<Connector, Map>();

	/**
	 * 変更対象のモデルを設定する
	 * 
	 * @param model
	 *            変更対象のモデル
	 */
	public void setModel(ModelElement model) {
		this.model = model;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		model.accept(new Visiter() {
			public void visit(ModelElement element) {
				List<Connector> connnectionList = new ArrayList<Connector>();
				if (element instanceof Connector) {
					connnectionList.add((Connector) element);
				}

				if (element instanceof ConnectorSource) {
					connnectionList.addAll(((ConnectorSource) element)
							.getSourceConnectors());
				}

				if (element instanceof ConnectorTarget) {
					connnectionList.addAll(((ConnectorTarget) element)
							.getTargetConnectors());
				}

				for (Connector connection : connnectionList) {
					Object routingConstraint = connection
							.getRoutingConstraint();
					if (routingConstraint instanceof EMap) {
						oldRoutingConstraint.put(connection, new TreeMap(
								((EMap) routingConstraint).map()));

						((EMap) connection.getRoutingConstraint()).clear();
					}
				}
			}
		});
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void undo() {
		for (Connector connection : oldRoutingConstraint.keySet()) {
			Map constraint = oldRoutingConstraint.get(connection);
			connection.getRoutingConstraint().clear();
			connection.getRoutingConstraint().putAll(constraint);
		}

		oldRoutingConstraint.clear();
	}
}
