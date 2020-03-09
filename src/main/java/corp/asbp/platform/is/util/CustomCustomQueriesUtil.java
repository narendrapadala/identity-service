package corp.asbp.platform.is.util;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import corp.asbp.platform.is.model.Role;
import corp.asbp.platform.is.model.User;


@Component
public class CustomCustomQueriesUtil {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcCall simpleJdbcCallRefCursor;

	@SuppressWarnings("unchecked")
	public void test() {

		jdbcTemplate.setResultsMapCaseInsensitive(true);

		SqlParameterSource paramaters = new MapSqlParameterSource()
				.addValue("uid", BigInteger.valueOf(8))				
				.addValue("users", "users")
				.addValue("roles", "roles");

		simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("user_roles")
				.returningResultSet("users", BeanPropertyRowMapper.newInstance(User.class))
				.returningResultSet("roles", BeanPropertyRowMapper.newInstance(Role.class))
				;

		simpleJdbcCallRefCursor.execute(paramaters);
		
		/*
		Map out = simpleJdbcCallRefCursor.execute(paramaters);

		List<User> user;

		if (out == null) {
			user = Collections.emptyList();
		} else {
			user = (List<User>) out.get("users");
		}
		
		
		if(user.size() > 0) {
			
			for(User u : user) {
				
				if(u!=null ) { 
					
					System.out.println(u.getEmail());				
				
				}
					
			}
			
		}
		*/
	}
}
