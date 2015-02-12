package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Shuttle extends Remote {
    List<Student> startShuttle(List<Student> studentList) throws RemoteException;
}