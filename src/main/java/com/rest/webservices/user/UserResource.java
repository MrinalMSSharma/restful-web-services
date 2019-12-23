package com.rest.webservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;
	
	@GetMapping(path = "/users")
	public List<User> retreiveAllUsers(){
		return service.findAll();
	}
	
	@GetMapping(path = "/user/{id}")
	public Resource<User> retreiveUser(@PathVariable Integer id){
		 User user = service.findOne(id);
		 if(null == user) {
			 throw new UserNotFoundException("id - " + id);
		 }
		 Resource<User> resource = new Resource<User>(user);
		 Link link = ControllerLinkBuilder.linkTo(this.getClass()).slash("users").withRel("all-users");
		 resource.add(link);
		 return resource;
	}
	
	@PostMapping(path = "/user")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.saveUser(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(path = "/user/{id}")
	public void deleteById(@PathVariable Integer id) {
		User deletedById = service.deleteById(id);
		if(deletedById == null) {
			throw new UserNotFoundException("id - " + id);
		}
	}
}