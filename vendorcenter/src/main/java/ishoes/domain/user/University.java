/**
 * 
 */
package ishoes.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 大学实体
 * 
 * @author jayd
 * @since 1.0
 */
public class University implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5631084156680062210L;
	//大学对象ID
	private long university_id;
	//名称
	private String name;
	//属于的省
	private Province province;

	/**
	 * getter方法
	 * 
	 * @return University对象ID
	 */
	public long getUniversity_id() {
		return university_id;
	}

	/**
	 * setter方法
	 * 
	 * @param university_id
	 *            University对象ID
	 */
	public void setUniversity_id(long university_id) {
		this.university_id = university_id;
	}

	/**
	 * getter方法
	 * 
	 * @return University对象name属性
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter方法
	 * 
	 * @param name
	 *            University对象name属性
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter方法
	 * 
	 * @return University对象关联的Province对象
	 */
	public Province getProvince() {
		return province;
	}

	/**
	 * setter方法
	 * 
	 * @param province
	 *            University对象关联的Province对象
	 */
	public void setProvince(Province province) {
		this.province = province;
	}

	/**
	 * 利用org.apache.commons.lang.builder.ToStringBuilder重写了toString方法。
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("university_id", university_id).append("name", name).toString();
	}

	/**
	 * 利用org.apache.commons.lang.builder.HashCodeBuilder重写了hashCode方法。
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(university_id).append(name).toHashCode();
	}

	/**
	 * 利用org.apache.commons.lang.builder.EqualsBuilder重写了equals方法。
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof University)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		University rhs = (University) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(name, rhs.name).isEquals();
	}
}
