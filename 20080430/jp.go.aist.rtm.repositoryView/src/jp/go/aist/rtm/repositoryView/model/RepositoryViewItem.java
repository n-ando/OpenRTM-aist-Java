package jp.go.aist.rtm.repositoryView.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class RepositoryViewItem {
	
	public static final int ROOT_ITEM = 1;
	public static final int CATEGORY_ITEM = 2;
	public static final int LEAF_ITEM = 3;
	
	private String name = null;
	private int type = 0;
	private Object parent;
	private List children;

	public RepositoryViewItem(String name,int type, Object parent, ArrayList children){
		this.name = name;
		this.type = type;
		this.parent = parent;
		this.children = children;
	}
	public RepositoryViewItem(String name,int type){
		this(name,type,null,new ArrayList());
	}
	public RepositoryViewItem(String name,int type, Object parent){
		this(name,type,parent,new ArrayList());
	}
	//
	public String getName(){
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType(){
		return this.type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getParent(){
		return this.parent;
	}
	public void setParent(RepositoryViewItem parent) {
		this.parent = parent;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	//
	public void addChild(RepositoryViewItem child) {
		this.children.add(child);
	}
	
	public RepositoryViewItem getChild(String target) {
		Iterator it = children.iterator();
		while (it.hasNext()) {
			RepositoryViewItem item = (RepositoryViewItem) it.next();
			if( item.getName().equals(target) ) {
				return item;
			}
		}
		return null;
	}

	public String toString(){
		return this.name;
	}
}
