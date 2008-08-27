package jp.go.aist.rtm.toolscommon;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class ToolsCommonPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "jp.go.aist.rtm.toolscommon";

	// The shared instance
	private static ToolsCommonPlugin plugin;
	
	/**
	 * The constructor
	 */
	public ToolsCommonPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ToolsCommonPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path.
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public static Image getCachedImage(String path) {
		Image result = getDefault().getImageRegistry().get(path);
		if (result == null) {
			result = getImageDescriptor(path).createImage();
			getDefault().getImageRegistry().put(path, result);
		}

		return result;
	}

	public static Image getCachedImage(ImageDescriptor descriptor) {
		Image result = null;
		if (descriptor != null) {
			result = (Image) getDefault().getImageRegistry().get(
					descriptor.toString());
			if (result == null) {
				result = descriptor.createImage();
				getDefault().getImageRegistry().put(
						descriptor.toString(), result);
			}
		}

		return result;
	}
}
