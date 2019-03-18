package com.klerman.ibooks.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.data.entity.Book;
import com.klerman.ibooks.data.entity.Category;
import com.klerman.ibooks.data.repository.AuthorRepository;
import com.klerman.ibooks.data.repository.BookRepository;
import com.klerman.ibooks.data.repository.CategoryRepository;


@Service
public class BookServiceImpl implements BookService{
	
	private BookRepository bookRepository;
	private CategoryRepository categoryRepository;
	private AuthorRepository authorRepository;
	
	@Autowired
	public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository) {
		this.bookRepository = bookRepository;
		this.categoryRepository = categoryRepository;
		this.authorRepository = authorRepository;
	}
	
	public Book addBook (Book book) {
		Book newBook = bookRepository.save(book);
		return newBook;
	}
	
	public void loadBooks() {
		
		Category cat1 = new Category("Programming");
		categoryRepository.save(cat1);
		
		Author author = new Author("Agatha");
		authorRepository.save(author);
		HashSet<Author> authorList = new HashSet<Author>();
		authorList.add(author);
		
		Book book1 = new Book("Java How to program");
		Book book2 = new Book("Head First");
		Book book3 = new Book("Beginners guide");
		Book book4 = new Book("Core Java");
		
		Stream<Book> books = Stream.of(book1, book2, book3, book4);		
		
		books.forEach(book -> {
			book.setCategory(cat1);
			book.setAuthorList(authorList);
			bookRepository.save(book);
		});	
		
		
		//bookRepository.findAll().forEach(System.out::println);
	}
	
	public List<Book> getBooks() {
		List<Book> list = new ArrayList<Book>();
		bookRepository.findAll().forEach(book -> {
			list.add(book);
		});
		return list;
	}

	@Override
	public Page<Book> findAll(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}

	@Override
	public Book findOne(Long id) {
		return bookRepository.findById(id).orElse(null);
	}

	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);		
	}

}
