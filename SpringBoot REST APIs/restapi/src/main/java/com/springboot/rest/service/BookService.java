package com.springboot.rest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.dao.BookDao;
import com.springboot.rest.entities.Book;

@Service
@Transactional
public class BookService {

	@Autowired
	private BookDao bookdao;

	public BookDao getBookdao() {
		return bookdao;
	}

	public void setBookdao(BookDao bookdao) {
		this.bookdao = bookdao;
	}
	
	public Book saveBookDetails(Book book) {
		return bookdao.save(book);
	}

	public Book getBookDetails(int bookid) {
		return bookdao.findByBookid(bookid);
	}
}
