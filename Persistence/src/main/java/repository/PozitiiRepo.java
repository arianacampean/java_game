package repository;

import domeniu.Jucator;
import domeniu.Pozitii;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.interfete.RepoPozitii;

import java.util.ArrayList;
import java.util.List;

public class PozitiiRepo implements RepoPozitii {
    private SessionFactory sessionFactory;

    public PozitiiRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Iterable<Pozitii> findAll() {
        List<Pozitii> lis=new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                lis =session.createQuery("FROM Pozitii ", Pozitii.class).getResultList();

                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
            return lis;
        }
    }

    @Override
    public void add(Pozitii entity) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(Pozitii entity) {

    }

    @Override
    public void delete(Pozitii entity) {

    }
}
