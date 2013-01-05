package core.utils.solr;

import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 * solrj的服务器的工具类。构造单例的服务器实例。
 * 
 * @author dengjie
 * @since 1.0
 **/
public class SolrServerUtils {
    private static HttpSolrServer server = null;

    private SolrServerUtils() {

    }

    /**
     * 获取Solrj中的HttpSolrServer对象，也是单例的。获取solr服务器对象
     * 
     * @return solr服务器对象
     */
    public static synchronized HttpSolrServer getServer() {
        if (server == null) {
            server = new HttpSolrServer(SolrConstants.SERVER_URL);
            server.setSoTimeout(SolrConstants.SOCKET_TIMEOUT); // socket read timeout
            server.setConnectionTimeout(SolrConstants.CONNECTION_TIMEOUT);
            server.setDefaultMaxConnectionsPerHost(SolrConstants.DEFAULT_MAX_CONNECTIONS_PERHOST);
            server.setMaxTotalConnections(SolrConstants.MAX_TOTAL_CONNECTIONS);
            server.setFollowRedirects(SolrConstants.FOLLOW_REDIRECTS); // defaults to false
            // allowCompression defaults to false.
            // Server side must support gzip or deflate for this to have any effect.
            server.setAllowCompression(SolrConstants.ALLOW_COMPRESSION);
            server.setMaxRetries(SolrConstants.MAX_RETRIES); // defaults to 0. > 1 not recommended.
        }
        return server;
    }
}
