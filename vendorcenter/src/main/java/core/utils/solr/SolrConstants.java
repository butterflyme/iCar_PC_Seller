/**
 * 
 */
package core.utils.solr;

import ishoes.Constants;

import java.io.File;

import core.utils.file.PropertiesUtils;

/**
 * <p>
 * 搜索引擎solr的常量类，避免魔鬼数字
 * </p>
 * 
 * @author dengjie
 * @since 1.0
 */
public class SolrConstants {

    /**
     * Solr服务器地址,暂时只支持单机
     */
    public static final String SERVER_URL = PropertiesUtils.getProperty(Constants.PROJECT_CLASS_ROOT + File.separator
            + "solr-config.properties", "server_url");

    /**
     * Solr服务器套接字读取超时时间
     */
    public static final int SOCKET_TIMEOUT = Integer.valueOf(PropertiesUtils.getProperty(Constants.PROJECT_CLASS_ROOT
            + File.separator + "solr-config.properties", "socket_timeout"));

    /**
     * Solr服务器套接字连接超时时间
     */
    public static final int CONNECTION_TIMEOUT = Integer.valueOf(PropertiesUtils.getProperty(
            Constants.PROJECT_CLASS_ROOT + File.separator + "solr-config.properties", "connection_timeout"));

    /**
     * Solr服务器setDefaultMaxConnectionsPerHost参数
     */
    public static final int DEFAULT_MAX_CONNECTIONS_PERHOST = Integer.valueOf(PropertiesUtils
            .getProperty(Constants.PROJECT_CLASS_ROOT + File.separator + "solr-config.properties",
                    "default_max_connections_perhost"));

    /**
     * Solr服务器setMaxTotalConnections参数
     */
    public static final int MAX_TOTAL_CONNECTIONS = Integer.valueOf(PropertiesUtils.getProperty(
            Constants.PROJECT_CLASS_ROOT + File.separator + "solr-config.properties", "max_total_connections"));

    /**
     * Solr服务器setFollowRedirects参数
     */
    public static final boolean FOLLOW_REDIRECTS = Boolean.valueOf(PropertiesUtils.getProperty(
            Constants.PROJECT_CLASS_ROOT + File.separator + "solr-config.properties", "follow_redirects"));
    /**
     * Solr服务器setAllowCompression参数
     */
    public static final boolean ALLOW_COMPRESSION = Boolean.valueOf(PropertiesUtils.getProperty(
            Constants.PROJECT_CLASS_ROOT + File.separator + "solr-config.properties", "allow_compression"));

    /**
     * Solr服务器setMaxRetries参数
     */
    public static final int MAX_RETRIES = Integer.valueOf(PropertiesUtils.getProperty(Constants.PROJECT_CLASS_ROOT
            + File.separator + "solr-config.properties", "max_retries"));

    /**
     * 私有构造方法，使该类不能被继承
     */
    private SolrConstants() {

    }
}
