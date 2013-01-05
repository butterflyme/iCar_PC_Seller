/**
 * 
 */
package ishoes.domain.tree;

import java.util.HashSet;
import java.util.Set;

import core.domain.tree.AbstractTree;
import core.domain.tree.TreeNode;

/**
 * 实现具体的树的类型,DEMO
 * 
 * @author jayd
 * @since 1.0
 */
public class AssetType extends AbstractTree implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5495433368708645251L;
	//资产类型ID
	protected Long id;
	//资产类型名称
	protected String name;
	//用来在后台构造的class名字
	protected String styleName;
	//父节点对象
	protected TreeNode parent;
	//子结点对象set集合
	protected Set<TreeNode> children = new HashSet<TreeNode>();

	/**
	 * getter方法
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setter方法
	 * 
	 * @param id
	 *            assetType对象id属性
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getter方法
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter方法
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter方法
	 */
	public String getStyleName() {
		return styleName;
	}

	/**
	 * setter方法
	 * 
	 * @param styleName assetType对象styleName属性
	 */
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	/**
	 * getter方法，得到父节点对象
	 */
	public TreeNode getParent() {
		return parent;
	}

	/**
	 * setter方法
	 */
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	/**
	 * getter方法，得到子结点对象集合
	 */
	public Set<TreeNode> getChildren() {
		return children;
	}

	/**
	 * setter方法
	 */
	public void setChildren(Set<TreeNode> children) {
		this.children = children;
	}
}
