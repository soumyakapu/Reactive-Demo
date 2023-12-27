package reactivemongodb.demo.doa;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactivemongodb.demo.model.Book;
import reactor.core.publisher.Mono;

@EnableReactiveMongoRepositories
public interface BookRepo extends ReactiveMongoRepository<Book,String> {
    Mono<Book> findByName(String name);
}
