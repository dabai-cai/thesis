package cn.zttek.thesis.common.easyui;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * easyUI树形控件节点格式
 */
public class EUTreeNode {

	private long id;
	private String text;
	private String state;
    @JsonInclude(JsonInclude.Include.NON_NULL)
	private List<EUTreeNode> children;
    private boolean leaf;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public List<EUTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<EUTreeNode> children) {
		this.children = children;
	}

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
}
