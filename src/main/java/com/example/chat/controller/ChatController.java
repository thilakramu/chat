package com.example.chat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.chat.AppProperties;
import com.example.chat.data.ChatData;
import com.example.chat.data.CustomApiResponse;
import com.example.chat.data.UserSession;
import com.example.chat.entity.ChatMessage;
import com.example.chat.model.ChatModel;

@Controller
public class ChatController {
	@Autowired
    private EntityManager entityManager;
	
	@Autowired 	
	private ChatModel chatModel;

	@Autowired
	private UserSession userSession;
	
	@Autowired
	private AppProperties appProperties;
	
	@RequestMapping("/threads")
	@ResponseBody
    public CustomApiResponse playThread() {

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            
	        try {
	            	
	            Thread.sleep(20000);
	            
	            System.out.println("This will print after 20 seconds");
	            System.out.println(Thread.currentThread().getName());
	        } catch (Exception e) {
	            e.printStackTrace();
	            
	        }
            
        });

        return new CustomApiResponse(true, "saved successfully", false);
    }
	
	
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
	public String chatWithUser(@PathVariable Integer id, Model model, HttpServletRequest request) {
		model.addAttribute("to_id", id);
		//System.out.println(userSession.getEmail());
		Iterable<ChatMessage> chatList = chatModel.chatListByUser(request);		
		model.addAttribute("chatList", chatList);
		System.out.println("share session "+userSession.getEmail());
		
		System.out.println("share original session "+ request.getSession().getAttribute("user_id"));
		
		System.out.println(appProperties.getName());
		return "chat";
		
	}
	
	@RequestMapping("/chat/save")
	public @ResponseBody CustomApiResponse saveChat(HttpServletRequest request) {
		Integer toId = Integer.parseInt(request.getParameter("to_id"));
		String message = request.getParameter("message");
		chatModel.save(toId, message, request);
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
	
	@RequestMapping("/chat/messages/unread/{id}")
    public ResponseBodyEmitter chatMessagesUnread(HttpServletRequest request, @PathVariable Integer id) {
		//Iterable<ChatMessage> chatMessages = ChatMessageRepository.findAll();
		
		String sql = "select cm from ChatMessage cm where to_id=:to_id and from_id=:from_id and read=0";
		Query query = entityManager.createQuery(sql, ChatMessage.class);
		query.setParameter("to_id", request.getSession().getAttribute("user_id"));
		query.setParameter("from_id", id);
		
		@SuppressWarnings("unchecked")
		Iterable<ChatMessage> chatMessages = query.getResultList();
		
		if (chatMessages != null) {
			for (ChatMessage message : chatMessages) {
				if (!message.getRead()) {
					chatModel.update(message.getId());
				}
			}
		} 

        final SseEmitter emitter = new SseEmitter();
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            
	        try {
	            emitter.send(chatMessages, MediaType.ALL);
	
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
	
	@SuppressWarnings("unchecked")
	@GetMapping(path="/chat/messages/history/user")
	public @ResponseBody Iterable<ChatMessage> chatHistoryByUser(HttpServletRequest request, @RequestParam Integer other_user_id, @RequestParam Integer start , @RequestParam Integer end) {
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		try {
			String sql = "select cm from ChatMessage cm where (from_id=:user_id or to_id=:user_id) and (from_id=:other_user_id or to_id=:other_user_id)";
			Query query = entityManager.createQuery(sql, ChatMessage.class);
			query.setParameter("user_id", userId);
			query.setParameter("other_user_id", other_user_id);
			query.setFirstResult(start);
			query.setMaxResults(end);
			return query.getResultList();			
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/chat/upload/file") // //new annotation since 4.3
    public @ResponseBody Object singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {	
		
		//test file
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		if (chatModel.saveFile(file, request)) {
			
			 data = (HashMap<String, Object>) request.getSession().getAttribute("fileUpData");
			
			 return data;
		} else {
			 return new CustomApiResponse(false, "File uploaded failed", true);
		}
    }
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(path="/chat/messages/by/user")
	public @ResponseBody HashMap<String, HashMap<Integer, ChatMessage>> chatByUser(HttpServletRequest request, @RequestParam Integer start , @RequestParam Integer end) {
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		try {
			String sql = "select cm from ChatMessage cm where (from_id=:user_id or to_id=:user_id)";
			Query query = entityManager.createQuery(sql, ChatMessage.class);
			query.setParameter("user_id", userId);
			query.setFirstResult(start);
			query.setMaxResults(end);
	
	
			Iterable<ChatMessage> chatMessages = query.getResultList();
			
			HashMap<String, HashMap<Integer, ChatMessage>> data = new HashMap<String, HashMap<Integer, ChatMessage>>();
			
			if (chatMessages != null) {
				for (ChatMessage message : chatMessages) {
					if (message.getRead()) {
						if ( !data.containsKey("readMessages") ) {
							data.put("readMessages",  new HashMap<Integer, ChatMessage>());
						}
						
						data.get("readMessages").put(message.getId(), message);
					} else {
						if ( !data.containsKey("unreadMessages") ) {
							data.put("unreadMessages",  new HashMap<Integer, ChatMessage>());
						}
						
						data.get("unreadMessages").put(message.getId(), message);
					}
				}
			}
			
			//data.put("count", data.get("unreadMessages").size());
			
			System.out.println("count of unread message - " + data.get("unreadMessages").size() );
			
			return data;
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(path="/chat/messages/by/user/new")
	public @ResponseBody HashMap<String, Object> chatByUserNew(HttpServletRequest request, @RequestParam Integer start , @RequestParam Integer end) {
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		try {
			String sql = "select cm from ChatMessage cm where (from_id=:user_id or to_id=:user_id)";
			Query query = entityManager.createQuery(sql, ChatMessage.class);
			query.setParameter("user_id", userId);
			query.setFirstResult(start);
			query.setMaxResults(end);
	
	
			Iterable<ChatMessage> chatMessages = query.getResultList();
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			if (chatMessages != null) {
				for (ChatMessage message : chatMessages) {
					if (message.getRead()) {
						if ( !data.containsKey("readMessages") ) {
							data.put("readMessages",  new HashMap<Integer, ChatMessage>());
						}
						
						((Map<Integer, ChatMessage>) data.get("readMessages")).put(message.getId(), message);
					} else {
						if ( !data.containsKey("unreadMessages") ) {
							data.put("unreadMessages",  new HashMap<Integer, ChatMessage>());
						}
						
						((Map<Integer,ChatMessage>) data.get("unreadMessages")).put(message.getId(), message);
					}
				}
			}
			
			
			return data;
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(path="/chat/messages/by/user/new2")
	public @ResponseBody HashMap<Integer, Object> chatByUserNew2(HttpServletRequest request, @RequestParam Integer start , @RequestParam Integer end) {
		Integer userId = (Integer) request.getSession().getAttribute("user_id");
		try {
			String sql = "select cm from ChatMessage cm where (from_id=:user_id or to_id=:user_id)";
			Query query = entityManager.createQuery(sql, ChatMessage.class);
			query.setParameter("user_id", userId);
			query.setFirstResult(start);
			query.setMaxResults(end);
	
	
			Iterable<ChatMessage> chatMessages = query.getResultList();
			
			HashMap<Integer, Object> data = new HashMap<Integer, Object>();
			
			if (chatMessages != null) {
				for (ChatMessage message : chatMessages) {
					if (!data.containsKey(message.getToId())) {
						data.put(message.getToId(), new HashMap<String, Object>());
					}
					
					if (message.getRead()) {
						
						if ( !((HashMap<String, ArrayList<Object>>) data.get(message.getToId())).containsKey("readMessages") ) {
							((HashMap<String, ArrayList<Object>>) data.get(message.getToId())).put("readMessages",  new ArrayList<Object>());
						}
						
						((HashMap<String, ArrayList<Object>>) data.get(message.getToId())).get("readMessages").add(message);
						
						
					} else {						
						
						if ( !((HashMap<String, ArrayList<Object>>) data.get(message.getToId())).containsKey("unreadMessages") ) {
							((HashMap<String, ArrayList<Object>>) data.get(message.getToId())).put("unreadMessages",  new ArrayList<Object>());
						}
						
						((HashMap<String, ArrayList<Object>>) data.get(message.getToId())).get("unreadMessages").add(message);
					}
				}
			}
			
			return data;
			
		} catch (NoResultException e) {
			return null;
		}
	}	
	
	
	@RequestMapping(value = "/download/file",
            produces = MediaType.ALL_VALUE)

    public void getImage(HttpServletResponse response) throws IOException {

		ClassPathResource imgFile = new ClassPathResource("static/uploads/5996aef8-3831-460b-b0bb-37749c404cdd.jpg");

        response.setContentType(MediaType.ALL_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }
	
	
	@RequestMapping(value = "/sid",
            produces = MediaType.ALL_VALUE)
    public ResponseEntity<InputStreamResource> getImage() throws IOException {

		ClassPathResource imgFile = new ClassPathResource("static/uploads/5996aef8-3831-460b-b0bb-37749c404cdd.jpg");

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }
	
}
