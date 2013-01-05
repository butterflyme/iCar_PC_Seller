/**
 * 
 */
package ishoes.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 省或是州实体类
 * 
 * @author jayd
 * @since 1.0
 */
public class Province implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4642294357374789004L;
	//省或州的ID
	private long province_id;
	//名称
	private String name;
	//编号
	private String code;
	//属于的国家
	private Country country;

	/**
	 * getter方法
	 * 
	 * @return Province对象的ID
	 */
	public long getProvince_id() {
		return province_id;
	}

	/**
	 * setter方法
	 * 
	 * @param province_id
	 *            Province对象的ID
	 */
	public void setProvince_id(long province_id) {
		this.province_id = province_id;
	}

	/**
	 * getter方法
	 * 
	 * @return Province对象的name属性
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter方法
	 * 
	 * @param name
	 *            Province对象的name属性
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter方法
	 * 
	 * @return Province对象的code属性
	 */
	public String getCode() {
		return code;
	}

	/**
	 * setter方法
	 * 
	 * @param code
	 *            Province对象的code属性
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * getter方法
	 * 
	 * @return Province对象的关联的Country对象
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * setter方法
	 * 
	 * @param country
	 *            Province对象的关联的Country对象
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * 利用org.apache.commons.lang.builder.ToStringBuilder重写了toString方法。
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("province_id", province_id).append("name", name).append("code", code)
				.toString();
	}

	/**
	 * 利用org.apache.commons.lang.builder.HashCodeBuilder重写了hashCode方法。
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(province_id).append(name).append(code).toHashCode();
	}

	/**
	 * 利用org.apache.commons.lang.builder.EqualsBuilder重写了equals方法。
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Province)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Province rhs = (Province) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(name, rhs.name).append(code, rhs.code)
				.isEquals();
	}
}
