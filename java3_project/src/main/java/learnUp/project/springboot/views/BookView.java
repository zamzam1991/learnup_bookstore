package learnUp.project.springboot.views;

import lombok.Data;

@Data
public class BookView {

    private Long id;
    private String name;
    private AuthorView author;
    private int yearOfPublication;
    private int pagesCount;
    private Double price;
}
