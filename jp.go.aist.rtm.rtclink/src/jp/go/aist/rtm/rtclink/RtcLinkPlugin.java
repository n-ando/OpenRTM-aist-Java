package jp.go.aist.rtm.rtclink;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class RtcLinkPlugin extends AbstractUIPlugin {
	public static final String PLUGIN_ID = "jp.go.aist.rtm.rtclink";

	// The shared instance.
	private static RtcLinkPlugin plugin;

	/**
	 * The constructor.
	 */
	public RtcLinkPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static RtcLinkPlugin getDefault() {
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
		return AbstractUIPlugin.imageDescriptorFromPlugin(
				"jp.go.aist.rtm.rtclink", path);
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
			result = (Image) RtcLinkPlugin.getDefault().getImageRegistry().get(
					descriptor.toString());
			if (result == null) {
				result = descriptor.createImage();
				RtcLinkPlugin.getDefault().getImageRegistry().put(
						descriptor.toString(), result);
			}
		}

		return result;
	}
}
