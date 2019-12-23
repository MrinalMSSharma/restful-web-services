package com.rest.webservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJPAResource {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping(path = "/jpa/users")
	public List<User> retreiveAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping(path = "/jpa/user/{id}")
	public Resource<User> retreiveUser(@PathVariable Integer id){
		 Optional<User> user = userRepository.findById(id);
		 if(!user.isPresent()) {
			 throw new UserNotFoundException("id - " + id);
		 }
		 Resource<User> resource = new Resource<User>(user.get());
		 Link link = ControllerLinkBuilder.linkTo(this.getClass()).slash("jpa/users").withRel("all-users");
		 resource.add(link);
		 return resource;
	}
	
	@PostMapping(path = "/jpa/user")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(path = "/jpa/user/{id}")
	public void deleteById(@PathVariable Integer id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping(path = "/jpa/user/{id}/posts")
	public List<Post> retreiveAllPosts(@PathVariable Integer id){
		Optional<User> optionalUser = userRepository.findById(id);
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("id - " + id);
		}
		return optionalUser.get().getPosts();
	}
	
	@PostMapping(path = "/jpa/user/{id}/posts")
	public ResponseEntity<User> createPosts(@Valid @PathVariable Integer id, @RequestBody Post post) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("id - " + id);
		}
		User user = optionalUser.get();
		post.setUser(user);
		postRepository.save(post);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}