package reactivemongodb.demo.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactivemongodb.demo.handler.BookHandler;
@Configuration
public class BookRouter {
    @Autowired
    BookHandler bookHandler;
    @Bean
    public RouterFunction<ServerResponse> routerFunction(BookHandler bookHandler){
         return RouterFunctions.route()
                 .POST("/add", bookHandler::postBook)
                 .GET("/getAllBooks", bookHandler::getAllBooks)
                 .DELETE("/delete/{id}",bookHandler::deleteById)
                 .PUT("/updateBook/{id}",bookHandler::updateBookId)
                 .GET("/get/{id}",bookHandler::findByBookId)
                 .GET("/get/{name}",bookHandler::findByBookName)
                 .POST("/api/v2/create",bookHandler::createBook)
                 .build();
    }
}
