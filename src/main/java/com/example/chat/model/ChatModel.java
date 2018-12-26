package com.example.chat.model;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import com.example.chat.entity.ChatMessage;
import com.example.chat.entity.ChatMessageRepository;


@Component
public class ChatModel {
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private ChatMessageRepository ChatMessageRepository; 
	
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
