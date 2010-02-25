package net.berinle.yuitour;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

	private List<Person> persons = new ArrayList<Person>();
	
	public Warehouse(){
		for(int i=0; i<10000; i++){
			persons.add(new Person("person_" + System.currentTimeMillis(),"last_" + i, "333-245-09" + i));
		}
	}
	
	public List<Person> getPersons(){
		return persons;
	}
}
