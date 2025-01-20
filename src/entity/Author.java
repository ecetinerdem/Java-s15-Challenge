package entity;

public class Author extends Person{

    private Set<Book> books;

    public Author (Long id, String name, String email) {
        super()
    }


    @Override
    public String whoYouAre() {
        return "Author " + name;
    }
}
