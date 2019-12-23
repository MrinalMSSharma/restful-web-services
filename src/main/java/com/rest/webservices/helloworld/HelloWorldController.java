package com.rest.webservices.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(method=RequestMethod.GET, value="/hello")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path = "helloworld")
	public HelloWorld helloWorldBean() {
		return new HelloWorld("Hello World");
	}
	
	@GetMapping(path = "helloworldagain/{name}")
	public HelloWorld helloWorldAgain(@PathVariable String name) {
		return new HelloWorld("Hello World " + name);
	}
	
	@GetMapping(path = "/helloWorldInternationalized")
	public String helloWorldInternationalized() {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
}