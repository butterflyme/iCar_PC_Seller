1.发布war包的时候，需要将jstl-1.2.jar打包到WEB-INF/lib下面。因为活动模块使用到了JSTL。
2.pro_env:生产环境，用来保证云服务器上的发布包。先暂时放到源代码中。后续需要做配置管理。
生产环境上的jdbc配置/vendorcenter/src/main/resources/jdbc/applicationContext-jdbc.xml请注意修改相关配置信息。
生产环境上的memcached配置/vendorcenter/src/main/resources/spring/applicationContext-memcached.xml请注意修改相关配置信息。

3.test_env:共同测试环境，例如112服务器。