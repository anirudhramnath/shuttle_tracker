package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Office extends Remote {
	
    public String boardShuttle(Student student1, Student student2) throws RemoteException, java.rmi.NotBoundException;
    public List<Student> getStudentList()  throws RemoteException;
    public void setStudentList(List<Student> studentList) throws RemoteException;
}