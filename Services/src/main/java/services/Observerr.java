package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observerr extends Remote {
    void start() throws RemoteException, ObiecteException;
    void verifica_rezultate() throws RemoteException, ObiecteException;
    void clasament() throws  RemoteException, ObiecteException;
    void log() throws  RemoteException, ObiecteException;

}
