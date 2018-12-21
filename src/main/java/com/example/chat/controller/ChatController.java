package com.example.chat.controller;

import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.chat.data.CustomApiResponse;
import com.example.chat.entity.ChatMessage;
import com.example.chat.entity.User;

@Controller
public class ChatController {
	@Autowired
    private EntityManager entityManager;

	@RequestMapping("/sseTest")
    public ResponseBodyEmitter handleRequest () {

        final SseEmitter emitter = new SseEmitter();
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            
	        try {
	            emitter.send(LocalTime.now().toString() , MediaType.TEXT_PLAIN);
	
	            Thread.sleep(200);
	        } catch (Exception e) {
	            e.printStackTrace();
	            emitter.completeWithError(e);
	            return;
	        }
            emitter.complete();
        });

        return emitter;
    }
	
	@RequestMapping("/chat/user/{id}")
	public String chatWithUser(@PathVariable Integer id ) {
		return "chat";
		
	}
	
	@RequestMapping("/chat/save")
	public @ResponseBody CustomApiResponse saveChat() {
		
		return new CustomApiResponse(true, "saved successfully", false);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(path="/chat/messages/user")
	public @ResponseBody Iterable<ChatMessage> chatMessagesByUserId(@RequestParam Integer id) {
  		
		try {
			String sql = "select cm from ChatMessage cm";
			Query query = entityManager.createQuery(sql, ChatMessage.class);
			
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		
	
	}
}
