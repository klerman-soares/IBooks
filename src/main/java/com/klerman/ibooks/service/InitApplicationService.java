package com.klerman.ibooks.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klerman.ibooks.auth.AuthGroup;
import com.klerman.ibooks.auth.AuthGroupRepository;
import com.klerman.ibooks.auth.User;
import com.klerman.ibooks.auth.UserService;
import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.data.entity.Book;
import com.klerman.ibooks.data.entity.Category;

@Service
@Profile(value = {"dev"})
public class InitApplicationService {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(InitApplicationService.class);

	 @Autowired
	 CategoryService categoryService;	 
	 
	 @Autowired
	 AuthorService authorService;
	 
	 @Autowired
	 BookService bookService;
	 
	 @Autowired
	 UserService userService;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 AuthGroupRepository authGroupRepository;

	 @EventListener(ApplicationReadyEvent.class)
	 public void initializeTestData() {
		 LOGGER.info("Initialize test data");

		 // Category
		 Category category1 = categoryService.saveCategory(new Category("Computers & Technology"));
		 Category category2 = categoryService.saveCategory(new Category("Comics & Graphic Novels"));
		 categoryService.saveCategory(new Category("Arts & Photography"));
		 categoryService.saveCategory(new Category("Biographies & Memoirs"));
		 categoryService.saveCategory(new Category("Business & Money"));
		 categoryService.saveCategory(new Category("Calendars"));
		 categoryService.saveCategory(new Category("Children's Books"));
		 categoryService.saveCategory(new Category("Christian Books & Bibles"));
		 categoryService.saveCategory(new Category("Cookbooks, Food & Wine"));
		 categoryService.saveCategory(new Category("Crafts, Hobbies & Home"));
		 categoryService.saveCategory(new Category("Education & Teaching"));
		 categoryService.saveCategory(new Category("Engineering & Transportation"));
		 categoryService.saveCategory(new Category("Health, Fitness & Dieting"));
		 categoryService.saveCategory(new Category("History"));
		 categoryService.saveCategory(new Category("Humor & Entertainment"));
		 
		 // Author
		 Author author1 = authorService.saveAuthor(new Author("James Patterson"));
		 Author author2 = authorService.saveAuthor(new Author("J. K. Rowling"));
		 Author author3 = authorService.saveAuthor(new Author("Robert Dugoni"));
		 Author author4 = authorService.saveAuthor(new Author("Stephen King"));
		 Author author5 = authorService.saveAuthor(new Author("The Great Courses"));
		 Author author6 = authorService.saveAuthor(new Author("Kendra Elliot"));
		 Author author7 = authorService.saveAuthor(new Author("Charles Krauthammer"));
		 Author author8 = authorService.saveAuthor(new Author("Nora Roberts"));
		 Author author9 = authorService.saveAuthor(new Author("Minka Kent"));
		 Author author10 = authorService.saveAuthor(new Author("Bella Forrest"));
		 authorService.saveAuthor(author1);
		 authorService.saveAuthor(author2);
		 authorService.saveAuthor(author3);
		 authorService.saveAuthor(author4);
		 authorService.saveAuthor(author5);
		 authorService.saveAuthor(author6);
		 authorService.saveAuthor(author7);
		 authorService.saveAuthor(author8);
		 authorService.saveAuthor(author9);
		 authorService.saveAuthor(author10);
		 
		 LocalDate dateNow = LocalDate.of(2019, 02, 20);
		 
		 // Book
		 bookService.save(new Book("Testing Java Microservices", category1, author1, dateNow));
		 bookService.save(new Book("Algorithms Illuminated", category1, author2, dateNow));
		 bookService.save(new Book("Super Mario: How Nintendo Conquered America", category1, author3, dateNow));
		 bookService.save(new Book("Python Crash Course", category1, author4, dateNow));
		 bookService.save(new Book("Javascript for Babies", category1, author5, dateNow));
		 bookService.save(new Book("Spring Boot and Single-Page Applications", category1, author6, dateNow));
		 bookService.save(new Book("Head First Servlets and JSP", category1, author7, dateNow));
		 bookService.save(new Book("Web Development with Java", category1, author8, dateNow));
		 bookService.save(new Book("Automate the Boring Stuff with Python", category1, author9, dateNow));
		 bookService.save(new Book("The Ultimate Roblox Book", category2, author10, dateNow));
		 
		 String user = "user";
		 String admin = "admin";
		 String book_admin = "book";
		 userService.save(new User(user, passwordEncoder.encode("1234")));
		 userService.save(new User(admin, passwordEncoder.encode("1234")));
		 userService.save(new User(book_admin, passwordEncoder.encode("1234")));

		 authGroupRepository.save(new AuthGroup(user, "USER"));
		 authGroupRepository.save(new AuthGroup(book_admin, "ADMIN_BOOK"));
		 authGroupRepository.save(new AuthGroup(admin, "ADMIN"));
		 authGroupRepository.save(new AuthGroup(admin, "ADMIN_BOOK"));

	     LOGGER.info("Initialization completed");
	 }

}
