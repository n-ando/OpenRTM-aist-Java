package jp.go.aist.rtm.repositoryView.util;

import java.io.File;

import jp.go.aist.rtm.repositoryView.model.RepositoryViewItem;

public class RepositoryViewUtil {
	
	public static String getPath(RepositoryViewItem viewItem) {
		String path = null;
		RepositoryViewItem tempViewItem = viewItem;
		while (tempViewItem != null 
				&& tempViewItem.getParent() != null) {
			tempViewItem = (RepositoryViewItem) tempViewItem.getParent();
		}
		if (tempViewItem != null && tempViewItem.getType() == 1
				&& tempViewItem.getName() != null) {
			path = tempViewItem.getName()
				+ File.separator
				+ viewItem.getName().substring(
							viewItem.getName().lastIndexOf("(") + 1,
							viewItem.getName().lastIndexOf(")"));
		}
		
		return path;
	}
}
	

