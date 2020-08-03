package edu.pucmm.jdbc.routes;

import edu.pucmm.jdbc.domains.User;
import edu.pucmm.jdbc.models.RouteBase;
import edu.pucmm.jdbc.utils.Constants;
import edu.pucmm.jdbc.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author a.marte
 */
public class LoginRoute extends RouteBase {

    public LoginRoute() {
        super("/check/login");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String email = request.queryParams("email");
        String password = request.queryParams("pass");
        System.out.println(email);
        System.out.println(password);
        spark.Session session = request.session(true);
        User user =new User();
        user.setEmail(email);
        user.setPassword(password);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
         Query query = s.createQuery("from User");
        ArrayList<User> users = (ArrayList<User>) query.list();
        System.out.println("Length->"+users.size());
        User found = new User(); 
        for (User u : users) {
                System.out.println(u.getEmail()+"-----"+u.getLastnames()+"-----------"+u.getId()+"-------------"+u.getBirthDate()+"-------------------"+u.getPassword());
                
            if (u.getEmail().equals(email) && u.getPassword().equals(password)){
                found = u;
                break;
            }
        }
       
        //User found = users.stream().filter( u -> u.getEmail().equals(email) && u.getPassword().equals(password)).findFirst().orElse(null);
        System.out.println("esto es una prueba" + "email=" + email + "password=" + password);
        if (found != null) {
            session.attribute("usuario", user);
            response.cookie("/", Constants.COOKIE, found.getId() + "", 3600, false, true);
            return "1";
        }
   
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        Query query = session.createQuery("from User");
//        ArrayList<User> users = (ArrayList<User>) query.list();
//        User found = users.stream().filter((User u) -> u.getEmail().equals(email) && u.getPassword().equals(password)).findFirst().orElse(null);
//        System.out.println("esto es una prueba" + "email=" + email + "password=" + password);
//        if (found != null) {
//            response.cookie("/", Constants.COOKIE, found.getId() + "", 3600, false, true);
//            return "1";
//        }
        return "0";
    }

}
