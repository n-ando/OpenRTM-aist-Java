package jp.go.aist.rtm.repositoryView.ui;


import java.util.ArrayList;

import jp.go.aist.rtm.repositoryView.model.RepositoryViewItem;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ArrayContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ArrayList) {
			Object[] obj = new Object[((ArrayList)parentElement).size()];
			
			for (int intIdx=0; intIdx < ((ArrayList)parentElement).size(); intIdx++) {
				obj[intIdx] = ((ArrayList)parentElement).get(intIdx);
			}
		    return obj;
		}
		return ((RepositoryViewItem)parentElement).getChildren().toArray();
	}

	public Object getParent(Object element) {
		if (element instanceof ArrayList) return null;

		Object obj = new Object();
		obj = ((RepositoryViewItem)element).getParent();
		return obj;
	}
	
	public boolean hasChildren(Object element) {
	    //(自分内にディレクトリがあるかどうか)
	    return getChildren(element).length > 0;
	}

	public Object[] getElements(Object inputElement) {
	    return getChildren(inputElement);
	}

	public void dispose() {
		
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

}
