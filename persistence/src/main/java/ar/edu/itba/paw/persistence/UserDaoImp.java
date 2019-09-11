package ar.edu.itba.paw.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.UserDao;

@Repository
public class UserDaoImp implements UserDao {
	
	private RowMapper<User> rm = (resultSet, i) -> {
		User user = new User(resultSet.getString("username"));
		return user;
	};
	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public UserDaoImp(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("users")
					.usingGeneratedKeyColumns("id");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (" +
				"id SERIAL PRIMARY KEY," +
				"username VARCHAR(32)," +
				"password VARCHAR(32)" +
				")");
	}


	@Override
	public User getUser(final long id) {
		List<User> resultSet = jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new Object[]{id}, rm);
		if(resultSet.isEmpty()) {
			return null;
		}
		return resultSet.get(0);
	}

	@Override
	public long createUser(final String userName) {
		Map<String, Object> args = new HashMap<>();
		args.put("userName", userName);
		final Number userGeneratedId = jdbcInsert.executeAndReturnKey(args);
		return userGeneratedId.longValue();
	}

}
