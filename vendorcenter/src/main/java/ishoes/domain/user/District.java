/**
 * 
 */
package ishoes.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 行政区实体。一个市的行政区，如成都市武侯区这样的行政级别
 * 
 * @author jayd
 * @since 1.0
 */
public class District implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4232505573559415007L;
	//行政区ID
	private long district_id;
	//行政区名称
	private String name;
	//行政区编号
	private String code;
	//行政属于的省
	private Province province;

	/**
	 * getter方法
	 * 
	 * @return district对象的ID
	 */
	public long getDistrict_id() {
		return district_id;
	}

	/**
	 * setter方法
	 * 
	 * @param district_id
	 *            district对象的ID
	 */
	public void setDistrict_id(long district_id) {
		this.district_id = district_id;
	}

	/**
	 * getter方法
	 * 
	 * @return district对象的name属性
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter方法
	 * 
	 * @param name
	 *            district对象的name属性
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter方法
	 * 
	 * @return district对象的code属性
	 */
	public String getCode() {
		return code;
	}

	/**
	 * setter方法
	 * 
	 * @param code
	 *            district对象的code属性
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * getter方法
	 * 
	 * @return district对象的关联的province属性
	 */
	public Province getProvince() {
		return province;
	}

	/**
	 * setter方法
	 * 
	 * @param province
	 *            district对象的关联的province属性
	 */
	public void setProvince(Province province) {
		this.province = province;
	}

	/**
	 * 利用org.apache.commons.lang.builder.ToStringBuilder重写了toString方法。
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("district_id", district_id).append("name", name).append("code", code)
				.toString();
	}

	/**
	 * 利用org.apache.commons.lang.builder.HashCodeBuilder重写了hashCode方法。
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(district_id).append(name).append(code).toHashCode();
	}

	/**
	 * 利用org.apache.commons.lang.builder.EqualsBuilder重写了equals方法。
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof District)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		District rhs = (District) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(name, rhs.name).append(code, rhs.code)
				.isEquals();
	}
}
