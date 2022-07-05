package com.example.authsystemspring.Resources;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.authsystemspring.Models.User;
import com.example.authsystemspring.Services.userServices;
import com.example.authsystemspring.constants;

import org.apache.catalina.authenticator.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/user")
public class userResouces {
	@Autowired
	userServices UserServices;
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> Register(@RequestBody Map<String, Object> userMap) {
		String firstName = (String) userMap.get("firstName");
		String lastName = (String) userMap.get("lastName");
		String username = (String) userMap.get("username");
		String email = (String) userMap.get("email");
		String password = (String) userMap.get("password");
		User user = UserServices.registerUser(email, username, password, firstName, lastName);
		//Map<String, String> map = new HashMap<String, String>();
		//map.put("message", "user registred successfully!!");
		
		return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, Object> credentielsMap){
		String email = (String) credentielsMap.get("email");
		String password = (String) credentielsMap.get("password");
		User user = UserServices.ValidateUser(email, password);
		//Map<String, String> map = new HashMap<String, String>();
		//map.put("message", "login successfully");
		return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
	}
	
	private Map<String, String> generateJWTToken(User user){
		
		long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, constants.KEY).setIssuedAt(new Date(timestamp))
				                                                                       .setExpiration(new Date(timestamp + constants.DURATION))
				                                                                       .claim("userId", user.getId())
				                                                                       .claim("email", user.getEmail())
				                                                                       .claim("lastName", user.getLastName())
				                                                                       .claim("firstName", user.getFirstName())
				                                                                       .compact();
		Map<String, String> map = new HashMap();
		map.put("token", token);
		System.out.println(token);
		return map;
	}
}
