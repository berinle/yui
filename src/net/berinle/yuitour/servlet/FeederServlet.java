package net.berinle.yuitour.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.berinle.yuitour.HibernateSessionFactory;
import net.berinle.yuitour.Person;
import net.berinle.yuitour.Warehouse;

public class FeederServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(FeederServlet.class);
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("starting service...");
		Warehouse wh = new Warehouse();
		//List<Person> persons = wh.getPersons();
		
		Session s = HibernateSessionFactory.getSession();
		
		String sortBy = request.getParameter("sort");
		String sortOrder = request.getParameter("dir");
		
		boolean asc = true;
		int startFrom = Integer.parseInt(request.getParameter("startIndex"));
		int pagesize = Integer.parseInt(request.getParameter("results"));
		
		Criteria crit = s.createCriteria(Person.class)
		.setFirstResult(startFrom)
		.setFetchSize(pagesize);
		
		if(!sortOrder.equals("asc"))
			asc = false;
			
		
		if(asc){
			if(sortBy.equals("firstName")){
				crit.addOrder(Order.asc("firstName"));
			} else if(sortBy.equals("lastName")){
				crit.addOrder(Order.asc("lastName"));
			} else if(sortBy.equals("ssn")){
				crit.addOrder(Order.asc("ssn"));
			} else if(sortBy.equals("email")){
				crit.addOrder(Order.asc("email"));
			}
		} else{//descending
			if(sortBy.equals("firstName")){
				crit.addOrder(Order.desc("firstName"));
			} else if(sortBy.equals("lastName")){
				crit.addOrder(Order.desc("lastName"));
			} else if(sortBy.equals("ssn")){
				crit.addOrder(Order.desc("ssn"));
			} else if(sortBy.equals("email")){
				crit.addOrder(Order.desc("email"));
			}
		}
		
		List<Person> persons = crit.setFirstResult(Integer.parseInt(request.getParameter("startIndex")))
		.setFetchSize(Integer.parseInt(request.getParameter("results")))
		.list();
		
		//List<Person> persons = crit.list();
		
		/*s.createQuery("from Person")		
		//.setMaxResults(Integer.parseInt(request.getParameter("results")))
		.setFirstResult(Integer.parseInt(request.getParameter("startIndex")))
		.setFetchSize(Integer.parseInt(request.getParameter("results")))
		.list();*/
	    
		
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){
			String key = en.nextElement().toString();
			String value = request.getParameter(key);
			System.out.println(key + "=" + value);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("recordsReturned", Integer.parseInt(request.getParameter("results")));
		obj.put("totalRecords", persons.size());
		obj.put("startIndex", Integer.parseInt(request.getParameter("startIndex")));
		obj.put("sort", request.getParameter("sort"));
		obj.put("dir", request.getParameter("dir"));
		obj.put("pageSize", 10);
		
		JSONArray list = new JSONArray();
		for(Person p: persons){
			JSONObject jsonPerson = new JSONObject();
			jsonPerson.put("firstName", p.getFirstName());
			jsonPerson.put("lastName", p.getLastName());
			jsonPerson.put("ssn", p.getSsn());
			jsonPerson.put("email", p.getEmail());
			
			list.add(jsonPerson);
		}
		
		obj.put("records", list);
		
		response.getWriter().write(obj.toJSONString());
		log.debug("ending service...");
		
		HibernateSessionFactory.closeSession();
	}
	
	

}
/*
 * {"recordsReturned":25,
    "totalRecords":1397,
    "startIndex":0,
    "sort":null,
    "dir":"asc",
    "pageSize":10,
    "records":[
        {"id":"0",
        "name":"xmlqoyzgmykrphvyiz",
        "date":"13-Sep-2002",
        "price":"8370",
        "number":"8056",
        "address":"qdfbc",
        "company":"taufrid",
        "desc":"pppzhfhcdqcvbirw",
        "age":"5512",
        "title":"zticbcd",
        "phone":"hvdkltabshgakjqmfrvxo",
        "email":"eodnqepua",
        "zip":"eodnqepua",
        "country":"pdibxicpqipbsgnxyjumsza"},
        ...
    ]
}
 */
