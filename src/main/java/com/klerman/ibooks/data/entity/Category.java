package com.klerman.ibooks.data.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Category_Id")
	private Long id;
	
	@Column
	private String name;
	
	@OneToMany(mappedBy="category", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Book> books = new ArrayList<>();
	
	public Category() {}
	
	public Long getId () {
		return this.id;
	}
	
	public void setId(Long id) {
        this.id = id;
    }
	
	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public String toString() {
		return "Category { id=" + id + ", name=" + name + "}";
	}

}
