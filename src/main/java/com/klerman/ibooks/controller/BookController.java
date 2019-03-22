package com.klerman.ibooks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.data.entity.Book;
import com.klerman.ibooks.data.entity.Category;
import com.klerman.ibooks.service.AuthorService;
import com.klerman.ibooks.service.BookService;
import com.klerman.ibooks.service.CategoryService;
import com.klerman.ibooks.util.PageWrapper;

@Controller
@RequestMapping (value="book")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	AuthorService authorService;
	
	@RequestMapping (value= {"/", "/list"})
	public String bookList(Model model, Pageable pageable) {
		PageWrapper<Book> page = new PageWrapper<Book> (bookService.findAll(pageable), "/book/list");
		model.addAttribute("page", page);		

		return "book-list";
	}
	
	@RequestMapping (value= {"/edit", "/edit/{id}"}, method = RequestMethod.GET)
	public String  bookEditForm(Model model, @PathVariable(required=false, name="id") Long id) {
		if (null != id) {
            model.addAttribute("book", bookService.findOne(id));
        } else {
            model.addAttribute("book", new Book());
        }
		
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		
		List<Author> authors = authorService.findAll();
		model.addAttribute("authors", authors);
		
		return "book-edit";
	}
	
	@RequestMapping (value="/edit", method = RequestMethod.POST)
	public String bookSave(Model model, Book book, Pageable pageable) {
		bookService.save(book);
		
		PageWrapper<Book> page = new PageWrapper<Book> (bookService.findAll(pageable), "/book/list");
		model.addAttribute("page", page);
		return "redirect:/book/list";
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String bookDelete(Model model, @PathVariable(required = true, name = "id") Long id, Pageable pageable) {
		bookService.deleteBook(id);
		
		return "redirect:/book/list";
	}

}
