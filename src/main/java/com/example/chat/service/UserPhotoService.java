package com.example.chat.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.chat.data.CustomApiResponse;
import com.example.chat.entity.User;
import com.example.chat.entity.UserRepository;
import com.example.chat.entity.UserPhoto;
import com.example.chat.entity.UserPhotoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import com.example.chat.service.UserService;

@Service
public class UserPhotoService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPhotoRepository userPhotoRepository;
	
	private static String UPLOADED_FOLDER = "F://temp//";
	
	public Boolean save(MultipartFile file, HttpServletRequest request) {
		
		if (file.isEmpty()) {         
            return false;
        }

        try {
        	
        	String fileName = file.getOriginalFilename();     	
        	
        	int i = fileName.lastIndexOf('.');
        	String fileExt = fileName.substring(i);
        	
        	UUID uuid = UUID.randomUUID();
    		String randomUUIDString = uuid.toString();
    		String filePath = UPLOADED_FOLDER + randomUUIDString + fileExt;
    		String newFileName = randomUUIDString + fileExt;

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);
            
            Integer userId = (Integer) request.getSession().getAttribute("user_id");
    		
    		User user = userService.getUserById(userId);
    		            
            UserPhoto up = new UserPhoto();
            up.setUser(user);
            up.setUuid(randomUUIDString);
            up.setFileExt(fileExt);
            up.setFilePath(filePath);
            up.setFileSize(file.getSize());
            up.setFileType(file.getContentType());
            up.setFileName(newFileName);
            up.setCreatedOn(new Date());
            up.setUpdatedOn(new Date());
            
    		userPhotoRepository.save(up);

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return true;
	}

}
