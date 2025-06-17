package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import it.uniroma3.siw.repository.AuthorRepository;
import it.uniroma3.siw.service.AuthorService;

@Controller
public class AuthorController {
	@Autowired 
	private AuthorService authorService;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	//voglio l'elenco di tutti gli autori
	@GetMapping("/author")
	public String getAuthors(Model model) {
		//QUI NON MI STAVA PRENDENDO model.addAttribute poiché l'IMPORT non era quello corretto, attenzione!
		//DA CONTROLLARE DIFFERENZA TRA USARE this.authorRepository.findAll() e this.authorService.findAll()
		model.addAttribute("authors", this.authorRepository.findAll());
		return "/authors.html";
	}
}
