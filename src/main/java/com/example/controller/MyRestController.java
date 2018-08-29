package com.example.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Message;

@RestController
public class MyRestController {

	@RequestMapping("/")
	public String welcome() {//Welcome page, non-rest
		return "Welcome to RestTemplate Example.";
	}

	@RequestMapping("/hello/{player}")
	public Message message(@PathVariable String player) {//REST Endpoint.

		Message msg = new Message(player, "Hello " + player);
		return msg;
	}

	
}
