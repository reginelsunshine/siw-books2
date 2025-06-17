package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.BookService;

@Controller
public class BookController {
	//ATTENZIONE, LA ROTTA /BOOK NON FUNZIONAVA PERCHE' HAI DIMENTICATO DI METTERE @AUTOWIRED ANCHE QUI SU bookService
	@Autowired
	private BookService bookService;

	//quando arriva una richiesta http get alla rotta /book esegui questo metodo e restituisci la lista di libri
	@GetMapping("/book")
	public String getBooks(Model model) {
		model.addAttribute("books", this.bookService.findAll());
		return "books.html";
	}
}
