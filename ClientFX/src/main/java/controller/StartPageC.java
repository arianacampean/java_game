package controller;

import domeniu.Jucator;
import domeniu.Pozitii;
import domeniu.Punctaje;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import services.ObiecteException;
import services.IService;
import services.Observerr;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartPageC  extends UnicastRemoteObject implements Observerr {

    IService serv;
    ObservableList<String> model= FXCollections.observableArrayList();
    Jucator jucator;
    @FXML
    TextField nume;
    @FXML
    TextField poz1;
    @FXML
    TextField poz2;
    @FXML
    TextField poz3;
    @FXML
    TextField poz_noua;
    @FXML
    Label pozitii_label;
    @FXML
    Label ultima;
    @FXML
    ListView<String>listView;
    @FXML
    Button but;

    

    public void setService(IService serv) {
        this.serv = serv;
    }
    public StartPageC() throws RemoteException {

    }

    public void setJuc(Jucator arbb) {
        this.jucator=arbb;
        nume();
    }
    @FXML
    public void initialize() {
        listView.setItems(model);
    }
    public void nume()
    {
        nume.setText(jucator.getUsername());
    }


    public void logout(ActionEvent actionEvent) throws ObiecteException {
        serv.logout(jucator,this);
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
    public void incepe() throws ObiecteException {
        int po1= Integer.parseInt(poz1.getText());
        int po2= Integer.parseInt(poz2.getText());
        int po3= Integer.parseInt(poz3.getText());
        serv.add_pozitii(new Pozitii(jucator.getId(),po1));
        serv.add_pozitii(new Pozitii(jucator.getId(),po2));
        serv.add_pozitii(new Pozitii(jucator.getId(),po3));


    }
    public void lista_logati() throws ObiecteException {
        List<Integer> list=serv.lista_log();
        List<Jucator>juc=serv.get_allJuc();
        List<String>all=new ArrayList<>();
        String s;

        for(Jucator j:juc)
        {
            for(int nr:list)
            {
                if(j.getId()==nr && j.getId()!=jucator.getId())
                {
                    s=j.getUsername();
                    all.add(s);


                }

            }

        }
        model.setAll(all);
    }
    public void start_but() throws ObiecteException {
        incepe();
        lista_logati();
        List<Integer> list=serv.lista_log();
        List<Jucator>juc=serv.get_allJuc();
        String ss="";
        for(Jucator j:juc)
        {
            for(int nr:list)
            {
                if(j.getId()==nr)
                {
                    ss=ss+j.getUsername()+":";

                }

            }
            ss=ss+"_________" +"\n";

        }
        pozitii_label.setText(ss);

    }

    public void sir_string() throws ObiecteException {
        List<Punctaje>pct= serv.get_allPct();
        List<Jucator>juc= serv.get_allJuc();
        String s="";
            for(Jucator j:juc)
            {
                s=s+j.getUsername()+":";
                Map<Integer,String> poz=new HashMap<>();
                for(Punctaje p:pct)
                {
                    if(j.getId()==p.getId_jucator_primeste()) {
                        if (p.getPunctaj() == 7) {
                            poz.put(p.getPozitie(), " O ");
                        }
                        if (p.getPunctaj() == 3) {
                            poz.put(p.getPozitie(), " N ");
                        }
                        if (p.getPunctaj() == 0) {
                            poz.put(p.getPozitie(), " C ");
                        }

                    }
                }
                int i=1;
                while(i<=9) {
                    int contor = 0;
                    for (Map.Entry<Integer, String> m : poz.entrySet()) {
                        if (m.getKey() == i)
                            contor++;

                    }

                    if (contor == 0)
                        poz.put(i, " _ ");
                    i++;
                }

                for (Map.Entry<Integer, String> mm : poz.entrySet())
                {
                    s=s+mm.getValue();

            }
                s=s+"\n";
        }
            serv.retine_jur(s);



    }
    public void trimite_poz() throws ObiecteException {
        String user=listView.getSelectionModel().getSelectedItem();
        List<Jucator>juc=serv.get_allJuc();
        int id=0;
        for(Jucator j:juc)
        {
            if(j.getUsername().equals(user))
                id=j.getId();

        }
        int poz= Integer.parseInt(poz_noua.getText());
        serv.trimite_info(jucator.getId(),id,poz);
        int nr=serv.apasa(jucator);
        if(nr%3==0) {
            sir_string();
            serv.notificare();
        }

    }
    public void runda_noua()
    {
        poz_noua.clear();
    }


    @Override
    public void start() throws RemoteException, ObiecteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                but.setDisable(false);
            }
        });
    }


    @Override
    public void verifica_rezultate() throws RemoteException, ObiecteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String sir=serv.afiseaza_sir();
                    pozitii_label.setText(sir);
                    serv.runde();
                } catch (ObiecteException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void clasament() throws RemoteException, ObiecteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
               ultima.setText("sfarsit joc");
            }
        });
    }

    @Override
    public void log() throws RemoteException, ObiecteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // but.setDisable(false);
            }
        });
    }

}
