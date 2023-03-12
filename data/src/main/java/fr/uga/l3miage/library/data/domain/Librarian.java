package fr.uga.l3miage.library.data.domain;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("L")
public class Librarian extends Person {

    @ManyToOne
    private Librarian manager;

    @OneToMany(mappedBy = "manager")
    private Set<Librarian> librarians;

    //@OneToOne(mappedBy = "librarian", cascade = CascadeType.ALL)
    //private Borrow borrow;

    public Librarian getManager() {
        return manager;
    }

    public void setManager(Librarian manager) {
        this.manager = manager;
    }

    public Set<Librarian> getLibrarians(){
        return librarians;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Librarian librarian)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(manager, librarian.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), manager);
    }
}
