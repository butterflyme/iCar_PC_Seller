package core.utils.json;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * JSON辅助工具类。提供了复杂对象对JSON格式的转换，支持对象中嵌套对象与嵌套集合类。
 * </p>
 * <p>
 * 利用反射构造复杂对象的JSON数组字符串。具有简单API与高效的性能。准备以后提供ObjectWeb深入
 * JVM的反射。
 * </p>
 * <p>
 * 如果使用者不满意该代码的写法可以去使用GSON，Google写的支持任意复杂对象对JSON的转换，也支持反转。
 * 或是使用Jackson性能最佳,提倡使用jackson来进行转换。唯一有问题是他是使用get方法来做到的，和gson利用
 * field方式不一样，如果没有get方法就做不到。
 * </p>
 * 
 * @author jayd
 * @since 1.0
 */
public class JSONUtil {
	//日期格式
	public static String DATE_FORMAT = "yyyy-MM-dd";
	/**
	 * <p>将复杂对象转换成JSON数组字符串，提供给前台使用。</p>
	 * @param o 该复杂对象
	 * @param s StringBuffer对象来封装返回的字符串。使用线程安全的StringBuffer，但是由于是线程安全所以效率要低点。
	 * @param parseClazz 该对象的Class对象以及所嵌套的对象的Class对象，提供来反射操作。
	 * @throws Exception
	 */
	public static void object2JSON(Object o,StringBuffer s,Class<?>... parseClazz) throws Exception{
		Class<?> clazz = o.getClass();
		
		List<Class<?>> context = new ArrayList<Class<?>>();
		for(Class<?> c :parseClazz){
			context.add(c);
		}
		
		if(clazz.isArray()){
			s.append("[");
			for(Object sub:(Object[])o){
				JSONUtil.covert(sub, context, s, parseClazz);
				s.append(",");
			}
			s.delete(s.length()-1, s.length());
			s.append("]");
		} else if(o instanceof Collection<?>){
			s.append("[");
			
			Collection<?> collection = (Collection<?>) o;
			for(Iterator<?> iterator = collection.iterator();iterator.hasNext();){
				JSONUtil.covert(iterator.next(), context, s, parseClazz);
				s.append(",");
			}
			s.delete(s.length()-1, s.length());
			s.append("]");
		} else {
			JSONUtil.covert(o, context, s, parseClazz);
		}
	}
	
	private static void covert(Object o,List<Class<?>> context,StringBuffer s,Class<?>... parseClazz) throws Exception{
		Class<?> clazz = o.getClass();
		s.append("{");
		
		PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
		for (PropertyDescriptor prop : props) {
			
			if (prop.getReadMethod() != null && prop.getReadMethod().invoke(o) != null&& !"class".equals(prop.getDisplayName())) {
				
				s.append("\"").append(prop.getName()).append("\"").append(":");
				
				Object value = prop.getReadMethod().invoke(o);

				if(prop.getPropertyType() == String.class
						|| prop.getPropertyType() == Boolean.class
						|| prop.getPropertyType() == boolean.class
						|| prop.getPropertyType() == Character.class
						|| prop.getPropertyType() == char.class){
					s.append("\"").append(value).append("\"");
				} else if (isWrapClass(prop.getPropertyType())
						|| prop.getPropertyType().isPrimitive()){
					s.append(value);
				} else if(value.getClass() == java.util.Date.class || value.getClass() == java.sql.Date.class){
					s.append("\"").append(new SimpleDateFormat(DATE_FORMAT).format(value)).append("\"");
				} else if(prop.getPropertyType() == clazz){
					System.out.println("Ignore nested class: \""+value.getClass()+"\"");
					s.delete(s.lastIndexOf(","), s.length());
				} else if(context.contains(prop.getPropertyType())
						|| (value.getClass().isArray() && ((Object[])value).length>0 && context.contains(((Object[])value)[0].getClass()))
						|| value instanceof Collection<?>){
					JSONUtil.object2JSON(value,s,parseClazz);
				} else {
					s.delete(s.lastIndexOf(","), s.length());
				}
				s.append(",");
			}
		}
		
		s.delete(s.length()-1, s.length());
		s.append("}");
	}
	
	private static boolean isWrapClass(Class<?> clazz){  
	    try {  
	        return ((Class<?>)clazz.getField("TYPE").get(null)).isPrimitive();
	    } catch (Exception e){  
	        return false;  
	    }  
	}
}
