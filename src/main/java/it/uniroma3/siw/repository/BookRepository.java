package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Book;
import jakarta.validation.constraints.AssertFalse.List;
import it.uniroma3.siw.model.Author;

public interface BookRepository extends CrudRepository<Book, Long> {

}
