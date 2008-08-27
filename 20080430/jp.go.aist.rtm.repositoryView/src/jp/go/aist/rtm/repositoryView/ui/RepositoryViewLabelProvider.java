package jp.go.aist.rtm.repositoryView.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import jp.go.aist.rtm.repositoryView.RepositoryViewPlugin;
import jp.go.aist.rtm.repositoryView.model.RTCRVLeafItem;
import jp.go.aist.rtm.repositoryView.model.RTModelRVLeafItem;
import jp.go.aist.rtm.repositoryView.model.RTSenarioRVLeafItem;
import jp.go.aist.rtm.repositoryView.model.RTSystemRVLeafItem;
import jp.go.aist.rtm.repositoryView.model.RepositoryViewItem;
import jp.go.aist.rtm.repositoryView.model.ServerRVRootItem;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class RepositoryViewLabelProvider extends LabelProvider {
	private Image image = null;
	
	public String getText(Object obj) {
		if (obj instanceof RepositoryViewItem) {
			return ((RepositoryViewItem)obj).toString();
		}
		return ""; 
	}
	
	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;

		if (obj instanceof ArrayList) {
			imageKey = ISharedImages.IMG_TOOL_BACK;
		} else {
			RepositoryViewItem item = ((RepositoryViewItem)obj);
			ImageDescriptor descPort;
			String imageName = null;
			if (item.getType() == RepositoryViewItem.ROOT_ITEM) {
				if(item instanceof ServerRVRootItem) {
					imageName = "icon/Server.png";
				} else {
					imageKey = ISharedImages.IMG_OBJ_FILE;
					return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
				}
			} else if (item.getType() == RepositoryViewItem.CATEGORY_ITEM) {
				imageName = "icon/Category.png";
			} else if (item.getType() == RepositoryViewItem.LEAF_ITEM) {
				if(item instanceof RTSystemRVLeafItem) {
					imageName = "icon/RTsystem.png";
				} else if(item instanceof RTSenarioRVLeafItem) {
					imageName = "icon/welcome_editor.gif";
				} else if(item instanceof RTModelRVLeafItem) {
					imageName = "icon/showchild_mode.gif";
				} else {
					if( ((RTCRVLeafItem)item).getComponent().isSpecUnLoad() ) {
						imageName = "icon/ComponentD.png";
					} else {
						imageName = "icon/Component.png";
					}
				}
			}
			try {
				URL url = RepositoryViewPlugin.getDefault().getBundle().getEntry("/");
				descPort = ImageDescriptor.createFromURL(new URL(url ,imageName));
				image = descPort.createImage();
				return image;
			} catch(MalformedURLException e) {
				descPort = ImageDescriptor.getMissingImageDescriptor();
			}
		}
		return null;
	}

	@Override
	public void dispose() {
		if( image != null && !image.isDisposed() ){
			image.dispose();
		}
	}
}
