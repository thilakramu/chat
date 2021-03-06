package com.example.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.chat.data.CustomApiResponse;
import com.example.chat.data.Player;
import com.example.chat.data.UserSession;
import com.example.chat.entity.User;
import com.example.chat.service.UserService;
import com.example.chat.service.UserPhotoService;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	public static final Integer limit = 10;
	
	@Autowired
	private UserSession userSession;
	
	@Autowired
    private EntityManager entityManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPhotoService userPhotoService;
	
	@GetMapping("/")
    public String home() {
		        
        return "redirect:/user/login";        
    }
	
	@GetMapping("/user/login")
    public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        
        return mav;        
    }
	
	@PostMapping("/user/login")
	public String loginCheck(HttpServletRequest request, Model model) {		
		User user = null;
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			String sql = "select u from User u where u.email=:username";
			Query query = entityManager.createQuery(sql, User.class);
			query.setParameter("username", username);
			
			user = (User) query.getSingleResult();
		}
		catch (NoResultException nre) {
			//Ignore this because as per your logic this is ok!
		}
		
		if (user == null) {
			model.addAttribute("error", "Username not found");
			return "login";
		} else {
			request.getSession().setAttribute("username",user.getEmail());
			request.getSession().setAttribute("name",user.getName());
			request.getSession().setAttribute("user_id",user.getId());
			
			userSession.setUserId(user.getId());
			userSession.setEmail(user.getEmail());
			userSession.setName(user.getName());
			
			return "redirect:dashboard";
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/user/dashboard")
	public ModelAndView dashboard(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("user_id");
		Iterable<User> users = null;
		ModelAndView mav = new ModelAndView();
        
		try {
			
			String sql = "select u from User u where id != :id";
			Query query = entityManager.createQuery(sql, User.class);
			query.setParameter("id", userId);
			users = query.getResultList();
		}
		catch (NoResultException nre) {
			//Ignore this because as per your logic this is ok!
		}
		
		System.out.println(userSession.getEmail());
		
		mav.addObject("users", users);
        mav.setViewName("dashboard");
        
		return mav;
	}
	
	@RequestMapping("/user/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
	
	@GetMapping("/user/upload/image")
    public ModelAndView uploadImage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
        mav.setViewName("upload-image");
        
        return mav;        
    }
	
	@PostMapping("/user/upload/image") // //new annotation since 4.3
    public @ResponseBody CustomApiResponse singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {		
		if (userPhotoService.save(file, request)) {
			 return new CustomApiResponse(true, "profile photo uploaded successfully", false);
		} else {
			 return new CustomApiResponse(false, "profile photo uploaded successfully", true);
		}
    }
	
	@GetMapping("/memory-management") // //new annotation since 4.3
    public @ResponseBody CustomApiResponse memoryManagement(HttpServletRequest request) {	
		ArrayList<Double> list = new ArrayList<Double>();
		
		ArrayList<Double> list2 = new ArrayList<Double>();
		
		for (int i=0;i<10;i++) {
			list.add(Math.random());
		} 
		
		list2 = list;
		
		System.out.println(list);
		
		System.out.println(list2);
		
		list = null;
		
		System.out.println(list);
		
		System.out.println(list2);
		String name = "thilak ramu";
		String name2 = new String("thilak ramu");
		
		if (name == name2.intern()) {
			System.out.println("Equals");
		} else {
			System.out.println("Not Equals");
		}
		
		return new CustomApiResponse(false, "profile photo uploaded successfully", true);
    }
	
	@GetMapping("/comparator")
	@ResponseBody public CustomApiResponse comparator() {
		
		List<Player> footballTeam = new ArrayList<>();
	    Player player1 = new Player(59, "John", 20);
	    Player player2 = new Player(67, "Roger", 22);
	    Player player3 = new Player(45, "Steven", 24);
	    footballTeam.add(player1);
	    footballTeam.add(player2);
	    footballTeam.add(player3);
	   
	    System.out.println("Before Sorting : " + footballTeam);
	    Collections.sort(footballTeam);
	    System.out.println("After Sorting : " + footballTeam);
	    
		return new CustomApiResponse(false, "profile photo uploaded successfully", true);
	}
	


}
