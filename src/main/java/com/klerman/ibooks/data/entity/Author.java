package com.klerman.ibooks.data.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Author {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column (nullable = false)
	private String name;
	
	@ManyToMany (mappedBy="authorList")
	private Set<Book> books = new HashSet<Book>();
	
	public Author() {}
	
	public Author (String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	public String toString() {
		return "Author {name=" + name  + "}";
	}

}
