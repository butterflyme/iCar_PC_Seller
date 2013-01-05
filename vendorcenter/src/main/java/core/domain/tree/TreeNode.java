/**
 * 
 */
package core.domain.tree;

import java.util.Set;

/**
 * <p>
 * hibernate所有树形结构的接口,提供常用树形的操作,具体实现是由该接口结合AbstractTree（骨干）
 * 形成骨架结构（类似JDK中的集合类体系结构）
 * </p>
 * 
 * @author jayd
 * @since 1.0
 */
public interface TreeNode {
	/**
	 * 获取节点ID
	 */
	Long getId();

	/**
	 * 获取名称
	 * 
	 * @return 树节点名称
	 */
	String getName();

	/**
	 * 获取样式,有些树在后台构造HTML的时候可能需要一些class的名字，好让前台进行渲染，比如说jquery.treeview这个组件
	 * 
	 * @return class名字
	 */
	String getStyleName();

	/**
	 * 获取父节点
	 * 
	 * @return 该节点的父节点对象
	 */
	TreeNode getParent();

	/**
	 * 是否为根节点
	 * 
	 * @return true表示为根节点,false表示不是根节点
	 */
	boolean isRoot();

	/**
	 * 是否为叶子节点
	 * 
	 * @return true表示为叶子节点，false表示不是叶子节点
	 */
	boolean isLeaf();

	/**
	 * 判断是该节点是否为传入参数节点的父节点
	 * 
	 * @param tree
	 *            传入需要进行判断的节点
	 * @return true表示该节点是传入参数tree节点的父节点，false表示不是
	 */
	boolean isParentOf(TreeNode tree);

	/**
	 * 判断是该节点是否为传入参数节点的子节点
	 * 
	 * @param tree
	 *            传入需要进行判断的节点
	 * @return true表示该节点是传入参数tree节点的子节点，false表示不是
	 */
	boolean isChildOf(TreeNode tree);

	/**
	 * 在对象中增加该节点的子节点
	 * 
	 * @param tree
	 *            作为该节点儿子的节点
	 */
	void addChild(TreeNode tree);

	/**
	 * 删除该节点的子节点
	 * 
	 * @param tree
	 *            该节点儿子的节点
	 */
	void removeChild(TreeNode tree);

	/**
	 * 得到该节点对象所有子节点
	 * 
	 * @return 所有子节点
	 */
	Set<TreeNode> getAllChildren();

	/**
	 * 得到该节点的直接子节点
	 * 
	 * @return 所有直接子节点
	 */
	Set<TreeNode> getChildren();

	/**
	 * 得到该节点子节点中的所有叶子节点
	 * 
	 * @return 该节点子节点中的所有叶子节点
	 */
	Set<TreeNode> getAllLeaves();
}
