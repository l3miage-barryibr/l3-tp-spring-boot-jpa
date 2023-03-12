package fr.uga.l3miage.library.data.repo;

import fr.uga.l3miage.library.data.domain.Author;
import fr.uga.l3miage.library.data.domain.Book;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorRepository implements CRUDRepository<Long, Author> {

    private final EntityManager entityManager;

    @Autowired
    public AuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Author save(Author author) {
        entityManager.persist(author);
        return author;
    }

    @Override
    public Author get(Long id) {
        return entityManager.find(Author.class, id);
    }


    @Override
    public void delete(Author author) {
        entityManager.remove(author);
    }

    /**
     * Renvoie tous les auteurs
     *
     * @return une liste d'auteurs trié par nom
     */
    @Override
    public List<Author> all() {
        String reqAll = "select a from Author a where a.fullName = :fullName ";
        return entityManager.createQuery(reqAll, Author.class)
                .getResultList();
    }
    /**
     * Recherche un auteur par nom (ou partie du nom) de façon insensible  à la casse.
     *
     * @param namePart tout ou partie du nomde l'auteur
     * @return une liste d'auteurs trié par nom
     */
    public List<Author> searchByName(String namePart) {
        String search  = '%' + namePart +'%';
        String reqName = "select n from Author n where n.fullName LIKE :name";
        return entityManager.createQuery(reqName, Author.class)
                .setParameter("name", search)
                .getResultList();
    }
    /**
     * Recherche si l'auteur a au moins un livre co-écrit avec un autre auteur
     *
     * @return true si l'auteur partage
     */

    public boolean checkAuthorByIdHavingCoAuthoredBooks(long authorId) {
        Author auteur = entityManager.find(Author.class,authorId);
        for(Book book: auteur.getBooks() ){
            if(book.getAuthors().size()> 1){
                return true;
            }
        }
        return false;
    }

}
