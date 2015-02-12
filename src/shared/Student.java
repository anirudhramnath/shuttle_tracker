package shared;

import java.io.Serializable;

public class Student implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public String address;
	
	public Student(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}
	
}
