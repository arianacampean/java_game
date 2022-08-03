package service;

import domeniu.Jucator;
import domeniu.Pozitii;
import domeniu.Punctaje;
import javafx.util.Pair;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.JucatorRepo;
import repository.PozitiiRepo;
import repository.PunctajeRepo;
import repository.interfete.RepoJucator;
import repository.interfete.RepoPozitii;
import repository.interfete.RepoPunctaje;
import services.ObiecteException;
import services.IService;
import services.Observerr;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService {
    private SessionFactory sessionFactory;
    //private Interfete
    private Map<Integer, Observerr> loggedClients;
    private List<Pair<Pair<Integer,Integer>,Integer>> rezultate_runde;
    private List<Integer> lista_logati;
    private List<Integer>trmitere_jucator;
    int nr_de_runde=0;
    List<Jucator>au_apasat;
    String label;
    int runda=1;

    public  void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }
    private RepoJucator repo_juc;
    private RepoPunctaje repo_pct;
    private RepoPozitii repo_poz;


    public Service() {
        initialize();
        this.loggedClients=new ConcurrentHashMap<>();
        this.au_apasat=new ArrayList<>();
        this.rezultate_runde=new ArrayList<>();
        this.lista_logati=new ArrayList<>();
        this.trmitere_jucator=new ArrayList<>();
        this.repo_juc=new JucatorRepo(sessionFactory);
        this.repo_pct=new PunctajeRepo(sessionFactory);
        this.repo_poz=new PozitiiRepo(sessionFactory);

    }



    @Override
    public synchronized void login(Jucator juc, Observerr obs) throws ObiecteException {
        Jucator arb = repo_juc.find_one(juc.getUsername(), juc.getParola());
        if (arb != null) {
            if (loggedClients.get(arb.getId()) != null) {
                throw new ObiecteException("arb deja logat");
            }
            System.out.println("am pus aici");
            loggedClients.put(arb.getId(), obs);
            lista_logati.add(arb.getId());
        } else {
            throw new ObiecteException("Login nereusit");
        }
        if(loggedClients.size()==3)
            notifyJuc();
    }

    @Override
    public synchronized void logout(Jucator juc, Observerr obs) throws ObiecteException {
       loggedClients.remove(juc.getId());
       int ceva=0;
       for(int nr:lista_logati)
       {

           if(nr==juc.getId())
               break;
           else ceva++;
       }
       lista_logati.remove(ceva);
       notifyLog();
    }

    @Override
    //cand apasa pe trimite
    public synchronized int apasa(Jucator juc) throws ObiecteException {
        au_apasat.add(juc);
        if(au_apasat.size()%3==0) {
            punctaje();

        }
        return au_apasat.size();

    }

    @Override
    public synchronized void trimite_info(Integer trim,Integer prim, Integer s) throws ObiecteException {
        Pair<Integer,Integer>p=new Pair<>(trim,prim);
            rezultate_runde.add(new Pair(p,s));
    }

    @Override
    public  synchronized List<Pair<Pair<Integer, Integer>, Integer>> afiseaza_info() throws ObiecteException {
        return rezultate_runde;
    }

    @Override
    public synchronized void punctaje() throws ObiecteException {
        int i = 0;
        List<Pozitii> pct = get_allPoz();
        for (Pair<Pair<Integer,Integer>, Integer> p : rezultate_runde) {
            int contor=0;
            List<Integer> pozitii = new ArrayList<>();
            for (Pozitii pp : pct) {
                if (pp.getId_juc() == p.getKey().getValue()) {
                    pozitii.add(pp.getPozitie());
                }
            }
                for(int po:pozitii) {

                    if (p.getValue() == po) {
                        Punctaje punctej = new Punctaje(p.getKey().getKey(), p.getKey().getValue(), p.getValue(), 7, runda);
                        add_puncte(punctej);
                        contor++;
                        break;
                    } else {
                        if (p.getValue() == po + 1 || p.getValue() == po - 1) {
                            contor++;
                            Punctaje punctej = new Punctaje(p.getKey().getKey(), p.getKey().getValue(), p.getValue(), 3, runda);
                            add_puncte(punctej);
                        }
                    }
                }
                if(contor==0)
                {
                    Punctaje punctej = new Punctaje(p.getKey().getKey(), p.getKey().getValue(), p.getValue(),0, runda);
                    add_puncte(punctej);
                }


            }

        runda++;
    }

    @Override
    public void notificare() throws ObiecteException {
        notifyRez();
    }

    @Override
    public synchronized  void retine_jur(String sir) throws ObiecteException {
        label=sir;
    }

    @Override
    public synchronized  String afiseaza_sir() throws ObiecteException {
        return label;
    }




    //asta pus intr o functie de la observer
    @Override
    public synchronized void runde()  {
        nr_de_runde++;
        if(nr_de_runde==9)
        {
            notifyCla();

        }
    }

    @Override
    public synchronized  List<Jucator> get_allJuc() throws ObiecteException {
        return (List<Jucator>) repo_juc.findAll();
    }

    @Override
    public synchronized  Jucator get_oneJuc(String username, String parola) throws ObiecteException {
        return repo_juc.find_one(username, parola);
    }

    @Override
    public synchronized List<Punctaje> get_allPct() throws ObiecteException {
        return (List<Punctaje>) repo_pct.findAll();
    }

    @Override
    public synchronized  List<Pozitii> get_allPoz() throws ObiecteException {
        return (List<Pozitii>) repo_poz.findAll();
    }

    @Override
    public synchronized  void add_puncte(Punctaje pct) throws ObiecteException {
            repo_pct.add(pct);
    }

    @Override
    public synchronized void add_pozitii(Pozitii poz) throws ObiecteException {
            repo_poz.add(poz);
    }

    @Override
    public synchronized List<Integer> lista_log() throws ObiecteException {
        return lista_logati;
    }

    @Override
    public synchronized void primeste_jucator(int juc) throws ObiecteException {
        trmitere_jucator.add(juc);
    }




    private void notifyJuc()
    {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (var integer : loggedClients.keySet()) {
            Observerr client = loggedClients.get(integer);
            executor.execute(()->{
                try{
                    System.out.println("Notifying ");
                    client.start();
                } catch (ObiecteException | RemoteException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        executor.shutdown();
    }
    private final int defaultThreadsNo=9;
    private void notifyRez()
    {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (var integer : loggedClients.keySet()) {
            Observerr client = loggedClients.get(integer);
            executor.execute(()->{
                try{
                    System.out.println("Notifying ");
                    client.verifica_rezultate();
                } catch (ObiecteException | RemoteException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        executor.shutdown();
    }

    private void notifyCla()
    {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (var integer : loggedClients.keySet()) {
            Observerr client = loggedClients.get(integer);
            executor.execute(()->{
                try{
                    System.out.println("Notifying ");
                    client.clasament();
                } catch (ObiecteException | RemoteException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        executor.shutdown();
    }
    private void notifyLog()
    {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (var integer : loggedClients.keySet()) {
            Observerr client = loggedClients.get(integer);
            executor.execute(()->{
                try{
                    System.out.println("Notifying ");
                    client.log();
                } catch (ObiecteException | RemoteException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        executor.shutdown();
    }
}
