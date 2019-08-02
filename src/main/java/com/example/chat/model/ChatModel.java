package com.example.chat.model;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import com.example.chat.entity.ChatMessage;
import com.example.chat.entity.ChatMessageRepository;
import com.example.chat.entity.User;
import com.example.chat.entity.UserPhoto;
import com.example.chat.entity.UserPrivateFile;
import com.example.chat.entity.UserPrivateFileRepository;
import com.example.chat.entity.UserRepository;
import com.example.chat.service.UserService;


@Component
public class ChatModel {
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private ChatMessageRepository ChatMessageRepository; 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPrivateFileRepository userPrivateFileRepository;
	
	private static String UPLOADED_FOLDER = "F://temp//";
	
	public Boolean save(Integer toId, String message, HttpServletRequest request) {
		Integer fromId = (Integer) request.getSession().getAttribute("user_id");		
		ChatMessage n = new ChatMessage();
		n.setFromId(fromId);
		n.setToId(toId);
		n.setMessage(message);
		//n.setRead(true);
		n.setCreatedOn(new Date());
		n.setUpdatedOn(new Date());
		ChatMessageRepository.save(n);
		return true;
	}
	
	
	public Boolean saveFile(MultipartFile file, HttpServletRequest request) {
		
		Integer toId = Integer.parseInt(request.getParameter("to_id"));
		
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
    		            
    		UserPrivateFile up = new UserPrivateFile();
            up.setUser(user);
            up.setUuid(randomUUIDString);
            up.setFileExt(fileExt);
            up.setFilePath(filePath);
            up.setFileSize(file.getSize());
            up.setFileType(file.getContentType());
            up.setFileName(newFileName);
            up.setCreatedOn(new Date());
            up.setUpdatedOn(new Date());
    	
    		ChatMessage n = new ChatMessage();
    		n.setFromId(userId);
    		n.setToId(toId);
    		n.setFile(up);
    		n.setHasFile(true);
    		n.setCreatedOn(new Date());
    		n.setUpdatedOn(new Date());
    		
    		userPrivateFileRepository.save(up);
    		ChatMessageRepository.save(n);   
            
        } catch (IOException e) {
            e.printStackTrace();
            
            return false;
        }
		
		return true;
	}
	
	public Boolean update(Integer id) {
		
		ChatMessage n = ChatMessageRepository.findById(id).orElse(null);
		if (n == null) {
			return false;
		} 
		n.setRead(true);		
		n.setUpdatedOn(new Date());
		ChatMessageRepository.save(n);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public Iterable<ChatMessage> chatListByUser(HttpServletRequest request) {
		try {
			Integer userId = (Integer) request.getSession().getAttribute("user_id");
			String sql = "select cm from ChatMessage cm where from_id=:user_id or to_id=:user_id";
			Query query = entityManager.createQuery(sql, ChatMessage.class);
			query.setParameter("user_id", userId);
			//query.setMaxResults(maxResult);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
