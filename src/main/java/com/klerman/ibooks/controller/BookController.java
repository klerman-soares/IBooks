package com.klerman.ibooks.controller;

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.data.entity.Book;
import com.klerman.ibooks.data.entity.Category;
import com.klerman.ibooks.service.AuthorService;
import com.klerman.ibooks.service.BookService;
import com.klerman.ibooks.service.CategoryService;
import com.klerman.ibooks.util.PageWrapper;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping (value="book")
@SessionAttributes(names = { "book", "categories", "authors" })
public class BookController {
	
	private static Logger logger = LogManager.getLogger(BookController.class);
	
	public static final String VIEW_EDIT_FORM = "book-edit";
	public static final String VIEW_LIST = "book-list";
	public static final String REDIRECT_VIEW_LIST = "redirect:/book/list";
	
	@Value("${msg.book.save.success}") 
	String msgSaveSuccess;
	
	@Value("${msg.book.delete.success}") 
	String msgDeleteSuccess;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	AuthorService authorService;
	
	@RequestMapping (value= {"/", "/list"})
	@PreAuthorize("hasRole('ROLE_ADMIN_BOOK') or hasRole('ROLE_ADMIN')")
	public String bookList(
			Model model, 
			@PageableDefault(
					size=PageWrapper.MAX_PAGE_ITEM_DISPLAY, 
					sort={"id"}, 
					direction=Direction.DESC
			) Pageable pageable) {
		PageWrapper<Book> page = new PageWrapper<Book> (bookService.findAll(pageable), "/book/list");
		model.addAttribute("page", page);		
		
		return VIEW_LIST;
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
		
		return VIEW_EDIT_FORM;
	}
	
	@RequestMapping (value="/edit", method = RequestMethod.POST)
	public String bookSave(ModelMap model, 
			@Valid @ModelAttribute("book") Book book,
			BindingResult bindingResult, 
			SessionStatus sessionStatus,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {			
			return VIEW_EDIT_FORM;
		} else {		
			bookService.save(book);
			redirectAttributes.addFlashAttribute("msgOperationResult",msgSaveSuccess);
			sessionStatus.setComplete();
			return REDIRECT_VIEW_LIST;
		}
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String bookDelete(
			Model model, 
			@PathVariable(required = true, name = "id") Long id, 
			RedirectAttributes redirectAttributes) {
		bookService.deleteBook(id);
		redirectAttributes.addFlashAttribute("msgOperationResult", msgDeleteSuccess);
		return REDIRECT_VIEW_LIST;
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
	public void initBinderEdit(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("id");
	}

}
