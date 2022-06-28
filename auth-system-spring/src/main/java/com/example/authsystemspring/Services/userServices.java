package com.example.authsystemspring.Services;

import com.example.authsystemspring.Models.User;

import com.example.authsystemspring.Exceptions.userException;

public interface userServices {
	
	User ValidateUser(String email, String password) throws userException;
	
	User registerUser(String Email, String username, String password, String firstName, String lastName) throws userException;

}
