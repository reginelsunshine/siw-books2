package it.uniroma3.siw.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Book;
import jakarta.validation.constraints.AssertFalse.List;
import it.uniroma3.siw.model.Author;



public interface BookRepository extends CrudRepository<Book, Long> {

	boolean existsByTitleIgnoreCaseAndYearOfPublication(String title, Integer yearOfPublication);

	boolean existsByTitleIgnoreCaseAndYearOfPublicationAndIdNot(String title, Integer yearOfPublication, Long id);
	
	
	
	
	
	
	
	
	
	
	Set<Book> findAllByOrderByTitleDesc();

}
