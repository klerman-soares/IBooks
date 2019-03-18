package com.klerman.ibooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.klerman.ibooks.data.entity.Book;
import com.klerman.ibooks.service.BookService;
import com.klerman.ibooks.util.PageWrapper;

@Controller
@RequestMapping (value="book")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@RequestMapping (value= {"/", "/list"})
	public String bookList(Model model, Pageable pageable) {
		PageWrapper<Book> page = new PageWrapper<Book> (bookService.findAll(pageable), "/book/list");
		model.addAttribute("page", page);		

		return "book-list";
	}

}
