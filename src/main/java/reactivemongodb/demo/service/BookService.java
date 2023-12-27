package reactivemongodb.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactivemongodb.demo.doa.BookRepo;
import reactivemongodb.demo.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BookService {
    @Autowired
   private BookRepo bookRepo;
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
    public Mono<Book> addBook(Book book){
       return bookRepo.save(book);
    }
    public Flux<Book> findBooks(){
        return bookRepo.findAll();
    }
    public Mono<Void> deleteBookById(String id){
        return bookRepo.deleteById(id);
    }
    public Mono<Book> updateBookById(String id,Book updatedBook){
      return  bookRepo.findById(id)
                .flatMap(book->{
                    book.setBookId(updatedBook.getBookId());
                    book.setGenre(updatedBook.getGenre());
                    book.setPrize(updatedBook.getPrize());
                    return bookRepo.save(book);
                });

    }
    public Mono<Book> findById(String id){
        return reactiveMongoTemplate.findById(id,Book.class);
    }
    public Mono<Book> findByBookName(String name){
        return bookRepo.findByName(name);
    }
    public Mono<Void> createBook(Flux<Book> books){
    return books
            .flatMap(book -> bookRepo.findByName(book.getName())
            .flatMap(existBook->{
                if(existBook != null){
                    return Mono.empty();
                }
                    return bookRepo.save(book);

            })).then();

    }
}
