package edu.pucmm.jdbc.routes;

import edu.pucmm.jdbc.domains.User;
import edu.pucmm.jdbc.models.RouteBase;
import edu.pucmm.jdbc.utils.HibernateUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;

import java.io.StringWriter;
import java.util.ArrayList;

/**
 * @author a.marte
 */
public class IndexRoute extends RouteBase {

    public IndexRoute() {
        super("/");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        System.out.println("Entro index");
        Template template = velocityEngine.getTemplate("/templates/index.html");
        VelocityContext context = new VelocityContext();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from user");

        ArrayList<User> UserList = (ArrayList<User>) query.list();
        for (User User : UserList) {
            // TODO puedo usar mi cookie y poner el usuario correcto
            System.out.println("id: " + User.getId() + ",email: " + User.getNames() + ", lastnames: " + User.getLastnames() + "birthdate: " + User.getBirthDate());
        }
        context.put("Userlist", UserList);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

}
