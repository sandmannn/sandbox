package com.example.movies;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Controller
public class UIController {

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}

}