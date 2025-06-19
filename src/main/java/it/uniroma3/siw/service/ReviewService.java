package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ReviewRepository;
import jakarta.validation.Valid;

@Service
public class ReviewService {
	@Autowired 
	ReviewRepository reviewRepository;
	public boolean alreadyReviewed(Book book, User user) {
	    return reviewRepository.findByBookAndUser(book, user).isPresent();
	}
	public void save(@Valid Review review) {
		reviewRepository.save(review);
	}

}
