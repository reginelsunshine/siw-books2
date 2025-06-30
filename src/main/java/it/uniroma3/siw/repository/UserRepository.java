package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;

public interface UserRepository  extends CrudRepository<User, Long> {
	Optional<User> findByName(String names);

	Optional<User> findBySurname(String surname);


	Optional<User> findByUsername(String username);

}
