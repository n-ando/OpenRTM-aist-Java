package jp.go.aist.rtm.toolscommon.ui.views.propertysheetview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.util.AdapterUtil;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * RtcのPropertySheetのContentProviderクラス
 */
public class PropertySheetContentProvider extends AdapterImpl implements
		IStructuredContentProvider, ITreeContentProvider {

	private Viewer viewer;

	/**
	 * {@inheritDoc}
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;

		addListener(oldInput, newInput);
	}

	private void addListener(Object oldInput, Object newInput) {
		final PropertySheetContentProvider provider = this;

		if (oldInput != null && oldInput instanceof ComponentWrapper) {
			((ComponentWrapper) oldInput).getComponent().eAdapters().remove(
					provider);
			for (Iterator iter = ((ComponentWrapper) oldInput).getComponent()
					.eAllContents(); iter.hasNext();) {
				EObject element = (EObject) iter.next();
				element.eAdapters().remove(provider);
			}
		}

		if (newInput != null && newInput instanceof ComponentWrapper) {
			((ComponentWrapper) newInput).getComponent().eAdapters().add(
					provider);
			for (Iterator iter = ((ComponentWrapper) newInput).getComponent()
					.eAllContents(); iter.hasNext();) {
				EObject element = (EObject) iter.next();
				element.eAdapters().add(provider);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		addListener(viewer.getInput(), null);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] getElements(Object parent) {
		return getChildren(parent);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getParent(Object child) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] getChildren(Object parent) {
		if (parent instanceof PropertyDescriptorWithSource) {
			return new Object[0];
		} else if (parent instanceof ComponentWrapper) {
			return new Object[] { ((ComponentWrapper) parent).getComponent() };
		} else {
			IPropertySource propertySource = (IPropertySource) AdapterUtil
					.getAdapter(parent, IPropertySource.class);

			List<Object> result = new ArrayList<Object>();

			if (propertySource != null) {
				for (IPropertyDescriptor descriptor : (propertySource)
						.getPropertyDescriptors()) {
					result.add(new PropertyDescriptorWithSource(descriptor,
							propertySource));
				}
			}

			IWorkbenchAdapter workbenchAdapter = (IWorkbenchAdapter) AdapterUtil
					.getAdapter(parent, IWorkbenchAdapter.class);
			if (workbenchAdapter != null) {
				Object[] children = workbenchAdapter.getChildren(parent);
				if (children != null) {
					result.addAll(Arrays.asList(children));
				}
			}

			return result.toArray(new Object[result.size()]);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasChildren(Object parent) {
		return getChildren(parent).length > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public void notifyChanged(
			final org.eclipse.emf.common.notify.Notification msg) {
		if (msg.getOldValue() != this && msg.getNewValue() != this) { // 自分をリスナに追加することによる、変更通知を無視するため
			if (!CorePackage.eINSTANCE.getModelElement_Constraint().equals(
					msg.getFeature())){
				viewer.getControl().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (viewer.getControl().isDisposed() == false) {
							if (Arrays.asList(Notification.SET, Notification.ADD,
									Notification.REMOVE).contains(
									msg.getEventType())) {

								viewer.refresh();
								((TreeViewer)viewer).expandAll();
							}
						}
					}
				});
			}
		}
	}
}
