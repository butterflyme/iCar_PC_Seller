/**
 * 
 */
package ishoes.service.user;


/**
 * search内容content实体的DAO
 * 
 * @author jayd
 * @since 1.0
 */
public interface ContentManager {

	/**
	 * 根据关键字联想查询需要的content实体对象的List集合,调用DAO的associateSearch方法。
	 * 
	 * @param keyword
	 *            客户端传来的关键字
	 * @return 联想到的content实体对象的List集合
	 */
	public String associateSearch(String keyword);
}
