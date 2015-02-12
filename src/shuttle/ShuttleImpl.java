package shuttle;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ListIterator;

import shared.Client;
import shared.Office;
import shared.Shuttle;
import shared.Student;

public class ShuttleImpl extends UnicastRemoteObject implements Shuttle{

	public ShuttleImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	/**
	 * This method starts the shuttle. It removes the students who have boarded the shuttle and sends back the updated
	 * list to the Office. It also tells the client that shuttle has been started
	 * 
	 * @param studentList The list of students waiting to ride the shuttle
	 * @return returnList The students who have boarded the shuttle.
	 */
	@Override
	public List<Student> startShuttle(List<Student> studentList)
			throws RemoteException {

		List<Student> returnList = studentList;
		
		if(returnList.size() < 6){
			throw new UnsupportedOperationException("studentList is less than 6");
		}
		
		String boarded = "";
		
		try{
			Registry registry = LocateRegistry.getRegistry(1099);
			Office office = (Office) registry.lookup("officeRemote");

			int count = 0;
			
			ListIterator<Student> listIterator = returnList.listIterator();	

			// Removing names of students who have boarded the shuttle.
			while(count < 6){
				boarded += listIterator.next().name+",";

				listIterator.remove();
				
				count++;
			}

			boarded = boarded.substring(0,boarded.length()-1);
			office.setStudentList(returnList);
			
		}
		catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		
		Registry registry = LocateRegistry.getRegistry(1099);
		
		try {
			// Telling the client that the shuttle has started, and giving a list of passengers who are on board.
			Client client = (Client) registry.lookup("clientRemote");
			client.responseFromShuttle("Shuttle has been started. Passengers are - "+boarded); 
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		return returnList;
	}

}
