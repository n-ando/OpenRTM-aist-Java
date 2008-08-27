package jp.go.aist.rtm.rtclink.ui.editor.editpolicy;

import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.model.component.SystemDiagram;
import jp.go.aist.rtm.rtclink.model.core.ModelElement;
import jp.go.aist.rtm.rtclink.synchronizationframework.SynchronizationSupport;
import jp.go.aist.rtm.rtclink.ui.editor.command.ChangeConstraintCommand;
import jp.go.aist.rtm.rtclink.ui.editor.command.CreateCommand;
import jp.go.aist.rtm.rtclink.ui.util.Draw2dUtil;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

/**
 * システムダイアグラムに関するEditPolicyクラス
 */
public class SystemXYLayoutEditPolicy extends XYLayoutEditPolicy {

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command createAddCommand(EditPart child, Object constraint) {
		return null;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		ChangeConstraintCommand command = new ChangeConstraintCommand();
		command.setModel((ModelElement) child.getModel());
		command.setConstraint((Rectangle) constraint);

		return command;
	}

	@Override
	/**
	 * {@inheritDoc}
	 * <p>
	 * 既に存在するオブジェクトは登録できないよう実装してある。
	 */
	protected Command getCreateCommand(CreateRequest request) {
		Component newObject = (Component) request.getNewObject();
		newObject.setConstraint(Draw2dUtil
				.toRtcLinkRectangle((Rectangle) getConstraintFor(request)));

		boolean isExist = true;
		if (newObject != null
				&& SynchronizationSupport.findLocalObjectByRemoteObject(
						new Object[] { newObject.getCorbaBaseObject() },
						(EObject) getHost().getModel()) == null) {
			isExist = false;
		}

		CreateCommand command = null;
		if (isExist == false) {
			command = new CreateCommand();
			command.setParent((SystemDiagram) getHost().getModel());

			command.setTarget(newObject);
		}

		return command;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command getDeleteDependantCommand(Request request) {
		return null;
	}

}
