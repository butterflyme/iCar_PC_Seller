package core.domain.rss;

import java.util.Date;

/**
 * <p>
 * 为rss和atom封装的基本POJO
 * 。可以帮助我们存储RSS与ATOM每个FEED（item标签）的属性值，如标题，连接， 描述等等。
 * </p>
 * 
 * @author Jay Deng
 * @since 1.0
 */
public class ChannelItem {
	//item的标题。
	private String title;
	//item的URL链接地址。 
	private String link;
	//item的摘要。
	private String description;
	//item发布的时间。
	private Date pubDate;
	//item作者的电子邮件地址。
	private String author;

	/**
	 * @return 得到<item>标签中的<title>标签内容
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            修改<item>标签中的<title>标签内容
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return 得到<item>标签中的<link>标签内容
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            修改<item>标签中的<link>标签内容
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return 得到<item>标签中的<description>标签内容
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            修改<item>标签中的<description>标签内容
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return 得到<item>标签中的<pubDate>标签内容
	 */
	public Date getPubDate() {
		return pubDate;
	}

	/**
	 * @param pubDate
	 *            修改<item>标签中的<pubDate>标签内容
	 */
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	/**
	 * @return 得到<item>标签中的<author>标签内容
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            修改<item>标签中的<author>标签内容
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
}
