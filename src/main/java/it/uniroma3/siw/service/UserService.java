package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class UserService {
	 private UserRepository userRepository;

	@Transactional
	    public User saveUser(User user) {
	        return this.userRepository.save(user);
	    }

}
