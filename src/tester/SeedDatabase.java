package tester;

import org.hibernate.Session;

import net.berinle.yuitour.HibernateSessionFactory;
import net.berinle.yuitour.Person;

public class SeedDatabase {
	public static void main(String[] args) {
		Session s = HibernateSessionFactory.getSession();
		s.beginTransaction();
		
		for(int i=1; i<= 30000; i++){
			Person p = new Person("person"+System.currentTimeMillis(), "last"+System.currentTimeMillis(), ""+System.currentTimeMillis());
			s.save(p);
			if(i%20 == 0){
				s.flush();
				s.clear();
			}
		}
		s.getTransaction().commit();
		s.close();
	}
}
