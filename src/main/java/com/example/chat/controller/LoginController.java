package com.example.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.chat.data.UserSession;
import com.example.chat.entity.User;

@Controller
public class LoginController {
	@Autowired
	private UserSession userSession;
	
	@Autowired
    private EntityManager entityManager;
	
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
			/*String password = request.getParameter("password");*/
			
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

}
