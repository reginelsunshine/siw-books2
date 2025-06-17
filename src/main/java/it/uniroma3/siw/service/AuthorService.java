package it.uniroma3.siw.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Author;
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


}
