package com.klerman.ibooks.controller;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.data.entity.Book;
import com.klerman.ibooks.data.entity.Category;
import com.klerman.ibooks.service.AuthorService;
import com.klerman.ibooks.service.BookService;
import com.klerman.ibooks.service.CategoryService;
import com.klerman.ibooks.util.PageWrapper;

@Controller
@RequestMapping (value="book")
@SessionAttributes(names = { "book", "categories", "authors" })
public class BookController {
	
	private static Logger logger = LogManager.getLogger(BookController.class);
	
	@Autowired
	BookService bookService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	AuthorService authorService;
	
	@RequestMapping (value= {"/", "/list"})
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
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
	public String bookSave(Model model, 
			@Valid @ModelAttribute("book") Book book,
			BindingResult bindingResult, SessionStatus sessionStatus, 
			Pageable pageable) {
		if (bindingResult.hasErrors()) {			
			return "book-edit";
		} else {		
			bookService.save(book);
			
			PageWrapper<Book> page = new PageWrapper<Book> (bookService.findAll(pageable), "/book/list");
			model.addAttribute("page", page);
			sessionStatus.setComplete();
			return "redirect:/book/list";
		}
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String bookDelete(Model model, @PathVariable(required = true, name = "id") Long id, Pageable pageable) {
		bookService.deleteBook(id);
		
		return "redirect:/book/list";
	}
	
	@InitBinder
	public void initBinder_New(WebDataBinder webDataBinder) {
		logger.info("initBinder_New() method: Registering CustomDateEditor");
		
		PropertyEditor editor = new PropertyEditorSupport() {
	        @Override
	        public void setAsText(String text) throws IllegalArgumentException {
	            if (!text.trim().isEmpty())
	                super.setValue(LocalDate.parse(text.trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	        }
	        @Override
	        public String getAsText() {
	            if (super.getValue() == null)
	                return null;
	            LocalDate value = (LocalDate) super.getValue();
	            return value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	        }
	    };
		
		webDataBinder.registerCustomEditor(LocalDate.class, editor);
	}
	
	@InitBinder(value = "book")
	public void initBinder_Edit(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("id");
	}

}
