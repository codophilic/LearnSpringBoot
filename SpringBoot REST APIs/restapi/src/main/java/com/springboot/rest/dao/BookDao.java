package com.springboot.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rest.entities.Book;
import java.util.List;


@Repository
public interface BookDao extends JpaRepository<Book, Integer> {

	Book findByBookid(int bookid);
}
