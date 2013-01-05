/**
 * 
 */
package ishoes.domain.tree;

import core.domain.tree.TreeNodeNotHibernate;

/**
 * <p>
 * 实现TreeNodeNotHibernate的树接口的具体实体,可以做到高性能的树的构造.减少的大对象的创建。
 * </p>
 * 
 * @author jayd
 * @since 1.0
 */
public class Asset implements TreeNodeNotHibernate, java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4421391360061543501L;
	//asset对象ID
	protected Long id;
	//名字
	protected String name;
	//用来在后台构造的class名字
	protected String styleName;
	//父节点ID
	protected Long parentId;

	/**
	 * getter方法得到asset对象ID属性
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setter方法
	 * 
	 * @param id asset对象ID属性
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
	 * 
	 * @param name asset对象name属性
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
	 * @param styleName asset对象styleName属性
	 */
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	/**
	 * getter方法，得到父节点ID
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * setter方法，设置父节点ID
	 * 
	 * @param parentId asset对象parentId属性
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
