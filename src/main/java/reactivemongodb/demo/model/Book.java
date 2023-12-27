package reactivemongodb.demo.model;

import com.mongodb.client.model.Collation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Book {
    @Id
    private String bookId;
    private String name;
    private List<String> genre;
    private double prize;
}
