package com.AuthAPI;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//@author aymane
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

//Login Servlet
//i did't use webServlet annotation i prefered to use xml configuration instead
public class Login extends HttpServlet{
	String url="jdbc:mysql://localhost:3306/servlet_user";
	String user = "root";
	String password = "123@Password";
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		
		String username = req.getParameter("username");
		
		String Password = req.getParameter("password");
		
		String query = "SELECT * FROM user WHERE user.Username = '"+ username + "' AND user.Password = '"+ Password+"'" ;
		
		System.out.println(query);
		
		res.setHeader("Content-Type", "application/json");
		
		PrintWriter out= res.getWriter();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(query);
			if(!result.next()) {
				out.println("{message: invalid password/username}");
			}else {
				Algorithm algorithm = Algorithm.HMAC256(String.format("%s %s", username, Password));
				String token = JWT.create().withIssuer("auth0").sign(algorithm);
				out.println(String.format("{id: %s, \nusername: %s, \npassword: %s, \ntoken: %s}", result.getString("Id"), result.getString("Username"), result.getString("Password"), token));
			}
		}catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
			res.setStatus(500);
			out.println("{message: something went wrong!}");
		}catch(SQLException e) {
			res.setStatus(500);
			System.out.println(e.getMessage());
			out.println("{message: SQL ERROR!}");
		}
		
	}
}
