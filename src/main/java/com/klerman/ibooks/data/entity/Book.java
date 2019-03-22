package com.klerman.ibooks.data.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Book {
	
	public Book() {	}
	
	public Book(String name) {
		this.name = name;
	}
	
	public Book (String name, Category category, Author author) {
		this.name = name;
		this.category = category;
		this.authorList.add(author);
	}
	
	public Book (String name, Category category, Set<Author> authorList) {
		this.name = name;
		this.category = category;
		this.authorList = authorList;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Book_ID")
	private long id;
	
	@Column
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "cat_id", nullable=false)
	private Category  category;
	
	@ManyToMany
	@JoinTable(name = "book_author",
    joinColumns = { @JoinColumn(name = "fk_book") },
    inverseJoinColumns = { @JoinColumn(name = "fk_author") })
	private Set<Author> authorList = new HashSet<Author>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Author> getAuthorList() {
		return authorList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void setAuthorList(Set<Author> authorList) {
		this.authorList = authorList;
	}

	public String toString() {
		return "Book{ id=" + id + ", name=" +this.name + ", category=" + category + ", writers=" + authorList + "}";
	}
}
