package com.example.authsystemspring.Services;

import com.example.authsystemspring.Exceptions.userException;
import com.example.authsystemspring.Models.User;
import com.example.authsystemspring.Repositories.userRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class userServicesImpl implements userServices{
	@Autowired
	userRepo UserRepo;
	
	@Override
	public User registerUser(String email, String username, String password, String firstName, String lastName)
			throws userException {
		// TODO Auto-generated method stub
		if(email != null)
			email = email.toLowerCase();
		else
			throw new userException("please eneter an e-mail to complet registration!");
		int count = UserRepo.countByEmail(email);
		if(count > 0)
			throw new userException("email already used! please use a new one!");
		int createdUserId = UserRepo.create(firstName, lastName, email, username, password);
		return UserRepo.findById(createdUserId);
	}
	@Override
	public User ValidateUser(String email, String password) throws userException {
		if(email == null)
			throw new userException("please enter your e-mail");
		if(email != null)
			email = email.toLowerCase();
		return UserRepo.findByEmailAndPassword(email, password);
	}
}
