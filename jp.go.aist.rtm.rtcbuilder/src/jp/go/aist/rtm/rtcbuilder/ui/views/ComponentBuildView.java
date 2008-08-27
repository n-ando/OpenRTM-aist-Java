package jp.go.aist.rtm.rtcbuilder.ui.views;

import java.util.Iterator;

import jp.go.aist.rtm.rtcbuilder.RtcBuilderPlugin;
import jp.go.aist.rtm.rtcbuilder.model.component.BuildView;
import jp.go.aist.rtm.rtcbuilder.model.component.Component;
import jp.go.aist.rtm.rtcbuilder.ui.editpart.factory.RtcBuilderEditPartFactory;
import jp.go.aist.rtm.rtcbuilder.util.AdapterUtil;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class ComponentBuildView extends ViewPart {

    /**
     * ComponentBuildView のID
     */
    public static final String VIEW_ID = RtcBuilderPlugin.PLUGIN_ID + ".buildeview";

    private DefaultEditDomain editDomain;
	private GraphicalViewer graphicalViewer;
	private BuildView targetComponent;

	public ComponentBuildView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		setEditDomain(new DefaultEditDomain(null));
		setGraphicalViewer(new ScrollingGraphicalViewer());
		getGraphicalViewer().createControl(parent);
		getGraphicalViewer().setRootEditPart(
				new FreeformGraphicalRootEditPart());
		getGraphicalViewer().setEditPartFactory(new RtcBuilderEditPartFactory());
		getGraphicalViewer().getControl().setBackground(ColorConstants.listBackground);
		//
		getSite().getWorkbenchWindow().getSelectionService()
			.addSelectionListener(selectionListener);
		//
		BuildView oldComponent = targetComponent;
		targetComponent = null;
		ISelection selection = getSite().getWorkbenchWindow().getSelectionService().getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;

			if (AdapterUtil.getAdapter(sSelection.getFirstElement(),
					BuildView.class) != null) {
				targetComponent = (BuildView) AdapterUtil.getAdapter(
						sSelection.getFirstElement(), BuildView.class);
				getGraphicalViewer().setContents(targetComponent);
				if( targetComponent != oldComponent ) {
					attachListener(oldComponent, targetComponent);
				}
			}
		}
	}

	/**
	 * モデルへのリスナ
	 */
	protected Adapter modelListener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification msg) {
			if( msg.getNewValue() != null ) {
				if( msg.getNotifier() instanceof Component ) {
					targetComponent.getComponents().set(0, msg.getNotifier());
					getGraphicalViewer().setContents(targetComponent);
				}
			}
		}
	};
	/**
	 * 選択を監視するリスナ
	 */
	private ISelectionListener selectionListener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			BuildView oldComponent = targetComponent;
			targetComponent = null;
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection sSelection = (IStructuredSelection) selection;

				if (AdapterUtil.getAdapter(sSelection.getFirstElement(),
						BuildView.class) != null) {
					targetComponent = (BuildView) AdapterUtil.getAdapter(
							sSelection.getFirstElement(), BuildView.class);
					getGraphicalViewer().setContents(targetComponent);
					if( targetComponent != oldComponent ) {
						attachListener(oldComponent, targetComponent);
					}
				}
			}
		}
	};

	private void attachListener(EObject targetOld, EObject targetNew) {
		if( targetOld != null ) {
			for( Iterator iter = targetOld.eAllContents(); iter.hasNext();) {
				EObject element = (EObject) iter.next();
				element.eAdapters().remove(modelListener);
//				element.eAdapters().get(0)
			}
		}
		for( Iterator iter = targetNew.eAllContents(); iter.hasNext();) {
			EObject element = (EObject) iter.next();
			element.eAdapters().add(modelListener);
		}
	}
	
	/**
	 * Sets the EditDomain for this ViewPart.
	 * 
	 * @param anEditDomain
	 *            the EditDomain for this ViewPart.
	 */
	protected void setEditDomain(DefaultEditDomain anEditDomain) {
		this.editDomain = anEditDomain;
	}

	/**
	 * Get the EditDomain for this ViewPart.
	 * 
	 * @return the EditDomain for this ViewPart.
	 */
	protected DefaultEditDomain getEditDomain() {
		return this.editDomain;
	} 

	/**
	 * Sets the graphicalViewer for this EditorPart.
	 * 
	 * @param viewer
	 *            the graphical viewer
	 */
	protected void setGraphicalViewer(GraphicalViewer viewer) {
		getEditDomain().addViewer(viewer);
		this.graphicalViewer = viewer;
	}

	/**
	 * Returns the graphical viewer.
	 * 
	 * @return the graphical viewer
	 */
	protected GraphicalViewer getGraphicalViewer() {
		return this.graphicalViewer;
	}

	@Override
	public void setFocus() {
	}

}
