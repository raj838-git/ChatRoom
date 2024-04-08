package com.boot.security.controllers;

import java.sql.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.security.websocketss.ChatMessage;


@Controller
public class Controllers {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Modell modell;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/login")
	public String login(@RequestParam(required = false) String parameter, Model model) {
		logger.info("Parameters pass int logint GET : {}", parameter);
		if ("success".equals(parameter)) {
			model.addAttribute("message", "Registration is successfull. Login now ...");
			model.addAttribute("messagecolor", "color : green");
        }
		else if("error".equals(parameter)) {
			model.addAttribute("message", "Invalide Username and Password");
			model.addAttribute("messagecolor", "color : red");
		}
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@RequestParam String email, @RequestParam String username, @RequestParam String name,
			@RequestParam String password, @RequestParam Date birth, Model model) {
		logger.info("we at register {}", email);
		String result = modell.startsignup(email, username, name, password, birth);
		if(result.equalsIgnoreCase("Success")) {
			return "redirect:/login?parameter=success";
		}
		else {
			model.addAttribute("message", result);
			return "/register";
		}
	}
	
	@GetMapping("/chat")
	public String chat(Model model) {
//		SecurityContext context = SecurityContextHolder.getContext();
//		if (context != null && context.getAuthentication() != null) {
//		    // SecurityContext is set and contains an Authentication object
//		    // User is authenticated
//			logger.info("Security Context is Set .......");
//			Authentication authentication = context.getAuthentication();
//			if (authentication != null) {
//			    String username = authentication.getName();
//			    // You can now use the username or the entire authentication object as needed
//			    logger.info("Security Context user Role : {}", username);
//			} else {
//			    // User is not authenticated
//			}
//		} else {
//		    // SecurityContext is not set or does not contain an Authentication object
//		    // User is not authenticated
//			logger.info("Security Contex is Not Not Not Set ......");
//		}
		String photourl = modell.Photourl();
		model.addAttribute("photourl", "/image/"+photourl);
		return "chat";
	}
	
	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		return chatMessage;
	}

	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		// Add username in web socket session
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}
	
}
