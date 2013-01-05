/**
 * 
 */
package ishoes.domain.user;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 用户实体
 * 
 * @author jayd
 * @since 1.0
 */
public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8095422847578508539L;
	//用户对象ID
	private long user_id;
	//用户邮件，用来登陆
	private String email;
	//用户密码
	private String password;
	//用户状态，是否激活
	private int status;
	//用户真实姓名
	private String real_name;
	//用户生日
	private Date birthdate;
	//用户头像图片链接地址
	private String head_picture;
	//用户与角色的多对多关联集合
	private Set<Role> roles;
	//用户的联系信息对象
	private UserContact userContact;

	/**
	 * gette方法
	 * 
	 * @return User对象ID
	 */
	public long getUser_id() {
		return user_id;
	}

	/**
	 * setter方法
	 * 
	 * @param user_id
	 *            User对象ID
	 */
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	/**
	 * gette方法
	 * 
	 * @return User对象email属性
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * setter方法
	 * 
	 * @param email
	 *            User对象email属性
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * gette方法
	 * 
	 * @return User对象password属性
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * setter方法
	 * 
	 * @param password
	 *            User对象password属性
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * gette方法
	 * 
	 * @return User对象status属性
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * setter方法
	 * 
	 * @param status
	 *            User对象status属性
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * gette方法
	 * 
	 * @return User对象real_name属性
	 */
	public String getReal_name() {
		return real_name;
	}

	/**
	 * setter方法
	 * 
	 * @param real_name
	 *            User对象real_name属性
	 */
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	/**
	 * gette方法
	 * 
	 * @return User对象birthdate属性
	 */
	public Date getBirthdate() {
		return birthdate;
	}

	/**
	 * setter方法
	 * 
	 * @param birthdate
	 *            User对象birthdate属性
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * gette方法
	 * 
	 * @return User对象head_picture属性
	 */
	public String getHead_picture() {
		return head_picture;
	}

	/**
	 * setter方法
	 * 
	 * @param head_picture
	 *            User对象head_picture属性
	 */
	public void setHead_picture(String head_picture) {
		this.head_picture = head_picture;
	}

	/**
	 * gette方法
	 * 
	 * @return User对象所关联的Role对象集合
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * setter方法
	 * 
	 * @param roles
	 *            所关联的Role对象集合
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * gette方法
	 * 
	 * @return User对象关联的userContact对象
	 */
	public UserContact getUserContact() {
		return userContact;
	}

	/**
	 * setter方法
	 * 
	 * @param userContact
	 *            User对象关联的userContact对象
	 */
	public void setUserContact(UserContact userContact) {
		this.userContact = userContact;
	}

	/**
	 * 利用org.apache.commons.lang.builder.ToStringBuilder重写了toString方法。
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("user_id", user_id).append("email", email).append("password", password)
				.append("status", status).append("real_name", real_name).append("head_picture", head_picture)
				.toString();
	}

	/**
	 * 利用org.apache.commons.lang.builder.HashCodeBuilder重写了hashCode方法。
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(user_id).append(email).append(password).append(status).append(real_name)
				.append(head_picture).toHashCode();
	}

	/**
	 * 利用org.apache.commons.lang.builder.EqualsBuilder重写了equals方法。
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		User rhs = (User) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(email, rhs.email).append(password,
				rhs.password).append(status, rhs.status).append(real_name, rhs.real_name).append(head_picture,
				rhs.head_picture).isEquals();
	}
}
