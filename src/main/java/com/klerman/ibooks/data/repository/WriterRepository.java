package com.klerman.ibooks.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.klerman.ibooks.data.entity.Writer;

@Repository
public interface WriterRepository extends CrudRepository<Writer, Long> {
	
	public Writer findByName(String name);

}
