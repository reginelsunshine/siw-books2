package it.uniroma3.siw.controller;

import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.BookRepository;
import it.uniroma3.siw.controller.validator.BookValidator;
import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.BookService;
import jakarta.validation.Valid;
import it.uniroma3.siw.service.AuthorService;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@Controller
public class BookController {
	//ATTENZIONE, LA ROTTA /BOOK NON FUNZIONAVA PERCHE' HAI DIMENTICATO DI METTERE @AUTOWIRED ANCHE QUI SU bookService
	@Autowired
	private BookService bookService;
	
	@Autowired 
	private BookRepository bookRepository;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private BookValidator bookValidator;

	//quando arriva una richiesta http get alla rotta /book esegui questo metodo e restituisci la lista di libri
	@GetMapping("/book")
	//String come tipo di ritorno poiché è il tipo di valore che il metodo restituisce ed in Spring è il nome della vista
	public String getBooks(Model model) { //Model model : oggetto fornito da Spring per passare dati alla vista
		//model.addAttribute(...) dice a Spring: “aggiungi dei dati da rendere disponibili alla pagina HTML”
		model.addAttribute("books", this.bookService.findAll()); //"books" è il nome che userò nella pagina HTML (Thymeleaf)
		//this.bookService.findAll() chiama il servizio per ottenere la lista di libri
		//bookService è un componente (@Service) che accede al database tramite BookRepository
		  // Forza caricamento authors per evitare errori LazyInitialization
		//Iterable<Book> books = this.bookService.findAll();
		
		
	  /*  for (Book book : books) {
	        book.getAuthors().size();
	    }*/
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
		Book book = bookService.findById(id);
		List<Review> reviews = bookService.getReviewsForBook(id);
		model.addAttribute("book", book);
		model.addAttribute("reviews", reviews);
		Set<Author> authorsBook = bookService.getAuthorsOfBook(id);
		model.addAttribute("authorsBook", authorsBook);

		return "book.html";
	}
	/*NB: ANCHE QUA NON FUNZIONAVA PERCHE NON AVEVI ANCORA FATTO I TODO DEI METODI GET/ECC...E LA ROTTA NON E'
	   ad es admin/formUpdateBook... ma http://localhost:8080/book/update/4 */
	// GET: mostra lista libri da selezionare per modifica
	/**
	 * Unica rotta per mostrare:
	 * - lista libri se non è passato id
	 * - form modifica libro se è passato id come parametro request
	 */
	// Mostra il form con lista libri e dati libro selezionato (se presente)
	@GetMapping("/admin/formUpdateBook")
	public String showUpdateBookForm(@RequestParam(required = false) Long selectedBookId, Model model) {
		Iterable<Book> books = bookService.findAll();
		model.addAttribute("books", books);

		Book book = null;
		if (selectedBookId != null) {
			book = bookService.findById(selectedBookId);
		}
		model.addAttribute("book", book);

		return "/admin/formUpdateBook.html";
	}

	// Riceve il form aggiornamento libro
	@PostMapping("/admin/formUpdateBook")
	public String updateBook(
			@Valid @ModelAttribute("book") Book book,
			BindingResult bindingResult,
			@RequestParam("imageFile") MultipartFile imageFile,
			Model model) throws IOException {

		bookValidator.validate(book, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("books", bookService.findAll());
			return "/admin/formUpdateBook.html";
		}

		if (!imageFile.isEmpty()) {
			book.setImage(imageFile.getBytes());
		}

		bookService.save(book);
		return "redirect:/book";
	}


	@GetMapping("/book/image/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> getBookImage(@PathVariable Long id) {
		Book book = bookService.findById(id);
		if (book == null || book.getImage() == null) {
			return ResponseEntity.notFound().build();
		}

		byte[] image = book.getImage();
		String type = book.getImageType();

		MediaType mediaType;
		try {
			mediaType = MediaType.parseMediaType(type);
		} catch (Exception e) {
			mediaType = MediaType.APPLICATION_OCTET_STREAM; // fallback generico
		}

		return ResponseEntity
				.ok()
				.contentType(mediaType)
				.body(image);
	}



	@GetMapping("/admin/indexBook")
	public String getAdminIndexBook(Model model) {
		return "/admin/indexBook.html";
	}


	/*@GetMapping("/admin/formAddBook")
	public String showAddBookForm(Model model) {
		model.addAttribute("book", new Book());
		return "/admin/formAddBook";
	}*/
	@GetMapping("/admin/formAddBook")
	public String showAddBookForm(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("authors", authorService.findAll()); // Passa tutti gli autori
		return "/admin/formAddBook";
	}


	/*@PostMapping("/admin/formAddBook")
	public String addBook(@ModelAttribute("book") Book book,
			@RequestParam("imageFile") MultipartFile imageFile,
			@RequestParam("authorName") String authorName,
			@RequestParam("authorSurname") String authorSurname) throws IOException {

		// Gestione copertina
		if (!imageFile.isEmpty()) {
			book.setImage(imageFile.getBytes());
		}

		// Ricerca o creazione autore
		if (!authorName.isBlank() && !authorSurname.isBlank()) {
			Author author = authorService.findByNameAndSurname(authorName, authorSurname);
			if (author == null) {
				author = new Author();
				author.setName(authorName);
				author.setSurname(authorSurname);
				authorService.save(author);
			}
			book.setAuthors(Set.of(author));
		}

		bookService.save(book);
		return "redirect:/book";
	}*/

	@PostMapping("/admin/formAddBook")
	public String addBook(
			@Valid @ModelAttribute("book") Book book,
			BindingResult bindingResult,                 // subito dopo @Valid!
			@RequestParam("imageFile") MultipartFile imageFile,
			@RequestParam("authorIds")  List<Long> authorIds,
			Model model) throws IOException {

		/* Richiamiamo il validatore custom */
		bookValidator.validate(book, bindingResult);
		
		if (bookService.alreadyExists(book)) {
		    bindingResult.rejectValue("title", "book.title.duplicate");
		}

		/* Se ci sono errori, torniamo al form con gli stessi dati */
		if (bindingResult.hasErrors()) {
			model.addAttribute("authors", authorService.findAll());
			return "/admin/formAddBook";
		}

		/* -------- gestione immagine e autori -------- */
		if (!imageFile.isEmpty()) {
			book.setImage(imageFile.getBytes());
		}

		/* oppure: Set<Author> selectedAuthors = new HashSet<>();
for (Long authorId : authorIds) {
    Author author = authorService.findById(authorId);
    if (author != null) {
        selectedAuthors.add(author);
    }
}
book.setAuthors(selectedAuthors);
		 */

		Set<Author> selectedAuthors = authorIds.stream()
				.map(authorService::findById)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		book.setAuthors(selectedAuthors);

		bookService.save(book);
		return "redirect:/book";
	}



	// Mostra il form per eliminare libro
	@GetMapping("/admin/formDeleteBook")
	public String showDeleteBookForm(Model model) {
		model.addAttribute("books", bookService.findAll());
		return "/admin/formDeleteBook.html";
	}

	// Riceve id libro da eliminare e lo cancella
	@PostMapping("/deleteBook")
	public String deleteBook(@RequestParam("bookId") Long bookId) {
		bookService.deleteById(bookId);
		return "redirect:/admin/indexBook";
	}

	@GetMapping("/admin/formDeleteReviewFromBook")
	public String showDeleteReviewForm(@RequestParam(value = "bookId", required = false) Long bookId, Model model) {
		model.addAttribute("books", bookService.findAll());

		if (bookId != null) {
			Book book = bookService.findById(bookId);
			if (book != null) {
				List<Review> reviews = bookService.getReviewsForBook(bookId);
				model.addAttribute("selectedBook", book);
				model.addAttribute("reviews", reviews);
			}
		}

		return "/admin/formDeleteReviewFromBook.html";
	}

	@PostMapping("/admin/deleteReview")
	public String deleteReview(@RequestParam("reviewId") Long reviewId,
			@RequestParam("bookId") Long bookId) {
		bookService.deleteReviewById(reviewId);
		return "redirect:/admin/formDeleteReviewFromBook?bookId=" + bookId;
	}

}
