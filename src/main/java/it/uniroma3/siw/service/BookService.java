package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.BookRepository;
import it.uniroma3.siw.repository.ReviewRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired 
	private ReviewRepository reviewRepository;

	public Iterable<Book> findAll() {
		return bookRepository.findAll();
	}

	public Book findById(Long id) {
		return bookRepository.findById(id).get();
	}

	public void save(Book book) {
		bookRepository.save(book);
	}

	public List<Review> getReviewsForBook(Long bookId) {
		Book book = this.findById(bookId); // oppure bookRepository.findById(id).get()
		return book.getReviews(); // ritorna le recensioni associate
	}

	public void deleteById(Long id) {
		bookRepository.deleteById(id);
	}
	
	
    public void deleteReviewById(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
