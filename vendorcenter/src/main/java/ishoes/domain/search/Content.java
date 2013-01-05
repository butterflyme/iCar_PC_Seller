/**
 * 
 */
package ishoes.domain.search;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * 搜索联想查询的内容实体
 * </p>
 * 
 * @author jayd
 * @since 1.0
 */
public class Content implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5358340043790077105L;
	//content的id
	private long content_id;
	//名字
	private String name;
	//编码
	private String code;

	/**
	 * getter方法
	 * 
	 * @return content对象的ID
	 */
	public long getContent_id() {
		return content_id;
	}

	/**
	 * setter方法
	 * 
	 * @param content_id
	 *            content对象的ID
	 */
	public void setContent_id(long content_id) {
		this.content_id = content_id;
	}

	/**
	 * getter方法
	 * 
	 * @return content对象的名字
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter方法
	 * 
	 * @param name
	 *            content对象的名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter方法
	 * 
	 * @return content对象的编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * setter方法
	 * 
	 * @param code
	 *            content对象的编码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 利用org.apache.commons.lang.builder.ToStringBuilder重写了toString方法。
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("country_id", content_id).append("name", name).append("code", code)
				.toString();
	}

	/**
	 * 利用org.apache.commons.lang.builder.HashCodeBuilder重写了hashCode方法。
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(content_id).append(name).append(code).toHashCode();
	}

	/**
	 * 利用org.apache.commons.lang.builder.EqualsBuilder重写了equals方法。
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Content)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Content rhs = (Content) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(name, rhs.name).append(code, rhs.code)
				.isEquals();
	}
}
