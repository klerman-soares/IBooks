package com.klerman.ibooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.klerman.ibooks.data.entity.Book;
import com.klerman.ibooks.data.repository.BookRepository;


@Service
public class BookServiceImpl implements BookService{
	
	private BookRepository bookRepository;
	
	@Autowired
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
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
