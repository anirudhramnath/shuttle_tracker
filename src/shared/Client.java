package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void responseFromShuttle(String response) throws RemoteException;
}