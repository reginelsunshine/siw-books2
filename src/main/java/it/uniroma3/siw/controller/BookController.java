package it.uniroma3.siw.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.AuthorService;

@Controller
public class BookController {
	//ATTENZIONE, LA ROTTA /BOOK NON FUNZIONAVA PERCHE' HAI DIMENTICATO DI METTERE @AUTOWIRED ANCHE QUI SU bookService
	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorService authorService;

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
	
	@GetMapping("/admin/book/update/{id}")
	public String showUpdateBookForm(@PathVariable("id") Long id, Model model) {
	    Book book = bookService.findById(id);
	    Iterable<Author> authors = authorService.findAll(); // o qualsiasi metodo tu abbia

	    model.addAttribute("book", book);
	    model.addAttribute("authors", authors);
	    return "admin/updateBook";
	}
	
	
	@PostMapping("/admin/book/update/{id}")
	public String updateBook(@PathVariable("id") Long id,
	                         @RequestParam("title") String title,
	                         @RequestParam("yearOfPublication") Integer year,
	                         @RequestParam("authorIds") List<Long> authorIds,
	                         @RequestParam("image") MultipartFile imageFile) throws Exception {

	    Book book = this.bookService.findById(id);
	    book.setTitle(title);
	    book.setYearOfPublication(year);

	    // Associazione autori
	    Set<Author> authors = new HashSet<>(this.authorService.findAllById(authorIds));
	    book.setAuthors(authors);

	    // Caricamento immagine solo se presente
	    if (!imageFile.isEmpty()) {
	        book.setImage(imageFile.getBytes()); // Salva i byte nel campo @Lob
	    }

	    this.bookService.save(book);
	    return "redirect:/book/" + id;
	}
	
	@GetMapping("/book/image/{id}")
	@ResponseBody
	public byte[] getBookImage(@PathVariable("id") Long id) {
	    return this.bookService.findById(id).getImage();
	}


}
