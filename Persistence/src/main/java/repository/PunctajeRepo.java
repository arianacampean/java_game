package repository;

import domeniu.Jucator;
import domeniu.Punctaje;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.interfete.RepoPunctaje;

import java.util.ArrayList;
import java.util.List;

public class PunctajeRepo implements RepoPunctaje {
    private SessionFactory sessionFactory;

    public PunctajeRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Iterable<Punctaje> findAll() {
        List<Punctaje> lis=new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                lis =session.createQuery("FROM Punctaje ", Punctaje.class).getResultList();


                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
            return lis;
        }
    }

    @Override
    public void add(Punctaje entity) {
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
    public void update(Punctaje entity) {

    }

    @Override
    public void delete(Punctaje entity) {

    }
}
