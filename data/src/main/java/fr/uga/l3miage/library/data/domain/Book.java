package fr.uga.l3miage.library.data.domain;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@NamedQueries({
    @NamedQuery(name = "all-books", 
        query = "select b from Book b  ORDER BY b.title  ASC"),

    @NamedQuery(name = "find-books-by-title",
        query = "select t from Book t where lower(t.title) LIKE :name"),

    @NamedQuery(name="find-books-by-author-and-title",
        query = "select b from Author a join Book b where a.id = :id and lower(b.title) LIKE :title"),

    @NamedQuery(name ="find-books-by-authors-name",
        query = "select b FROM Book b JOIN b.authors a WHERE lower(a.fullName) LIKE :name"),

    @NamedQuery(name ="find-books-by-several-authors",
        query = "select b FROM Book b JOIN b.authors a GROUP BY b.id HAVING COUNT(a.id) > :count")
        //query = "select b FROM Book b GROUP BY b.id HAVING COUNT(b.authors) > :count")

})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable =  false)
    private String title;

    private long isbn;

    private String publisher;

    @Column(name ="annee")
    @Temporal(TemporalType.DATE)
    private short year;

    // A revoire sur l'enumaration
    @Enumerated
    private Language language;

    @ManyToMany(mappedBy ="books")
    private Set<Author> authors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        if (this.authors == null) {
            this.authors = new HashSet<>();
        }
        this.authors.add(author);
    }

    public enum Language {
        FRENCH,
        ENGLISH
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn == book.isbn && year == book.year && Objects.equals(title, book.title) && Objects.equals(publisher, book.publisher) && language == book.language && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isbn, publisher, year, language, authors);
    }
}
