package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import it.uniroma3.siw.service.BookService;

@Controller
public class BookController {
	//ATTENZIONE, LA ROTTA /BOOK NON FUNZIONAVA PERCHE' HAI DIMENTICATO DI METTERE @AUTOWIRED ANCHE QUI SU bookService
	@Autowired
	private BookService bookService;

	//quando arriva una richiesta http get alla rotta /book esegui questo metodo e restituisci la lista di libri
	@GetMapping("/book")
	//String come tipo di ritorno poiché è il tipo di valore che il metodo restituisce ed in Spring è il nome della vista
	public String getBooks(Model model) { //Model model : oggetto fornito da Spring per passare dati alla vista
		//model.addAttribute(...) dice a Spring: “aggiungi dei dati da rendere disponibili alla pagina HTML”
		model.addAttribute("books", this.bookService.findAll()); //"books" è il nome che userò nella pagina HTML (Thymeleaf)
		                             //this.bookService.findAll() chiama il servizio per ottenere la lista di libri
		                            //bookService è un componente (@Service) che accede al database tramite BookRepository
		
		return "books.html"; //Questo dice a Spring:"Dopo aver eseguito il metodo, mostra la pagina templates/books.html"
	}
	
	/* DEBUG TEMPORANEO PER VEDERE SE STAMPA LA LISTA DEI LIBRI
	@GetMapping("/book")
	public String getBooks(Model model) {
	    Iterable<Book> allBooks = this.bookService.findAll();
	    System.out.println("Books found: " + allBooks);
	    model.addAttribute("books", allBooks);
	    return "books";
	}*/

	//NON STAVA FUNZIONANDO DANDO ERRORE DI Whitelabel Error Page POICHE' NON HAI ANCORA DEFINITO I METODI GETTER/SETTER ECC NEI MODEL
	@GetMapping("/book/{id}")
	public String getBook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("book", this.bookService.findById(id));
		return "book.html";
	}
}
