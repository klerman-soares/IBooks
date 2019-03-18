package com.klerman.ibooks.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Writer {
	
	public Writer() {}
	
	public Writer(String name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "Writer_Id")
	private long id;	
	
	@Column (nullable = false)
	private String name;	
	
	@Column
	private int age;
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}

	public String toString() {
		return "Writer{name=" + name + ", age=" + age + "}";
	}
}
