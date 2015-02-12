package junit;

import static org.junit.Assert.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import office.OfficeImpl;

import org.junit.Before;
import org.junit.Test;

import client.ClientImpl;
import shared.Office;
import shared.Shuttle;
import shared.Student;
import shuttle.ShuttleImpl;

public class JunitTests {

	@Before
	public void startServers() throws RemoteException, AlreadyBoundException, NotBoundException{
		Registry registry = LocateRegistry.getRegistry(1099);
		
		String[] boundNames = registry.list();
		
		for(String name : boundNames){
        	if(name.equals("officeRemote")){
        		registry.unbind("officeRemote");
        	}
        	else if(name.equals("clientRemote")){
        		registry.unbind("clientRemote");
        	}
        	else if(name.equals("remoteShuttle")){
        		registry.unbind("remoteShuttle");
        	}
        }
        
        registry.bind("officeRemote", new OfficeImpl());
        registry.bind("clientRemote", new ClientImpl());
        registry.bind("remoteShuttle", new ShuttleImpl());
	}
	
	@Test
	public void testForSeperateObjects() throws RemoteException, NotBoundException, AlreadyBoundException {
		
		OfficeImpl officeImpl = new OfficeImpl();
		Registry registry = LocateRegistry.getRegistry(1099);
		
        String[] boundNames = registry.list();
        
       // Checking if the name officeRemote is already bound, if it is we are unbinding it
        
        for(String name : boundNames){
        	if(name.equals("officeRemote")){
        		registry.unbind("officeRemote");
        		break;
        	}
        }
        
        registry.bind("officeRemote", officeImpl);
		
		Office office = (Office) registry.lookup("officeRemote");
		
		Student student1 = new Student("Test 1","Address 1");
		Student student2 = new Student("Test 2","Address 2");
		
		office.boardShuttle(student1, student2);
		
		int student1Hash = 0;
		int student2Hash = 0;
		
		for(Student s: office.getStudentList()){
			if(s.name.equals(student1.name)){
				student1Hash = s.hashCode();
			}
			if(s.name.equals(student2.name)){
				student2Hash = s.hashCode();
			}
		}
		
		boolean flag = false;
		
		if(student1Hash != student2Hash){
			flag = true;
		}
		
		assertEquals(flag, true);
	}
	
	@Test
	public void testForSameObjects() throws RemoteException, NotBoundException, AlreadyBoundException {
		
		OfficeImpl officeImpl = new OfficeImpl();
		Registry registry = LocateRegistry.getRegistry(1099);
		
        String[] boundNames = registry.list();
        
       // Checking if the name officeRemote is already bound, if it is we are unbinding it
        
        for(String name : boundNames){
        	if(name.equals("officeRemote")){
        		registry.unbind("officeRemote");
        		break;
        	}
        }
        
        registry.bind("officeRemote", officeImpl);
		
		Office office = (Office) registry.lookup("officeRemote");
		
		Student student1 = new Student("Test 1","Address 1");
		Student student2 = student1;
		
		office.boardShuttle(student1, student2);
		
		int student1Hash = 0;
		int student2Hash = 0;
		
		for(Student s: office.getStudentList()){
			if(s.name.equals(student1.name)){
				student1Hash = s.hashCode();
			}
			if(s.name.equals(student2.name)){
				student2Hash = s.hashCode();
			}
		}
		
		boolean flag = false;
		
		if(student1Hash == student2Hash){
			flag = true;
		}
		
		assertEquals(flag, true);
	}
	
	@Test
	public void testForIllegalShuttleStart() throws RemoteException, NotBoundException, AlreadyBoundException {
		Student student1 = new Student("Test 1","Address 1");
		Student student2 = new Student("Test 2","Address 2");
		Student student3 = new Student("Test 3","Address 3");
		Student student4 = new Student("Test 4","Address 4");
		Student student5 = new Student("Test 5","Address 5");
		
		List<Student> studentList = new ArrayList<Student>();
		
		studentList.add(student1);
		studentList.add(student2);
		studentList.add(student3);
		studentList.add(student4);
		studentList.add(student5);
		
		Registry registry = LocateRegistry.getRegistry(1099);
		Shuttle shuttle = (Shuttle)registry.lookup("remoteShuttle");
		
		boolean failedFlag = false;
		
		try{
			shuttle.startShuttle(studentList);
		}
		catch(UnsupportedOperationException e){
			failedFlag = true;
		}
		
		assertEquals(failedFlag,true);
	}
	
	@Test
	public void testForLegalShuttleStart() throws RemoteException, NotBoundException, AlreadyBoundException {
		Student student1 = new Student("Test 1","Address 1");
		Student student2 = new Student("Test 2","Address 2");
		Student student3 = new Student("Test 3","Address 3");
		Student student4 = new Student("Test 4","Address 4");
		Student student5 = new Student("Test 5","Address 5");
		Student student6 = new Student("Test 6","Address 6");
		
		List<Student> studentList = new ArrayList<Student>();
		
		studentList.add(student1);
		studentList.add(student2);
		studentList.add(student3);
		studentList.add(student4);
		studentList.add(student5);
		studentList.add(student6);
		
		Registry registry = LocateRegistry.getRegistry(1099);
		Shuttle shuttle = (Shuttle)registry.lookup("remoteShuttle");
		
		boolean failedFlag = false;
		int studentListSize = -1;

		try{
			studentList = shuttle.startShuttle(studentList);
			studentListSize = studentList.size();
		}
		catch(UnsupportedOperationException e){
			failedFlag = true;
		}
		
		if(studentList.size() != 0){
			failedFlag = true;
		}
		
		assertEquals(failedFlag,false);
	}
	
	@Test
	public void testForMoreThanSixStudentsInQueue() throws RemoteException, NotBoundException, AlreadyBoundException {
		Student student1 = new Student("Test 1","Address 1");
		Student student2 = new Student("Test 2","Address 2");
		Student student3 = new Student("Test 3","Address 3");
		Student student4 = new Student("Test 4","Address 4");
		Student student5 = new Student("Test 5","Address 5");
		Student student6 = new Student("Test 6","Address 6");
		Student student7 = new Student("Test 7","Address 7");
		
		List<Student> studentList = new ArrayList<Student>();
		
		studentList.add(student1);
		studentList.add(student2);
		studentList.add(student3);
		studentList.add(student4);
		studentList.add(student5);
		studentList.add(student6);
		studentList.add(student7);
		
		Registry registry = LocateRegistry.getRegistry(1099);
		Shuttle shuttle = (Shuttle)registry.lookup("remoteShuttle");
		
		String remainingStudent = null;

		try{
			studentList = shuttle.startShuttle(studentList);
			remainingStudent = studentList.get(0).name;
		}
		catch(UnsupportedOperationException e){
			remainingStudent = "-1";
		}
		
		assertEquals(remainingStudent,student7.name);
	}

}
