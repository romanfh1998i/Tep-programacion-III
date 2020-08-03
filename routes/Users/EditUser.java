package edu.pucmm.jdbc.routes.Users;

import edu.pucmm.jdbc.domains.User;
import edu.pucmm.jdbc.models.RouteBase;
import edu.pucmm.jdbc.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;

import java.text.SimpleDateFormat;

/**
 * @author a.marte
 */
public class EditUser extends RouteBase {

    public EditUser() {
        super("/students/edit");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String idstr = request.queryParams("id");
        String names = request.queryParams("names");
        String lastnames = request.queryParams("lastnames");
        String birdthStr = request.queryParams("birdth");
        String email = request.queryParams("email");
        if (idstr != null && names != null && lastnames != null && birdthStr != null
                && !idstr.isEmpty() && !names.isEmpty() && !lastnames.isEmpty() && !birdthStr.isEmpty()) {
            int id = Integer.parseInt(idstr);
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
            java.util.Date parsed = format.parse(birdthStr);
            java.sql.Date birdth = new java.sql.Date(parsed.getTime());

            try {

                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                //getting session object from session factory
                Session session = sessionFactory.openSession();
                //getting transaction object from session object
                session.beginTransaction();

                User user = (User) session.get(User.class, id);
                user.setNames(names);
                user.setLastnames(lastnames);
                user.setBirthDate(birdth);
                user.setEmail(email);

                System.out.println("Updated Successfully");
                session.getTransaction().commit();
                //sessionFactory.close();
            } catch (Exception e) {

                e.printStackTrace();
            }

            return "1";
        }
        return "0";
    }
}
