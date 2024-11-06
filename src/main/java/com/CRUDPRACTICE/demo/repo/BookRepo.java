package com.CRUDPRACTICE.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.CRUDPRACTICE.demo.model.Book;

public interface BookRepo extends JpaRepository<Book ,  Long> {

}
