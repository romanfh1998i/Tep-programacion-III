package edu.pucmm.jdbc.routes.Users;

import edu.pucmm.jdbc.domains.User;
import edu.pucmm.jdbc.models.RouteBase;
import edu.pucmm.jdbc.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author a.marte
 */
public class Registrar extends RouteBase {

    public Registrar() {
        super("/registrar");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String email = request.queryParams("email");
        String names = request.queryParams("names");
        String lastnames = request.queryParams("lastnames");
        String birthStr = request.queryParams("birdth");
        String pass =request.queryParams("pass");
//        String nameFile = request.queryParams("nameFile");
//        String typeFile = request.queryParams("typeFile");

        System.out.println("ensenar los valores que esta en java id=" + email + " names = " + names + " " + lastnames + " " + birthStr);

        if (names != null && lastnames != null && birthStr != null
                && !names.isEmpty() && !lastnames.isEmpty() && !birthStr.isEmpty()) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
                Date parsed = format.parse(birthStr);
                User user = new User();
                user.setEmail(email);
                user.setPassword(pass);
                user.setNames(names);
                user.setLastnames(lastnames);
                user.setBirthDate(new java.sql.Date(parsed.getTime()));
                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
                System.out.println("Inserted Successfully");
            } catch (Exception ignored) {
                return "0";
            }
            return "1";
        }
        return "0";
    }
}
