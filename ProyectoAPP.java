package edu.pucmm.jdbc;

import com.google.gson.Gson;
import edu.pucmm.jdbc.domains.Game;
import edu.pucmm.jdbc.domains.User;
import edu.pucmm.jdbc.games.SnakeWebSocket;
import edu.pucmm.jdbc.models.RouteBase;
import edu.pucmm.jdbc.routes.IndexRoute;
import edu.pucmm.jdbc.routes.LoginRoute;
import edu.pucmm.jdbc.routes.LogoutRoute;
import edu.pucmm.jdbc.routes.SnakeRoute;
import edu.pucmm.jdbc.routes.UploadPictureRoute;
import edu.pucmm.jdbc.routes.Users.DeleteUser;
import edu.pucmm.jdbc.routes.Users.EditUser;
import edu.pucmm.jdbc.routes.Users.Registrar;
import edu.pucmm.jdbc.utils.HibernateUtil;
import edu.pucmm.jdbc.utils.Language;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.staticFiles;

/**
 * @author a.marte
 */
public class ProyectoAPP {

    public static void main(String[] args) {
        staticFiles.location("/templates");
        Spark.port(8080);
        Spark.webSocket("/ws/snake", SnakeWebSocket.class);
        addGetRoute(new IndexRoute());
        addGetRoute(new Registrar());
        addGetRoute(new EditUser());
        addGetRoute(new DeleteUser());
        addGetRoute(new LoginRoute());
        addGetRoute(new LogoutRoute());
        addGetRoute(new SnakeRoute());
        UploadPictureRoute uploadPictureRoute = new UploadPictureRoute();
        Spark.post(uploadPictureRoute.path, "multipart/form-data", uploadPictureRoute);
        System.out.println(Language.getInstance().getText("esto.es.una.prueba"));
        Language.getInstance().setDEFAULT(Language.SPANISH);
        System.out.println(Language.getInstance().getText("esto.es.una.prueba"));
        get("/hello", (Request req, Response res) -> {
            //1ero Obetener de la base de datos un estudiante
            //2do Guardar  ese estudiante en una variable
            //3ero retornar estudiante para que se vea en el explorador como un JSON
            List<User> LISTuser = new ArrayList<>();

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from user");
            return new Gson().toJson(LISTuser);
        });

    }

    private static void addGetRoute(RouteBase routeBase) {
        Spark.get(routeBase.path, routeBase);
    }
}
