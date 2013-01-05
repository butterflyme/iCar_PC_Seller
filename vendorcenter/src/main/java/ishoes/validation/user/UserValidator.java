/**
 * 
 */
package ishoes.validation.user;

import ishoes.domain.user.User;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author jayd
 *
 */
public class UserValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	public void validate(Object obj, Errors e) {
		//errorCode在资源文件：validation/validation.properties中
		ValidationUtils.rejectIfEmpty(e, "email", "email.empty");
        User user = (User) obj;
        if (user.getPassword().length() > 6) {
            e.rejectValue("password", "password.moreLong");
        }
	}
}
