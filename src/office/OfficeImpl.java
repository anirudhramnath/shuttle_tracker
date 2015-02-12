package office;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import shared.Client;
import shared.Office;
import shared.Shuttle;
import shared.Student;

public class OfficeImpl extends UnicastRemoteObject implements Office{

	public OfficeImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	private List<Student> studentList = new ArrayList<Student>();
	private String duplicate="";
	
	/**
	 * This method accepts two student names are inputs from the client. After performing referential integrity checks,
	 * adds the students to the shuttle queue. 
	 * @param student1 Details of student 1
	 * @param student2 Details of student 2
	 * @return 'kill' returns a string 'kill' which will is an indication that the client program will be killed.
	 */
	@Override
	public String boardShuttle(Student student1, Student student2) throws RemoteException, NotBoundException{
		
		Registry registry = LocateRegistry.getRegistry(1099);
		
	    Client client = (Client) registry.lookup("clientRemote");
	    Shuttle shuttle = (Shuttle) registry.lookup("remoteShuttle");
	    
		studentList.add(student1);

		// Performing referential integrity check here
		if(student1.hashCode() != student2.hashCode()){
			studentList.add(student2);
		}
		else{
			duplicate += "\n Duplicate Detected ";
		}
		// -- //
		
		String studentNames="";
		
		for(Student stu: studentList){
			studentNames += stu.name+",";
		}
		
		studentNames = studentNames.substring(0,studentNames.length()-1);
		
		String returnString = "\nStudent 1 Name: "+student1.name+"\tObject ID: "+student1.hashCode()
				+ "\nStudent 2 Name: "+student2.name+"\tObject ID: "+student2.hashCode()
				+ duplicate
				+"\nStudents waiting are - "+studentNames;
		
		duplicate = "";
		
		// Telling the client about the students who are in queue
		client.responseFromShuttle(returnString);
		
		// Shuttle will be started if there are more than 6 students in queue
		if(studentList.size() >= 6){
		studentList = shuttle.startShuttle(studentList);
		}
		
		return "kill";
	}

	/**
	 * This is a getter method for the ArrayList 'studentList'
	 * 
	 * @return studentList The student list which is being accessed.
	 */
	public List<Student> getStudentList() throws RemoteException{
		return studentList;
	}

	/**
	 * This is a setter method for the ArrayList 'studentList'
	 * 
	 * @param studentList The student list which is being set.
	 */
	public void setStudentList(List<Student> studentList) throws RemoteException{
		this.studentList = studentList;
	}

}
