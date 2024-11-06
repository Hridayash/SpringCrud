package com.CRUDPRACTICE.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.CRUDPRACTICE.demo.repo.BookRepo;
import com.CRUDPRACTICE.demo.model.Book;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
public class bookController {

   @Autowired
    private BookRepo bookRepo;

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {

        try{
            List<Book> bookList = new ArrayList<>();
            bookRepo.findAll().forEach(bookList::add);

            if(bookList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }
            return new ResponseEntity<>(bookList , HttpStatus.OK);

        }catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            
            Optional<Book> bookData = bookRepo.findById(id);
             if(bookData.isPresent()){
                return new ResponseEntity<>(bookData.get() , HttpStatus.OK);
             }
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        try{
            Book bookObj = bookRepo.save(book); 
            return new ResponseEntity<>(bookObj , HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id , @RequestBody Book newBookData){

        try{
            Optional <Book> bookData = bookRepo.findById(id);

            if(bookData.isPresent()){
                Book updateBook = bookData.get();
                updateBook.setName(newBookData.getName());
                updateBook.setAuthor(newBookData.getAuthor());

                Book bookObj = bookRepo.save(updateBook);

                return new ResponseEntity<>(bookObj , HttpStatus.OK);

            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(Exception ex){
            System.err.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    } 
 
    

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id){

        try{
            bookRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch(Exception ex ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
        
    
}
