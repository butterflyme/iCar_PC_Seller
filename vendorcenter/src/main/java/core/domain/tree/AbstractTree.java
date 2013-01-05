/**
 * 
 */
package core.domain.tree;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 * <p>
 * hibernate树形结构的骨干类,提供了树节点常用的树形和方法及其实现。该模式是模板模式的实现可以参考
 * JDK集合类的架构。
 * </p>
 * <p>
 * 该抽象类实现了TreeNode接口，实现了一些hibernate树形方便操作的方法。真实的树的实体类可以继承
 * 该骨架类并且实现其抽象方法即可。
 * </p>
 * 
 * @author jayd
 * @since 1.0
 */
public abstract class AbstractTree implements TreeNode {

	//树节点名字
	protected String name;
	//父节点对象，自我关联的对象，利用hibernate的多对一完成关联。
	protected TreeNode parent;
	//子节点对象集合，自我关联，利用hibernate的一对多完成关联。
	protected Set<TreeNode> children = new HashSet<TreeNode>();

	/**
	 * <p>
	 * 修改实体树的名字
	 * </p>
	 * 
	 * @param name
	 *            要修改的名字
	 */
	public void setName(String name) {
		this.name = name;
	}
    /**
     * 获取树节点名字
     */
	abstract public String getName();
    /**
     * 获取该节点父节点对象
     */
	abstract public TreeNode getParent();
    /**
     * 判断该节点是否为根节点
     */
	public boolean isRoot() {
		return (getParent() == null);
	}
    
	public boolean isLeaf() {
		return (this.getChildren().size() == 0);
	}

	/**
	 * <p>
	 * 实现了TreeNode接口的isParentOf方法。
	 * </p>
	 * 
	 * @return true表示是父节点，false表示不是父节点
	 */
	public boolean isParentOf(TreeNode tree) {
		if (tree == null || ((AbstractTree) tree).equals(this)) {
			/* 如果对方为空 */
			return false;
		} else if (this.isLeaf()) {
			/* 如果自己为叶子,则返回FALSE */
			return false;
		} else if (tree.isRoot()) {
			/* 如果对方为根,返回FALSE */
			return false;
		} else {
			AbstractTree bt = (AbstractTree) (tree.getParent());
			if (this.equals(bt)) {
				/* 如果对方的父节点是自己,则返回TRUE */
				return true;
			} else {
				/* 判断对方的父节点是否是自己的孩子,进行递归 */
				return isParentOf(bt);
			}
		}
	}

	public boolean isChildOf(TreeNode tree) {
		return (tree.isParentOf(this));
	}

	public void addChild(TreeNode tree) {
		children.add(tree);
	}

	public void removeChild(TreeNode tree) {
		children.remove(tree);
		((AbstractTree) tree).setParent(null);
	}

	/**
	 * <p>
	 * 实现TreeNode接口的getAllLeaves方法，获取该实体树节点对象的所有叶子节点。
	 * </p>
	 * 
	 * @return 该节点对象的所有叶子节点
	 */
	public Set<TreeNode> getAllLeaves() {
		Set<TreeNode> set_old = this.getAllChildren();
		Set<TreeNode> set = new HashSet<TreeNode>();
		set.addAll(set_old);
		Iterator<TreeNode> itr = set_old.iterator();
		while (itr.hasNext()) {
			AbstractTree bt = (AbstractTree) itr.next();
			if (!bt.isLeaf()) {
				set.remove(bt);
			}
		}
		return set;
	}

	/**
	 * <p>
	 * 得到该节点的所有子结点对象集合。
	 * </p>
	 * 
	 * @return 该节点的所有子结点对象set集合
	 */
	public Set<TreeNode> getAllChildren() {
		Set<TreeNode> set = new HashSet<TreeNode>();
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(this);
		while (!stack.empty()) {
			AbstractTree bt = (AbstractTree) stack.pop();
			set.add(bt);
			Iterator<TreeNode> itr = bt.getChildren().iterator();
			while (itr.hasNext()) {
				AbstractTree btchild = (AbstractTree) itr.next();
				stack.push(btchild);
			}
		}
		set.remove(this);
		return set;
	}

	abstract public Set<TreeNode> getChildren();

	/**
	 * <p>
	 * 设置节点的父节点
	 * </p>
	 * 
	 * @param parent
	 *            该节点需要设置的父节点对象
	 */
	public void setParent(TreeNode parent) {
		this.parent = (AbstractTree) parent;
	}

	/**
	 * <p>
	 * 设置节点的子结点
	 * </p>
	 * 
	 * @param children
	 *            该节点需要设置的子结点对象
	 */
	public void setChildren(Set<TreeNode> children) {
		this.children = children;
	}
}
