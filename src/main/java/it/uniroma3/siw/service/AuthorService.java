package it.uniroma3.siw.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.AuthorRepository;
@Service
public class AuthorService {
	@Autowired
	private AuthorRepository authorRepository;
	public Iterable<Author> findAll() {
		return authorRepository.findAll();
	}

	public Set<Author> findAllById(List<Long> authorIds) {
		Iterable<Author> authorsIterable = authorRepository.findAllById(authorIds);
		Set<Author> authors = new HashSet<>();
		authorsIterable.forEach(authors::add);
		return authors;
	}

	public Author findById(Long id) {
		//NON DIMENTICARE DI CHIAMRE ANCHE .GET() PER OTTENERE L'AUTORE EFFETTIVAMENTE
		return authorRepository.findById(id).get();
	}





	public Author findByNameAndSurname(String authorName, String authorSurname) {
		return authorRepository.findByNameAndSurname(authorName, authorSurname).orElse(null);
	}

	public void save(Author author) {
		authorRepository.save(author);
	}

	public boolean alreadyExists(Author author) {
		return authorRepository
				.existsByNameIgnoreCaseAndSurnameIgnoreCaseAndDateOfBirth(
						author.getName(), author.getSurname(), author.getDateOfBirth());
	}





}
