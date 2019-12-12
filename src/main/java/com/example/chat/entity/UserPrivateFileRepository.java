package com.example.chat.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

public interface UserPrivateFileRepository extends CrudRepository<UserPrivateFile, Integer> {
	@Query("select u from UserPrivateFile u where u.uuid = ?1")
	UserPrivateFile findByUuid(String uuid);
}
