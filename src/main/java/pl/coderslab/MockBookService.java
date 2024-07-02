package pl.coderslab;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Setter
public class MockBookService implements BookService {
    private List<Book> list;
    private static Long nextId = 101L;

    public MockBookService() {
        list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(new Book(
                    (long) i,
                    "ISBN" + i,
                    "Title " + i,
                    "Author " + i,
                    "Publisher " + i,
                    "Type " + i
            ));
        }

    }


    @Override
    public List<Book> getBooks() {
        return list.stream().toList();
    }

    @Override
    public Optional<Book> get(Long id) {
        return list.stream().filter(b -> id.equals(b.getId())).findFirst();
    }

    public void add(Book book) {
        book.setId(nextId++);
        list.add(book);
    }


    @Override
    public void delete(Long id) {
        List<Book> toSave = list.stream().filter(b -> !id.equals(b.getId())).toList();
        setList(toSave);

    }

    @Override
    public void update(Book book) {
        if (this.get(book.getId()).isPresent()) {
            int indexOf = list.indexOf(this.get(book.getId()).get());
            List<Book> toSave = new ArrayList<>(list.stream().filter(b -> !book.getId().equals(b.getId())).toList());
            toSave.set(indexOf, book);
            setList(toSave);
        }


    }

}
