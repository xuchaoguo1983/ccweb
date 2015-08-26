package com.pengyang.ccweb.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeptSelect implements Serializable {
	private static final long serialVersionUID = -5257010663402798821L;
	private int v;
	private String n;
	private int i;
	private List<DeptSelect> s;

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public List<DeptSelect> getS() {
		return s;
	}

	public void setS(List<DeptSelect> s) {
		this.s = s;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public JsTreeNode toJsTreeNode() {
		JsTreeNode node = new JsTreeNode();

		node.setId(String.valueOf(v));
		node.setText(n);
		node.setIndex(i);

		if (s != null && s.size() > 0) {
			List<JsTreeNode> children = new ArrayList<JsTreeNode>();
			node.setChildren(children);

			for (DeptSelect ds : s) {
				children.add(ds.toJsTreeNode());
			}
		}

		return node;
	}
}
