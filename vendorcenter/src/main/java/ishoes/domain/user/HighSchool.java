/**
 * 
 */
package ishoes.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 高中实体
 * 
 * @author jayd
 * @since 1.0
 */
public class HighSchool implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6631053427134053186L;
	//高中ID
	private long highschool_id;
	//高中名称
	private String name;
	//高中属于的行政区
	private District district;

	/**
	 * getter方法
	 * 
	 * @return HighSchool对象的ID
	 */
	public long getHighschool_id() {
		return highschool_id;
	}

	/**
	 * setter方法
	 * 
	 * @param highschool_id
	 *            HighSchool对象的ID
	 */
	public void setHighschool_id(long highschool_id) {
		this.highschool_id = highschool_id;
	}

	/**
	 * getter方法
	 * 
	 * @return HighSchool对象的name属性
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter方法
	 * 
	 * @param name
	 *            HighSchool对象的name属性
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter方法
	 * 
	 * @return HighSchool对象的关联的District对象
	 */
	public District getDistrict() {
		return district;
	}

	/**
	 * setter方法
	 * 
	 * @param district
	 *            HighSchool对象的关联的District对象
	 */
	public void setDistrict(District district) {
		this.district = district;
	}

	/**
	 * 利用org.apache.commons.lang.builder.ToStringBuilder重写了toString方法。
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("highschool_id", highschool_id).append("name", name).toString();
	}

	/**
	 * 利用org.apache.commons.lang.builder.HashCodeBuilder重写了hashCode方法。
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(highschool_id).append(name).toHashCode();
	}

	/**
	 * 利用org.apache.commons.lang.builder.EqualsBuilder重写了equals方法。
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof HighSchool)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		HighSchool rhs = (HighSchool) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(name, rhs.name).isEquals();
	}
}
