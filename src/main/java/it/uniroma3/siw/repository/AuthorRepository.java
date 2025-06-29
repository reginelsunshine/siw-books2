package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Author;

public interface AuthorRepository  extends CrudRepository<Author, Long>{
	Optional<Author> findByNameAndSurname(String name, String surname);
	
	Optional<Author> findById(Long id);
	
	
	//nota bene: <operation>By<property><condition>OrderBy<property>Asc/Desc
	List<Author> findAllByOrderBySurnameAscNameAsc();

	boolean existsByNameIgnoreCaseAndSurnameIgnoreCaseAndDateOfBirth(String name, String surname,
			LocalDate dateOfBirth);

	boolean existsByNameIgnoreCaseAndSurnameIgnoreCaseAndDateOfBirthAndIdNot(String name, String surname,
			LocalDate dateOfBirth, Long id);

	boolean existsByNameAndSurnameAndDateOfBirth(String name, String surname, LocalDate dateOfBirth);
	

}
