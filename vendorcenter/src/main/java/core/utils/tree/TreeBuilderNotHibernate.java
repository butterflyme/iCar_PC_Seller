/**
 * 
 */
package core.utils.tree;

import java.util.ArrayList;
import java.util.List;

import core.domain.tree.TreeNodeNotHibernate;

/**
 * <p>
 * 例子treeview的构造工具类,适合非hibernate实体对象的集合转换成树形HTML
 * </p>
 * <p>
 * 非hibernate实体对象集合转换成树形，即为没有通过hibernate的关联关系进行实体对象的关联。
 * </p>
 * <p>
 * 而是采用ID自己进行关联和生成，这样在大批量的节点的情况下，会比利用hibernate的更加高效。
 * </p>
 * 
 * @author jayd
 * @since 1.0
 */

public class TreeBuilderNotHibernate {

	/**
	 * 根据列表数据构造树的数据
	 * 
	 * @param treeList
	 *            继承TreeNode的树形实体类的List集合
	 * @return 返回带有树形结构的HTML代码(这里采用jquery treeview组件)
	 */
	public String build(final List<? extends TreeNodeNotHibernate> treeList) {
		StringBuilder sb = new StringBuilder();
		List<TreeNodeNotHibernate> rootNodes = new ArrayList<TreeNodeNotHibernate>();
		for (TreeNodeNotHibernate node : treeList) {
			if (node.getParentId() == null || node.getParentId() == 0) {
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
	private void buildChildren(final List<? extends TreeNodeNotHibernate> treeList,
			List<TreeNodeNotHibernate> rootNodes, StringBuilder sb) {
		sb.append("<ul>");
		buildChildNode(treeList, rootNodes, sb);
		sb.append("</ul>");
	}

	private void buildChildNode(final List<? extends TreeNodeNotHibernate> treeList,
			List<TreeNodeNotHibernate> rootNodes, StringBuilder sb) {
		List<TreeNodeNotHibernate> child = null;
		for (TreeNodeNotHibernate root : rootNodes) {
			sb.append("<li id=\"").append(root.getId()).append("\"><span class=\"").append(root.getStyleName()).append(
					"\">").append(root.getName()).append("</span>");
			child = new ArrayList<TreeNodeNotHibernate>();
			for (TreeNodeNotHibernate node : treeList) {
				if (node == root || node.getParentId() == null) {
					continue;
				}
				if (node.getParentId() == root.getId()) {
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
