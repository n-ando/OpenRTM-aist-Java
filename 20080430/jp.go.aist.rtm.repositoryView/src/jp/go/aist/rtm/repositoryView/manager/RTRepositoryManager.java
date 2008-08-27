package jp.go.aist.rtm.repositoryView.manager;

import java.util.ArrayList;

import jp.go.aist.rtm.repositoryView.model.RepositoryViewItem;
import jp.go.aist.rtm.repositoryView.repository.RTRepositoryAccesser;


/**
 * RTリポジトリのリストを管理するマネージャ
 */
public class RTRepositoryManager {

	/**
	 * RTリポジトリのシングルトンインスタンス
	 */
	private static RTRepositoryManager __instance = new RTRepositoryManager();

	/**
	 * 追加済みリポジトリ・サーバのリスト
	 */
	private ArrayList<String> repositoryList = new ArrayList<String>();

	/**
	 * RTリポジトリのシングルトンインスタンスを取得する
	 */
	public static RTRepositoryManager getInstance() {
		return __instance;
	}

	/**
	 * RTリポジトリを追加する。
	 * 
	 * @param repositoryServer
	 * @return 追加したRTリポジトリ
	 */
	public RepositoryViewItem addRepository(String repositoryServer) {
		if( isExist(repositoryServer) ) return null;

		RepositoryViewItem rootItem = RTRepositoryAccesser.getInstance().getRepositoryServerRoot(repositoryServer);
		repositoryList.add(repositoryServer);
		return rootItem;
	}

	/**
	 * 対象のRTリポジトリ名が、存在するかどうか。
	 * 
	 * @param RTRepository
	 *            確認するRTリポジトリ
	 * @return RTリポジトリ名が存在するかどうか
	 */
	public boolean isExist(String RTRepository) {
//		return repositoryList.contains(RTRepository);
		return false;
	}
}
