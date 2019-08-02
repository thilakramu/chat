package com.example.chat.entity;

import org.springframework.data.repository.CrudRepository;

import com.example.chat.entity.UserPhoto;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

public interface UserPrivateFileRepository extends CrudRepository<UserPrivateFile, Integer> {

}
