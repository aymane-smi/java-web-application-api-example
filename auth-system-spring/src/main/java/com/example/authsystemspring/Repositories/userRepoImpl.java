package com.example.authsystemspring.Repositories;

import java.sql.PreparedStatement;

import java.sql.Statement;

import com.example.authsystemspring.Exceptions.userException;
import com.example.authsystemspring.Models.User;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class userRepoImpl implements userRepo{
	
	private static final String SQL_CREATE = "INSERT INTO users(id, firstName, lastName, email, username, password)"+
	"VALUES(NEXTVAL('user_seq'), ?, ?, ?, ?, ?)";
	private static final String SQL_COUNT_EMAIL = "SELECT COUNT(*) FROM users WHERE EMAIL = ?";
	private static final String SQL_FIND_USER = "SELECT * FROM users WHERE id = ?";
	private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
	
	@Autowired
	
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	public Integer create(String firstName, String lastName, String email, String username, String password)
			throws userException {
		// TODO Auto-generated method stub
		KeyHolder keyHolder= new GeneratedKeyHolder();
		String HashedPasword = BCrypt.hashpw(password, BCrypt.gensalt(10));
		try {
			//using lambda function
			jdbcTemplate.update(connection ->{
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, firstName);
				ps.setString(2, lastName);
				ps.setString(3, email);
				ps.setString(4, username);
				ps.setString(5, HashedPasword);
				return ps;
			}, keyHolder);
			return (int) keyHolder.getKeys().get("id");
		}catch(Exception e) {
			throw new userException(e.getMessage());
		}
	}

	@Override
	public User findByEmailAndPassword(String email, String password) throws userException {
		try {
			User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
			if(!BCrypt.checkpw(password, user.getPassword()))
				throw new userException("invalid email/password");
			return user;
		}catch(Exception e) {
			throw new userException(e.getMessage());
		}
	}

	@Override
	public Integer countByEmail(String email) {
		return jdbcTemplate.queryForObject(SQL_COUNT_EMAIL, new Object[]{email}, Integer.class);
	}

	@Override
	public User findById(int id) {
		
		return jdbcTemplate.queryForObject(SQL_FIND_USER, new Object[]{id}, userRowMapper);
	}
	
	public RowMapper<User> userRowMapper = ((rs, rowNum) -> {
		return new User(rs.getString("firstName"), rs.getString("lastName"), 
				rs.getString("email"), 
				rs.getString("username"),
				rs.getString("password"));
	});

}
