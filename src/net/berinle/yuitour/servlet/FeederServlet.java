package net.berinle.yuitour.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.berinle.yuitour.Person;
import net.berinle.yuitour.Warehouse;

public class FeederServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("starting service...");
		Warehouse wh = new Warehouse();
		List<Person> persons = wh.getPersons();
		
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
			
			list.add(jsonPerson);
		}
		
		obj.put("records", list);
		
		response.getWriter().write(obj.toJSONString());
		System.out.println("ending service...");
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
