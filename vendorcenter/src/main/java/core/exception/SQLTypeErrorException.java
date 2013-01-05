/**
 * 
 */
package core.exception;

import java.sql.SQLException;

/**
 * 调用DAO中，比如说delete方法，传入update的SQL会抛出这样的异常，表示方法传错了SQL类型
 * @author jayd
 */
public class SQLTypeErrorException extends SQLException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8126660614419338404L;

	public SQLTypeErrorException(String message) {
		super(message);
	}
}
