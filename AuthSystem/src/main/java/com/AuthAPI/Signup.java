package com.AuthAPI;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//@author aymane
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


public class Signup extends HttpServlet{
	//mysql server configuration
	String url="jdbc:mysql://localhost:3306/servlet_user";
	String user = "root";
	String password = "123@Password";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{

		String username = req.getParameter("username");
		
		String Password = req.getParameter("password");
		
		String query = String.format("INSERT INTO user(Username, Password) VALUES ('%s', '%s')", username, Password);
		
		res.setHeader("Content-Type", "application/json");
		
		PrintWriter out= res.getWriter();
		
		try {
				if(username.trim().isEmpty() || username == null || password.trim().isEmpty() || password == null)
					throw new Exception("please enter valid inputs!");
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.execute();
				ResultSet result = ps.getGeneratedKeys();
				Algorithm algorithm = Algorithm.HMAC256(String.format("%s %s", username, Password));
				String token = JWT.create().withIssuer("auth0").sign(algorithm);
				res.setStatus(200);
				out.println(String.format("{id: %d, \nusername: %s, \npassword: %s, \ntoken: %s}", result.getInt(1), username, Password, token));
			}catch(ClassNotFoundException e) {
				System.out.println(e.getMessage());
				res.setStatus(500);
				out.println("{message: something went wrong!}");
			}catch(SQLException e) {
				res.setStatus(500);
				System.out.println(e.getMessage());
				out.println("{message: SQL ERROR!}");
			}catch(Exception e) {
				res.setStatus(500);
				out.println(String.format("{\nmessage: %s\n}", e.getMessage()));
			}
		
	}
}
