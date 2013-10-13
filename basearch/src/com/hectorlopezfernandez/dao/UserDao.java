package com.hectorlopezfernandez.dao;

import com.hectorlopezfernandez.model.User;

public interface UserDao {

	// recupera un usuario a partir del id
	public User getUserById(Long id);

}