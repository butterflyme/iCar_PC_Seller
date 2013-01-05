/**
 * 
 */
package ishoes.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 国家实体
 * 
 * @author jayd
 * @since 1.0
 */
public class Country implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5246451275904194920L;
	//国家对象ID
	private long country_id;
	//国家名称
	private String name;
	//国家编号
	private String code;

	/**
	 * getter方法
	 * 
	 * @return country对象的ID
	 */
	public long getCountry_id() {
		return country_id;
	}

	/**
	 * setter方法
	 * 
	 * @param country_id
	 *            country对象的ID
	 */
	public void setCountry_id(long country_id) {
		this.country_id = country_id;
	}

	/**
	 * getter方法
	 * 
	 * @return country对象的name属性
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter方法
	 * 
	 * @param name
	 *            country对象的name属性
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter方法
	 * 
	 * @return country对象的code属性
	 */
	public String getCode() {
		return code;
	}

	/**
	 * setter方法
	 * 
	 * @param code
	 *            country对象的code属性
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 利用org.apache.commons.lang.builder.ToStringBuilder重写了toString方法。
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("country_id", country_id).append("name", name).append("code", code)
				.toString();
	}

	/**
	 * 利用org.apache.commons.lang.builder.HashCodeBuilder重写了hashCode方法。
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(country_id).append(name).append(code).toHashCode();
	}

	/**
	 * 利用org.apache.commons.lang.builder.EqualsBuilder重写了equals方法。
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Country)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Country rhs = (Country) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(name, rhs.name).append(code, rhs.code)
				.isEquals();
	}
}
