/**
 * 
 */
package core.utils.tree;

import java.util.ArrayList;
import java.util.List;

import core.domain.tree.TreeNode;

/**
 * <p>
 * 例子treeview的构造工具类,特别适合hibernate通过关联关系查询出来的实体对象的集合转换成树形HTML。
 * </p>
 * 
 * @author jayd
 * @since 1.0
 */
public class TreeBuilder {

	/**
	 * 根据列表数据构造树的数据
	 * 
	 * @param treeList
	 *            继承TreeNode的树形实体类的List集合
	 * @return 返回带有树形结构的HTML代码(这里采用jquery treeview组件)
	 */
	public String build(final List<? extends TreeNode> treeList) {
		StringBuilder sb = new StringBuilder();
		List<TreeNode> rootNodes = new ArrayList<TreeNode>();
		for (TreeNode node : treeList) {
			if (node.getParent() == null) {
				rootNodes.add(node);
			}
		}
		buildChildNode(treeList, rootNodes, sb);
		return sb.toString();
	}

	/**
	 * 构造子节点菜单，附加外部的ul标记
	 * 
	 * @param treeList
	 *            继承TreeNode的树形实体类的List集合
	 * @param rootNodes
	 *            根结点集合
	 * @param sb
	 *            构造子节点的HTML
	 */
	private void buildChildren(final List<? extends TreeNode> treeList, List<TreeNode> rootNodes, StringBuilder sb) {
		sb.append("<ul>");
		buildChildNode(treeList, rootNodes, sb);
		sb.append("</ul>");
	}

	private void buildChildNode(final List<? extends TreeNode> treeList, List<TreeNode> rootNodes, StringBuilder sb) {
		List<TreeNode> child = null;
		for (TreeNode root : rootNodes) {
			sb.append("<li id=\"").append(root.getId()).append("\"><span class=\"").append(root.getStyleName()).append(
					"\">").append(root.getName()).append("</span>");
			child = new ArrayList<TreeNode>();
			for (TreeNode node : treeList) {
				if (node == root || node.getParent() == null) {
					continue;
				}
				if (node.getParent() == root) {
					child.add(node);
				}
			}
			if (!child.isEmpty()) {
				buildChildren(treeList, child, sb);
			}
			sb.append("</li>");
		}
	}
}
