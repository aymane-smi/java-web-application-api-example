package com.AuthAPI;
//@author aymane
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Create extends HttpServlet{
	//mysql server configuration
			String url="jdbc:mysql://localhost:3306/servlet_user";
			String user = "root";
			String password = "123@Password";
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException{
		
		String username = req.getParameter("username");
		
		String Password = req.getParameter("password");
		
		String query = String.format("INSERT INTO user(Username, Password) VALUES ('%s', '%s')", username, Password);
		
		res.setHeader("Content-Type", "application/json");
		
		PrintWriter out = res.getWriter();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(url, user, password);
			
			PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			ps.execute();
			
			ResultSet result = ps.getGeneratedKeys();
			
			result.next();
			
			res.setStatus(200);
			
			out.println(String.format("{\nmessage: user created,!\nid: %s,\nusername: %s,\npassword: %s\n}", result.getInt(1), username, Password));
			
		}catch(ClassNotFoundException e) {
			
			res.setStatus(500);
			
			out.println("{\nmessage: something went wrong in the server!\n}");
			
		}catch(SQLException e){
			
			res.setStatus(500);
			
			System.out.println(e.getMessage());
			
			out.println("{\nmessage: SQL Error\n}");
		}
		
	}
}
