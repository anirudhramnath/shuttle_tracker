package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import shared.Client;
import shared.Office;
import shared.Student;

public class ClientImpl extends UnicastRemoteObject implements Client{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientImpl() throws RemoteException {
		super();
	}

	/**
	 * This method is used by ShuttleImpl to intimate the client that the shuttle has started
	 * @param response This is the response message from the server
	 * @return null This method does not return anything.
	 */
	@Override
	public void responseFromShuttle(String response) throws RemoteException {
		System.out.println(response);
	}
	
	/**
	 * This is the method which performs the client functions. It accepts Student names are input from the user and passes 
	 * them to the OfficeServer.
	 * 
	 * This method is not declared in the Client interface.
	 * 
	 * @return null This method does not return anything.
	 */
	public void doClientFunction() throws RemoteException{
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			Office office = (Office) registry.lookup("officeRemote");

			Student student1 = null;
			Student student2 = null;
			Scanner scan = new Scanner(System.in);
			String check = null;
			String name;
			String address = "123 XYZ, Chicago";

			System.out.println("Enter name of student 1 - ");
			name = scan.next();
			address = "";

			student1 = new Student(name, address);

			while (check == null || (!check.equalsIgnoreCase("Y") && !check.equalsIgnoreCase("N"))) {
				System.out.println("Do you want to create a copy of the first student? (Enter Y/N) [For referential integrity demo]");
				check = scan.next();
			}

			if (check.equalsIgnoreCase("Y")) {
				student2 = student1;
				System.out.println("\nCopy created!");
			} else {
				System.out.println("Enter name of student 2 - ");
				name = scan.next();
				address = "";
				student2 = new Student(name, address);
			}

			office.boardShuttle(student1,student2);
			
			System.out.println("\nInput any key and press Return to kill the program");
			scan.next();
			System.exit(1);
			scan.close();
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
