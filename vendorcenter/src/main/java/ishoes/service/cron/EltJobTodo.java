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
 * 定时任务
 * 
 * @author dengjie
 * @since 1.0
 */
@Service("eltJobTodo")
public class EltJobTodo {

	private final Log log = LogFactory.getLog(EltJobTodo.class);

	/**
	 * ELT项目需要的所有数据，通过定时任务触发
	 * 
	 * @return 清洗数据方法是否成功
	 */
	public boolean eltAllData() {
		log.info("开始进行清洗数据。 ");
		return true;
	}

}
