/**
 * 
 */
package ishoes.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 用户联系信息实体
 * 
 * @author jayd
 * @since 1.0
 */
public class UserContact implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3639160462317382864L;
	//联系信息对象ID
	private long contact_id;
	//工作地点
	private String workplace;
	//毕业大学
	private String university;
	//高中
	private String high_school;
	//关联的哪个用户（一对一关系）
	private User user;

	/**
	 * getter方法
	 * 
	 * @return UserContact对象ID
	 */
	public long getContact_id() {
		return contact_id;
	}

	/**
	 * setter方法
	 * 
	 * @param contact_id
	 *            UserContact对象ID
	 */
	public void setContact_id(long contact_id) {
		this.contact_id = contact_id;
	}

	/**
	 * getter方法
	 * 
	 * @return UserContact对象workplace属性
	 */
	public String getWorkplace() {
		return workplace;
	}

	/**
	 * setter方法
	 * 
	 * @param workplace
	 *            UserContact对象workplace属性
	 */
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	/**
	 * getter方法
	 * 
	 * @return UserContact对象关联的university对象
	 */
	public String getUniversity() {
		return university;
	}

	/**
	 * setter方法
	 * 
	 * @param university
	 *            UserContact对象所关联的university对象
	 */
	public void setUniversity(String university) {
		this.university = university;
	}

	/**
	 * getter方法
	 * 
	 * @return UserContact对象high_school属性
	 */
	public String getHigh_school() {
		return high_school;
	}

	/**
	 * setter方法
	 * 
	 * @param high_school
	 *            UserContact对象high_school属性
	 */
	public void setHigh_school(String high_school) {
		this.high_school = high_school;
	}

	/**
	 * getter方法
	 * 
	 * @return UserContact对象所关联的user对象
	 */
	public User getUser() {
		return user;
	}

	/**
	 * setter方法
	 * 
	 * @param user
	 *            UserContact对象所关联的user对象
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 利用org.apache.commons.lang.builder.ToStringBuilder重写了toString方法。
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("contact_id", contact_id).append("workplace", workplace).append(
				"university", university).append("high_school", high_school).toString();
	}

	/**
	 * 利用org.apache.commons.lang.builder.HashCodeBuilder重写了hashCode方法。
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(contact_id).append(workplace).append(university).append(high_school)
				.toHashCode();
	}

	/**
	 * 利用org.apache.commons.lang.builder.EqualsBuilder重写了equals方法。
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserContact)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		UserContact rhs = (UserContact) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(workplace, rhs.workplace).append(university,
				rhs.university).append(high_school, rhs.high_school).isEquals();
	}
}
