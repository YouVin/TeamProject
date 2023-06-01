package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	@GetMapping("/")
	public String login() {
		return "login";
	}
	
	@GetMapping("/home")
	public String home() {
		return "Home";
	}

	@GetMapping("/map")
	public String map() {
		return "map";
	}
	
	@GetMapping("/board")
	public String Board() {
		return "index";
	}
}

