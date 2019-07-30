package com.example.chat.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.chat.entity.User;
import com.example.chat.entity.UserRepository;

import java.util.Date;

@Service
public class UserService {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserById(Integer id) {		
		User user = userRepository.findById(id).orElse(null);
		
		return user;		
	}
	
	
}
