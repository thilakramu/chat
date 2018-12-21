package com.example.chat.controller;

import java.time.LocalTime;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class ChatController {

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
}
