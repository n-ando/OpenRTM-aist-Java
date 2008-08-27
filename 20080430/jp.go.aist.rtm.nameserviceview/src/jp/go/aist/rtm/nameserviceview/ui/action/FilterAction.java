package jp.go.aist.rtm.nameserviceview.ui.action;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.nameserviceview.model.nameservice.CategoryNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.HostNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.ManagerNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.ModuleNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.CategoryNamingContextImpl;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.HostNamingContextImpl;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ManagerNamingContextImpl;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ModuleNamingContextImpl;
import jp.go.aist.rtm.nameserviceview.ui.dialog.FiltersDialog;
import jp.go.aist.rtm.nameserviceview.ui.views.nameserviceview.NameServiceView;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.util.AdapterUtil;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.omg.CosNaming.Binding;

public class FilterAction implements IViewActionDelegate {
	private NameServiceView view;
	/**
	 * {@inheritDoc}
	 */
	public void init(IViewPart view) {
		this.view = (NameServiceView) view;
		setViewerFilters();
		
	}

	public void run(IAction action) {
		try {
			FiltersDialog  filterDialog = new FiltersDialog(view
					.getSite().getShell());
			if (filterDialog.open() == Dialog.OK) {
				setViewerFilters();
				
			}
		} catch (Exception e) {
			MessageDialog
					.openInformation(
							view
							.getSite().getShell(),
							"error.system.header", "error.system.message"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private void setViewerFilters() {
		List<ViewerFilter> filters = getFilters();
		if (FiltersDialog.loadPatternEnable()) {
			filters.add(new ViewerFilter() {
					@Override
					public boolean select(Viewer viewer, Object parent, Object e) {
						return patternFileter(e);
					}
				});
		}
		view.getViewer().resetFilters();
		for (ViewerFilter filter : filters) {
			view.getViewer().addFilter(filter);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// void
		
	}
	private boolean patternFileter(Object element) {
		
		String pattern = FiltersDialog.loadDefaultPattern();
		if ("".equals(pattern)) {
			return true;
		}
		String matchName = null;
		IWorkbenchAdapter workbenchAdapter = (IWorkbenchAdapter) AdapterUtil
				.getAdapter(element, IWorkbenchAdapter.class);
		if (workbenchAdapter != null) {
			matchName = workbenchAdapter.getLabel(element);
		} else {
			return true;
		}
		if (FiltersDialog.isStartsWith()){
			return !matchName.startsWith(pattern);
		}else{
			return !matchName.contains(pattern);
		}
	}
	private List<ViewerFilter> getFilters(){
		List<String> filterTarget = FiltersDialog.loadDefaultFilters();
		List<ViewerFilter> filters = new ArrayList<ViewerFilter>();
		for (String target : filterTarget) {
			if (FiltersDialog.COMPONENT.equals(target)) {
				filters.add(new ViewerFilter() {
					@Override
					public boolean select(Viewer viewer, Object parent, Object e) {
						if (e instanceof NamingObjectNode) {
							Object component = AdapterUtil.getAdapter(e,
									AbstractComponent.class);
							if (component instanceof Component
									&& !((Component)component).isCompositeComponent()) {
								return false;
							}
						}
						return true;
					}
				});
			} else if (FiltersDialog.COMPOSITE_COMPONENT.equals(target)) {
				filters.add(new ViewerFilter() {
					@Override
					public boolean select(Viewer viewer, Object parent, Object e) {
						if (e instanceof NamingObjectNode) {
							Object component = (Component) AdapterUtil
									.getAdapter(e, AbstractComponent.class);
							if ((component instanceof Component && ((Component) component)
									.isCompositeComponent())) {
								return false;
							}
						}
						return true;
					}
				});
			} else if (FiltersDialog.OBJECT.equals(target)) {
				filters.add(new ViewerFilter() {
					@Override
					public boolean select(Viewer viewer, Object parent, Object e) {
						if (e instanceof NamingObjectNode) {
							Object component = (Component) AdapterUtil
									.getAdapter(e, AbstractComponent.class);
							if (!(component instanceof Component)) {
								return false;
							}
						}
						return true;
					}
				});
			} else if (FiltersDialog.HOST_NAMING_CONTEXT.equals(target)) {
				filters.add(new ViewerFilter() {
					@Override
					public boolean select(Viewer viewer, Object parent, Object e) {
						if (e instanceof HostNamingContext) {
							return false;
						}
						return true;
					}
				});
			} else if (FiltersDialog.MANAGER_NAMING_CONTEXT.equals(target)) {
				filters.add(new ViewerFilter() {
					@Override
					public boolean select(Viewer viewer, Object parent, Object e) {
						if (e instanceof ManagerNamingContext) {
							return false;
						}
						return true;
					}
				});
			} else if (FiltersDialog.CATEGORY_NAMING_CONTEXT.equals(target)) {
				filters.add(new ViewerFilter() {
					@Override
					public boolean select(Viewer viewer, Object parent, Object e) {
						if (e instanceof CategoryNamingContext) {
							return false;
						}
						return true;
					}
				});
			} else if (FiltersDialog.MODULE_NAMING_CONTEXT.equals(target)) {
				filters.add(new ViewerFilter() {
					@Override
					public boolean select(Viewer viewer, Object parent, Object e) {
						if (e instanceof ModuleNamingContext) {
							return false;
						}
						return true;
					}
				});
			} else if (FiltersDialog.FOLDER.equals(target)) {
				filters.add(new ViewerFilter() {
					@Override
					public boolean select(Viewer viewer, Object parent, Object e) {
						List<String> kinds = new ArrayList<String>();
						kinds.add(HostNamingContextImpl.KIND);
						kinds.add(ManagerNamingContextImpl.KIND);
						kinds.add(CategoryNamingContextImpl.KIND);
						kinds.add(ModuleNamingContextImpl.KIND);
						if (e instanceof NamingContextNode) {
							if (((NamingContextNode) e)
									.getNameServiceReference() != null) {
								Binding binding = ((NamingContextNode) e)
										.getNameServiceReference().getBinding();
								if (binding != null) {
									if (binding.binding_name.length > 0) {
										return kinds
												.contains(binding.binding_name[binding.binding_name.length - 1]);
									}
								}
							}
						}
						return true;
					}
				});
			} else if (FiltersDialog.ZOMBIE_OBJECT.equals(target)) {
				filters.add(new ViewerFilter() {
					@Override
					public boolean select(Viewer viewer, Object parent, Object e) {
						if (e instanceof NamingObjectNode) {
							NamingObjectNode node = (NamingObjectNode)e;
							if( node.isZombie() ) {
								return false;
							}
						}
						return true;
					}
				});
			}
		}
		return filters;
	}
}
