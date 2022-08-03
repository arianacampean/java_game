package services;

import domeniu.Jucator;
import domeniu.Pozitii;
import domeniu.Punctaje;
import javafx.util.Pair;

import java.util.List;

public interface IService {
    void login(Jucator juc, Observerr obs) throws ObiecteException;
    void logout(Jucator juc, Observerr obs) throws ObiecteException;
    int apasa(Jucator juc) throws ObiecteException;
   void trimite_info(Integer trm,Integer prim,Integer s)throws ObiecteException;
    List<Pair<Pair<Integer, Integer>, Integer>> afiseaza_info()throws ObiecteException;
    void runde()throws ObiecteException;
    void retine_jur(String sir)throws ObiecteException;
    String afiseaza_sir()throws ObiecteException;
    List<Jucator> get_allJuc()throws ObiecteException;
    Jucator get_oneJuc(String username,String parola)throws ObiecteException;
    List<Punctaje>get_allPct()throws ObiecteException;
    List<Pozitii>get_allPoz()throws ObiecteException;
    void add_puncte(Punctaje pct)throws ObiecteException;
    void add_pozitii(Pozitii poz)throws ObiecteException;
    List<Integer>lista_log()throws ObiecteException;
    void primeste_jucator(int juc)throws ObiecteException;
    void punctaje()throws ObiecteException;
    void notificare()throws ObiecteException;




}
