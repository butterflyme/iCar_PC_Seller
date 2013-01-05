/**
 * 
 */
package core.utils.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 请借助MAIN函数中的代码自己编写util类。测试使用，系统中不使用。MD5Util带主函数方便运行生成MD5。
 * @author jayd
 * 
 */
public class MD5UtilMain {

	/**
	 * 提供打印，可以当作一个工具类用
	 * @param args
	 */
	public static void main(String args[]) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			//198702就是要加密的明文，可以替换
			messageDigest.update("198702".getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		System.out.println(md5StrBuff.toString());
	}
}
