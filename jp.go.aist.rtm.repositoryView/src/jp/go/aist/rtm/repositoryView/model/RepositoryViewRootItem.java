package jp.go.aist.rtm.repositoryView.model;


public class RepositoryViewRootItem extends RepositoryViewItem {

	public static final String LOCAL_ROOT = "Local";
	public static final String SERVER_ROOT = "Server";

	public RepositoryViewRootItem(String name) {
		super(name, RepositoryViewItem.ROOT_ITEM);
	}
}
