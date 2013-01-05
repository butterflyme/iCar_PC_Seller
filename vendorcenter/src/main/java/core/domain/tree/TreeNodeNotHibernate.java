/**
 * 
 */
package core.domain.tree;

/**
 * <p>
 * 非hibernate所有树形结构的接口,这个接口实现出来的类性能最好.可以提供使用JDBC操作查询出来
 * 封装成对象，进行海量节点的构造树
 * </p>
 * 
 * @author jayd
 * @since 1.0
 */
public interface TreeNodeNotHibernate {
	/**
	 * 获取节点ID
	 * 
	 * @return 节点ID
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
	 * @return class名字，用来方便后台构造树形HTML字符串需要的class属性值
	 */
	String getStyleName();

	/**
	 * 获取父节点ID
	 * 
	 * @return 得到父节点ID
	 */
	Long getParentId();
}
