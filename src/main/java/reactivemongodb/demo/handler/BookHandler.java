package reactivemongodb.demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactivemongodb.demo.model.Book;
import reactivemongodb.demo.service.BookService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class BookHandler {

    @Autowired
    private BookService bookService;
    public Mono<ServerResponse> postBook(ServerRequest serverRequest){
        Mono<Book> bookMono = serverRequest.bodyToMono(Book.class);
        Mono<Book> bookMono1 = bookMono.flatMap(book -> bookService.addBook(book));
        return ServerResponse.accepted().body(bookMono1,Book.class);
    }
    public Mono<ServerResponse> getAllBooks(ServerRequest serverRequest){
        Flux<Book> books = bookService.findBooks();
        return ServerResponse.ok().body(books,Book.class);
    }
    public Mono<ServerResponse> deleteById(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        Mono<Void> voidMono = bookService.deleteBookById(id);
        return ServerResponse.ok().body(voidMono,Book.class);
    }
    public Mono<ServerResponse> updateBookId(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        Mono<Book> bookMono = serverRequest.bodyToMono(Book.class).flatMap(book -> bookService.updateBookById(id, book));
        return ServerResponse.accepted().body(bookMono,Book.class);

    }
    public Mono<ServerResponse> findByBookId(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok().body(bookService.findById(id),Book.class);
    }
    public Mono<ServerResponse> findByBookName(ServerRequest serverRequest){
        String name = serverRequest.pathVariable("name");
        return  ServerResponse.ok().body(bookService.findByBookName(name),Book.class);
    }
    public Mono<ServerResponse> createBook(ServerRequest serverRequest){
        Mono<Book> bookMono = serverRequest.bodyToMono(Book.class);
        Mono<Void> voidMono = bookMono.flatMap((book) -> bookService.createBook(Flux.just(book)));
        return ServerResponse.ok().body(voidMono,Book.class);
    }
}
