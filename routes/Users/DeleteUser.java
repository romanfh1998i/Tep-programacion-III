package edu.pucmm.jdbc.routes.Users;

import edu.pucmm.jdbc.domains.User;
import edu.pucmm.jdbc.models.RouteBase;
import edu.pucmm.jdbc.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;

/**
 * @author a.marte
 */
public class DeleteUser extends RouteBase {

    public DeleteUser() {
        super("/user/delete");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        String idstr = request.queryParams("id");

        System.out.println("borro todo=" + idstr);

        if (idstr != null) {

            try {
                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                //getting session object from session factory
                Session session = sessionFactory.openSession();
                //getting transaction object from session object
                session.beginTransaction();
                int id = Integer.parseInt(idstr);
                User User = (User) session.load(User.class, id);
                session.delete(User);
                System.out.println("Deleted Successfully");
                session.getTransaction().commit();
                //   sessionFactory.close()

                ;
            } catch (Exception e) {

                e.printStackTrace();
            }
            return "1";
        }
        return "0";
    }
}
