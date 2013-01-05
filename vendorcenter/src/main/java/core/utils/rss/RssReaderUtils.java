/**
 * 
 */
package core.utils.rss;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import core.domain.rss.ChannelItem;

/**
 * <p>
 * RSS读取工具类。采用rome实现的RSS读取解析。
 * </p>
 * 
 * @author Jayd
 * @since 1.0
 */
public class RssReaderUtils {

	protected static Log log = LogFactory.getLog(RssReaderUtils.class);
	// 连接超时时间设置
	private static int CONNECTION_TIMEOUT = 5000;

	/**
	 * 得到SyndFeed对象，即得到Rss源里的所有信息
	 * 
	 * @param url
	 *            rss的xml资源地址。
	 * @return SyndFeed对象，包含了feed信息。
	 * @throws IllegalArgumentException
	 *             非法参数异常，比如你的url参数不是指向一个Rss源就会抛出
	 * @throws FeedException
	 *             Feed解析异常
	 * @throws IOException
	 *             IO异常
	 */
	public static SyndFeed getFeed(String url,int connectionTimeout) {
		URL feedUrl=null;
		URLConnection uc = null;
		try {
			feedUrl = new URL(url);
			uc = (HttpURLConnection) feedUrl.openConnection();
			// 有时候可能会遇到"Java.io.IOException: Server returned HTTP response code: 403 for URL"的错误信息。通常是因为服务器的安全设置不接受Java程序作为客户端访问，解决方案是设置客户端的User Agent
			uc.setRequestProperty("User-Agent", "IE/6.0");
			// 设置超时,防止多人连接错误地址造成连接不上
			uc.setConnectTimeout(CONNECTION_TIMEOUT);
			XmlReader reader = new XmlReader(feedUrl);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(reader);
			return feed;
		} catch (MalformedURLException e) {
			log.error(e);
		} catch (IOException e1) {
			log.error(e1);
		} catch (IllegalArgumentException e2) {
			log.error(e2);
		} catch (FeedException e3) {
			log.error(e3);
		} 
		return null;
	}

	/**
	 * 得到Rss新闻中子项列表
	 * 
	 * @param feed
	 *            传入取到的一个SyndFeed来读取里面信息
	 * @return 返回含有子项目的List
	 * @throws IllegalArgumentException
	 *             非法参数异常
	 * @throws FeedException
	 *             Feed异常
	 * @throws IOException
	 *             IO异常
	 */
	@SuppressWarnings("unchecked")
	public static List<SyndEntry> getEntries(SyndFeed feed) {
		Assert.notNull(feed,"No feed given");
		List<SyndEntry> entryList = feed.getEntries();
		return entryList;
	}

	/**
	 * 得到一个RSS FEED的小项目的列表
	 * 
	 * @param entryList
	 *            含Item的List
	 * @return ChannelItem的List
	 */
	public static List<ChannelItem> getChannelItems(List<SyndEntry> entryList) {
		Assert.notEmpty(entryList,"No entryList given");
		List<ChannelItem> channelItems = new ArrayList<ChannelItem>();
		for (int i = 0; i < entryList.size(); i++) {
			ChannelItem ChannelItem = new ChannelItem();
			SyndEntry entry = (SyndEntry) entryList.get(i);
			// 标题、连接地址、标题简介、时间是一个Rss源项最基本的组成部分
			ChannelItem.setTitle(entry.getTitle());
			ChannelItem.setLink(entry.getLink());
			ChannelItem.setDescription(entry.getDescription().getValue());
			ChannelItem.setAuthor(entry.getAuthor());
			ChannelItem.setPubDate(entry.getPublishedDate());
			channelItems.add(ChannelItem);
		}
		return channelItems;
	}

	/**
	 * 得到Feed的LINK，比如说网易的头条新闻是：http://news.163.com/special/00011K6L/rss_newstop.xml
	 * 
	 * @param feed
	 *            SyndFeed对象
	 * @return link链接
	 */
	public static String getFeedLink(SyndFeed feed) {
		Assert.notNull(feed,"No feed given");
		return feed.getLink();
	}

	/**
	 * 得到Feed的title
	 * 
	 * @param feed
	 *            SyndFeed对象
	 * @return 标题字符串
	 */
	public static String getFeedTitle(SyndFeed feed) {
		Assert.notNull(feed,"No feed given");
		return feed.getTitle();
	}

	/**
	 * 获取feed中的image
	 * 
	 * @param feed
	 *            SyndFeed对象
	 * @return SyndImage图片对象
	 */
	public static SyndImage getFeedImage(SyndFeed feed) {
		Assert.notNull(feed,"No feed given");
		return feed.getImage();
	}
}
