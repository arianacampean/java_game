package repository;

import domeniu.Jucator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.interfete.RepoJucator;
import repository.interfete.Repository;

import java.util.ArrayList;
import java.util.List;

public class JucatorRepo implements RepoJucator {
    private SessionFactory sessionFactory;
    public JucatorRepo(SessionFactory sessionFactory)
    {
        this.sessionFactory=sessionFactory;
    }

    @Override
    public Jucator find_one(String username, String parola) {
        Jucator arb=null;
    try(Session session = sessionFactory.openSession()) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            arb =session.createQuery("FROM Jucator E WHERE E.username= :username and E.parola=:parola", Jucator.class)
                    .setParameter("username",username).setParameter("parola",parola).getSingleResult();

            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null)
                tx.rollback();
        }
        return arb;
    }
    }

    @Override
    public Iterable<Jucator> findAll() {

        List<Jucator> lis=new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                lis =session.createQuery("FROM Jucator ", Jucator.class).getResultList();


                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
            return lis;
        }
    }

    @Override
    public void add(Jucator entity) {

    }

    @Override
    public void update(Jucator entity) {

    }

    @Override
    public void delete(Jucator entity) {

    }
//    public Iterable<Jucatori> findAll() {
//        List<Jucatori> lis=new ArrayList<>();
//        try(Session session = sessionFactory.openSession()) {
//            Transaction tx = null;
//            try {
//                tx = session.beginTransaction();
//                lis =session.createQuery("FROM Jucatori ", Jucatori.class).getResultList();
//
//
//                tx.commit();
//            } catch (RuntimeException ex) {
//                if (tx != null)
//                    tx.rollback();
//            }
//            return lis;
//        }
//    }





//@Override
//public Jucatori findJuc(String username, String parola) {
//    Jucatori arb=null;
//    try(Session session = sessionFactory.openSession()) {
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            arb =session.createQuery("FROM Jucatori E WHERE E.username= :username and E.parola=:parola", Jucatori.class)
//                    .setParameter("username",username).setParameter("parola",parola).getSingleResult();
//
//            tx.commit();
//        } catch (RuntimeException ex) {
//            if (tx != null)
//                tx.rollback();
//        }
//        return arb;
//    }
//
//}





//    @Override
//    public void add(Punctaje entity) {
//        try(Session session = sessionFactory.openSession()) {
//            Transaction tx = null;
//            try {
//                tx = session.beginTransaction();
//                session.save(entity);
//                tx.commit();
//            } catch (RuntimeException ex) {
//                if (tx != null)
//                    tx.rollback();
//            }
//        }
//    }



//    @Override
//    public void delete(Punctaje entity) {
//        try(Session session = sessionFactory.openSession()) {
//            Transaction tx = null;
//            try {
//                tx = session.beginTransaction();
//                Punctaje employee = (Punctaje) session.get(Punctaje.class, entity.getId());
//                session.delete(employee);
//                tx.commit();
//            } catch (RuntimeException ex) {
//                if (tx != null)
//                    tx.rollback();
//            }
//        }
//    }





//    @Override
//    public void update(Medicament entity) {
//        try(Session session = sessionFactory.openSession()) {
//            Transaction tx = null;
//            try {
//                tx = session.beginTransaction();
//                Medicament com = (Medicament) session.get(Medicament.class, entity.getId());
//                com.setCantitate_pe_stoc( entity.getCantitate_pe_stoc() );
//                session.update(com);
//                tx.commit();
//            } catch (RuntimeException ex) {
//                if (tx != null)
//                    tx.rollback();
//            }
//        }
//    }


}
