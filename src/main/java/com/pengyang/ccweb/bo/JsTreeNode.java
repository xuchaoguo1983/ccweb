package com.pengyang.ccweb.bo;

import java.util.List;

public class JsTreeNode {
	private String id;
	private String text;
	private int index;
	private List<JsTreeNode> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<JsTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<JsTreeNode> children) {
		this.children = children;
	}
}
