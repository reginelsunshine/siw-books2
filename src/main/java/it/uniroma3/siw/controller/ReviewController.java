package it.uniroma3.siw.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class ReviewController {
	@Autowired
	UserService userService;

	@Autowired 
	BookService bookService;

	@Autowired
	ReviewService reviewService;

	@GetMapping("/book/{id}/review")
	public String showReviewForm(@PathVariable("id") Long bookId, Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());

		Book book = bookService.findById(bookId);

		if (reviewService.alreadyReviewed(book, user)) {
			return "redirect:/book/" + bookId; // o messaggio che esiste già
		}

		Review review = new Review();
		review.setBook(book);
		review.setUser(user);

		model.addAttribute("review", review);
		return "reviewForm";
	}

	@PostMapping("/book/{id}/review")
	public String submitReview(@PathVariable("id") Long bookId,
			@Valid @ModelAttribute("review") Review review,
			BindingResult result,
			Model model,
			Principal principal) {

		if (result.hasErrors()) {
			return "reviewForm";
		}

		// Forza nuovo inserimento
		review.setId(null);

		Book book = bookService.findById(bookId);
		if (book == null) {
			// gestione errore, libro non trovato
			model.addAttribute("error", "Libro non trovato");
			return "error";
		}

		User user = userService.getUserByUsername(principal.getName());

		if (user == null) {
			// gestione errore, utente non trovato
			model.addAttribute("error", "Utente non trovato");
			return "error";
		}

		review.setBook(book);
		review.setUser(user);

		reviewService.save(review);

		return "redirect:/book/" + bookId;
	}


}