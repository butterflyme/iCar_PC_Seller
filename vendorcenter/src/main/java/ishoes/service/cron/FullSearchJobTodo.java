/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：做例子
 */
package ishoes.service.cron;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 定时任务更新solr索引类
 * 
 * @author dengjie
 * @since 1.0
 */
@Service("fullSearchJobTodo")
public class FullSearchJobTodo {

	private final Log log = LogFactory.getLog(FullSearchJobTodo.class);

	/**
	 * 定时任务更新增量索引
	 */
	public void updateProductFIndex() {
	}
}
