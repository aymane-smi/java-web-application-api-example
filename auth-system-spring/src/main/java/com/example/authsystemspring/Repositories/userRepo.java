package com.example.authsystemspring.Repositories;

import com.example.authsystemspring.Exceptions.userException;
import com.example.authsystemspring.Models.User;



public interface userRepo {

	Integer create(String firstName, String lastName, String email, String username, String password) throws userException;
	
	User findByEmailAndPassword(String email, String password) throws userException;
	
	Integer countByEmail(String email);
	
	User findById(int id);
}
