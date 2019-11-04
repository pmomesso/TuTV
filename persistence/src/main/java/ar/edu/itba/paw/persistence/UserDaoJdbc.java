package ar.edu.itba.paw.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UsersList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.UserDao;

//@Repository
//To use this UserDao uncomment @Repository and comment @Repository in UserDaoHibernate
public class UserDaoJdbc implements UserDao {
	
	private RowMapper<User> rm = (resultSet, i) -> {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setMailAddress(resultSet.getString("mail"));
		user.setUserName(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));
		user.setConfirmationKey(resultSet.getString("confirmation_key"));
		user.setIsAdmin(resultSet.getBoolean("isAdmin"));
		user.setIsBanned(resultSet.getBoolean("isBanned"));
		return user;
	};
	
	private JdbcTemplate userJdbcTemplate;
	private SimpleJdbcInsert uesrJdbcInsert;
	
	@Autowired
	public UserDaoJdbc(final DataSource ds) {
		userJdbcTemplate = new JdbcTemplate(ds);
		uesrJdbcInsert = new SimpleJdbcInsert(userJdbcTemplate)
					.withTableName("users")
					.usingGeneratedKeyColumns("id");
	}

	@Override
	public Optional<User> getUserById(final long id) {
		List<User> resultSet = userJdbcTemplate.query("SELECT * FROM users WHERE id = ?", new Object[]{id}, rm);
		return resultSet.stream().findFirst();
	}

	@Override
	public Optional<User> getUserByValidationKey(final String key) {
		List<User> resultSet = userJdbcTemplate.query("SELECT * FROM users WHERE confirmation_key = ?", new Object[]{key}, rm);
		return resultSet.stream().findFirst();
	}

	@Override
	public Optional<User> getUserByMail(final String mail) {
		List<User> resultSet = userJdbcTemplate.query("SELECT * FROM users WHERE mail = ?", new Object[]{mail}, rm);
		return resultSet.stream().findFirst();
	}

	@Override
	public Optional<User> createUser(final String userName, final String password, final String mail, boolean isAdmin) {
		Map<String, Object> args = new HashMap<>();
		args.put("username", userName);
		args.put("password", password);
		args.put("mail", mail);
		args.put("isAdmin",isAdmin);
		final Number userGeneratedId = uesrJdbcInsert.executeAndReturnKey(args);
		long insertedId = userGeneratedId.longValue();
		return getUserById(insertedId);
	}

	@Override
	public boolean mailIsTaken(String mail) {
		return !userJdbcTemplate.query("SELECT mail FROM users WHERE mail = ?", new Object[]{mail},
				(resultSet, i) -> { return resultSet.getString("mail"); }).isEmpty();
	}

	@Override
	public boolean userNameExists(String userName) {
		return !userJdbcTemplate.query("SELECT username FROM users WHERE username = ?", new Object[]{userName},
				(resultSet, i) -> { return resultSet.getString("username"); }).isEmpty();
	}

	@Override
	public int updateUserName(long userId, String newUserName) {
		if(userNameExists(newUserName)){
			return 0;
		}
		return userJdbcTemplate.update("UPDATE users SET username = ? WHERE id = ?",newUserName,userId);
	}

	@Override
	public boolean checkIfValidationKeyExists(String key) {
		Integer count = userJdbcTemplate.queryForObject(
				"SELECT count(*) FROM users WHERE confirmation_key = ?", new Object[] { key }, Integer.class);

		return count > 0;
	}

	@Override
	public void setValidationKey(long userId, String key) {
		userJdbcTemplate.update("UPDATE users SET confirmation_key = ? WHERE id = ?", key, userId);
	}

	@Override
	public int banUser(long userId) {
		List<Boolean> isBannedList = userJdbcTemplate.query("SELECT isbanned FROM users WHERE id = ?", new Object[]{userId},
				(resultSet, i) -> {
					return resultSet.getBoolean("isbanned");
				});
		if(isBannedList.isEmpty()) return -1;
		int numRows = userJdbcTemplate.update("UPDATE users SET isbanned = TRUE WHERE id = ?", new Object[]{userId});
		return numRows;
	}

	@Override
	public int unbanUser(long userId) {
		List<Boolean> isBannedList = userJdbcTemplate.query("SELECT isbanned FROM users WHERE id = ?", new Object[]{userId},
				(resultSet, i) -> {
					return resultSet.getBoolean("isbanned");
				});
		if(isBannedList.isEmpty()) return -1;
		int numRows = userJdbcTemplate.update("UPDATE users SET isbanned = FALSE WHERE id = ?", new Object[]{userId});
		return numRows;
	}

	@Override
	public boolean userExists(long userId) {
		Integer count = userJdbcTemplate.queryForObject("SELECT count(*) FROM users WHERE id = ?", new Object[]{userId},Integer.class);
		return count > 0;
	}

	@Override
	public UsersList getAllUsers(int page, long userId) {
		UsersList usersList = new UsersList();
		usersList.setUsersList(userJdbcTemplate.query("SELECT * FROM users", rm));
		return usersList;
	}

	@Override
	public void setUserAvatar(long userId, byte[] byteArray) {
		userJdbcTemplate.update("UPDATE users SET avatar = ? WHERE id = ?", new Object[]{byteArray, userId});
	}

	@Override
	public Optional<byte[]> getUserAvatar(long userId) {
		List<byte[]> results = userJdbcTemplate.query(
				"SELECT avatar FROM users WHERE id = ?", new Object[] { userId }, (resultSet,i) -> { return resultSet.getBytes("avatar");});
		if(results.size() == 0)
			return Optional.empty();
		else{
			return Optional.ofNullable(results.get(0));
		}
	}

}
