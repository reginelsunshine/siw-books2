package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Author;

public interface AuthorRepository  extends CrudRepository<Author, Long>{
	Optional<Author> findByNameAndSurname(String name, String surname);
	
	Optional<Author> findById(Long id);
	
	List<Author> findAllByOrderBySurnameAscNameAsc();

}
