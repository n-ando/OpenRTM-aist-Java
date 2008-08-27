package jp.go.aist.rtm.toolscommon.synchronizationframework.mapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationSupport;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;

/**
 * 多参照のマッピングを定義するためのクラス
 */
public abstract class ManyReferenceMapping extends ReferenceMapping {

	/**
	 * コンストラクタ
	 * 
	 * @param localFeature
	 *            ローカルオブジェクトのフィーチャ
	 */
	public ManyReferenceMapping(EReference localFeature) {
		this(localFeature, false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param localFeature
	 *            ローカルオブジェクトのフィーチャ
	 * @param allowZombie
	 *            ゾンビ（リモートオブジェクトが死んだ状態）でも存在させるか
	 */
	public ManyReferenceMapping(EReference localFeature, boolean allowZombie) {
		super(localFeature, allowZombie);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void syncronizeLocal(LocalObject localObject) {
		List newRemoteLinkList = getNewRemoteLinkList(localObject
				.getSynchronizationSupport().getRemoteObjects());

		List oldRemoteLinkList = getOldRemoteLinkList(localObject);

		List deleteRemoteLinkList = new ArrayList(oldRemoteLinkList);
		removeAll(deleteRemoteLinkList, newRemoteLinkList, this, localObject);

		for (Object remoteLink : deleteRemoteLinkList) {
			((EList) localObject.eGet(getLocalFeature()))
					.remove(getLocalObjectByRemoteLink(localObject, remoteLink));
		}

		List addRemoteLinkList = new ArrayList(newRemoteLinkList);
		removeAll(addRemoteLinkList, oldRemoteLinkList, this, localObject);

		for (java.lang.Object link : addRemoteLinkList) {
			Object[] remoteObjectByRemoteLink = getRemoteObjectByRemoteLink(
					localObject, localObject.getSynchronizationSupport()
							.getRemoteObjects(), link);
			if (remoteObjectByRemoteLink != null) {
				if (isAllowZombie()
						|| SynchronizationSupport
								.ping(remoteObjectByRemoteLink)) {
					LocalObject childNC = loadLocalObjectByRemoteObject(
							localObject, localObject
									.getSynchronizationSupport()
									.getSynchronizationManager(), link,
							remoteObjectByRemoteLink);
					if (childNC != null) {
						((EList) localObject.eGet(getLocalFeature()))
								.add(childNC);
					}
				}
			}
		}
	}

	/**
	 * 最新のリモートオブジェクトのリンクを返すように、オーバーライドされることが意図される
	 * 
	 * @param parentRemoteObjects
	 *            リモートオブジェクト
	 * @return 最新のリモートオブジェクトのリンク
	 */
	public abstract List getNewRemoteLinkList(Object[] parentRemoteObjects);

	/**
	 * 現在使用している、リモートオブジェクトのリンクを返す
	 * <p>
	 * 必要に応じて、オーバーライドすること デフォルト実装は、ローカルオブジェクトに対して、リモートオブジェクトが１つである場合の実装。 // *
	 * 関連オブジェクトなどの複数のリモートオブジェクトが存在する場合には、オーバーライドしなければならない。
	 * 
	 * @param localObject
	 *            ローカルオブジェクト
	 * @return 現在使用している、リモートオブジェクトのリンク
	 */
	public List getOldRemoteLinkList(LocalObject localObject) {
		List result = new ArrayList<Object[]>();
		for (Iterator iter = ((EList) localObject.eGet(getLocalFeature()))
				.iterator(); iter.hasNext();) {
			LocalObject elem = (LocalObject) iter.next();
			if (elem.getSynchronizationSupport().getRemoteObjects().length != 1) {
				throw new UnsupportedOperationException();
			}
			result.add(elem.getSynchronizationSupport().getRemoteObjects()[0]);
		}

		return result;
	}

	/**
	 * リモートオブジェクトのリンクから、ローカルオブジェクトを取得する
	 * 
	 * @param parent
	 *            親のローカルオブジェクト
	 * @param link
	 *            リモートオブジェクトのリンク
	 * @return ローカルオブジェクト
	 */
	public LocalObject getLocalObjectByRemoteLink(LocalObject parent,
			java.lang.Object link) {
		LocalObject result = null;
		for (Iterator iter = ((EList) parent.eGet(getLocalFeature()))
				.iterator(); iter.hasNext();) {
			LocalObject elem = (LocalObject) iter.next();

			if (link
					.equals(elem.getSynchronizationSupport().getRemoteObjects())) {
				result = elem;
				break;
			}
		}

		return result;
	}

	private static void removeAll(List target, List deleteList,
			ReferenceMapping referenceMapping, LocalObject localObject) {

		List tempDeleteList = new ArrayList();
		for (Iterator iter = deleteList.iterator(); iter.hasNext();) {
			java.lang.Object temp1 = iter.next();

			for (Iterator iterator = target.iterator(); iterator.hasNext();) {
				java.lang.Object temp2 = iterator.next();

				if (referenceMapping.isLinkEquals(temp1, temp2)) {
					tempDeleteList.add(temp2);
				}
			}
		}

		for (java.lang.Object deleteBinding : tempDeleteList) {
			target.remove(deleteBinding);
		}
	}
}
