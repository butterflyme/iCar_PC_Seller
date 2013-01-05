/**
 * 
 */
package ishoes.dao.user.impl;

import ishoes.dao.user.RoleDAO;
import ishoes.dao.user.UserDAO;
import ishoes.domain.user.Role;
import ishoes.domain.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import core.dao.genericType.jdbc.impl.GenericTypeJdbcDAOImpl;
import core.exception.SQLTypeErrorException;

/**
 * UserDAO接口的DAO实现类
 * 
 * @author jayd
 * @since 1.0
 */
@Component
public class UserDAOImpl extends GenericTypeJdbcDAOImpl<User, Long> implements UserDAO {

	private final Log log = LogFactory.getLog(UserDAOImpl.class);
	@Autowired
	private RoleDAO roleDAO;
	
	public User findUserByLoginName(String username) {
		User user = new User();
		String sql = "select * from user where email=?";
		List<User> users = this.query(sql, new Object[] { username }, new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setUser_id(rs.getLong(1));
				user.setEmail(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setStatus(rs.getInt(4));
				user.setReal_name(rs.getString(5));
				user.setBirthdate(rs.getDate(6));
				user.setHead_picture(rs.getString(7));
				return user;
			}
		});
		if (users.size() == 1) {
			user = users.get(0);
		}
		user.setRoles(new HashSet<Role>(roleDAO.userFetchRoles(user.getUser_id())));
		return user;
	}

	public void save(User user) {
		String sql="insert into user (email,password,status,real_name,birthdate,head_picture ) VALUES (?, ?, ?, ?, ?, ?)";
		this.save(sql, new Object[]{user.getEmail(),user.getPassword(),user.getStatus(),user.getReal_name(),user.getBirthdate(),user.getHead_picture()});
	}
}
