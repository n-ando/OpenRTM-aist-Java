package jp.go.aist.rtm.systemeditor.ui.editor.editpart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.systemeditor.ui.editor.editpolicy.SystemXYLayoutEditPolicy;
import jp.go.aist.rtm.systemeditor.ui.util.ComponentUtil;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationSupport;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.ui.PlatformUI;

/**
 * システムダイアグラムのEditPartクラス
 */
public class SystemDiagramEditPart extends AbstractEditPart {
	/**
	 * コンストラクタ
	 * 
	 * @param actionRegistry
	 *            ActionRegistry
	 */
	public SystemDiagramEditPart(ActionRegistry actionRegistry) {
		super(actionRegistry);

	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected IFigure createFigure() {
		FreeformLayer result = new FreeformLayer();
		result.setLayoutManager(new FreeformLayout());

		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new SystemXYLayoutEditPolicy());
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List getModelChildren() {
		List<Object> childrens = new ArrayList<Object>();
		List<AbstractComponent> components = new ArrayList<AbstractComponent>();
		for (Iterator iterator = getModel().eContents().iterator(); iterator
				.hasNext();) {
			Object obj = iterator.next();
			childrens.add(obj);
			if (obj instanceof AbstractComponent
					&& ((AbstractComponent) obj).isCompositeComponent()) {
				components.add((AbstractComponent) obj);
			}
		}
		for (AbstractComponent compositeComponent : components) {
			for (Iterator iterator = compositeComponent.getAllComponents()
					.iterator(); iterator.hasNext();) {
				AbstractComponent component = (AbstractComponent) iterator
						.next();
				childrens.remove(component);
				childrens.removeAll(component.eContents());
				if (component.getCorbaBaseObject() != null) {
					AbstractComponent localObjct = (AbstractComponent) SynchronizationSupport
							.findLocalObjectByRemoteObject(
									new Object[] { component
											.getCorbaBaseObject() }, getModel());
					if (localObjct != null) {
						childrens.remove(localObjct);
						childrens.removeAll(localObjct.eContents());
					}
				}
			}
		}
		ComponentUtil.createSpecificationConnector(getModel(), true);
		return childrens;
	}

	/**
	 * {@inheritDoc}
	 */
	public void notifyChanged(Notification notification) {
		if (notification.getOldValue() instanceof ComponentSpecification
				|| notification.getNewValue() instanceof ComponentSpecification) {
			refreshSpecificationConnector();
		}
		refreshSystemDiagram();
	}

	private void refreshSpecificationConnector() {
		ComponentUtil.createSpecificationConnector(getModel(), true);
	}

	public void refreshSystemDiagram() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (isActive()) {
					refreshChildren();
				}
			}
		});
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public SystemDiagram getModel() {
		return (SystemDiagram) super.getModel();
	}
}
