package learnUp.project.springboot.views;

import lombok.Data;

@Data
public class OrderDetailView {

    private long id;
    private BookView book;
    private int booksCount;
}
