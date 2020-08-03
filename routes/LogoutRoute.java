package edu.pucmm.jdbc.routes;

import edu.pucmm.jdbc.domains.User;
import edu.pucmm.jdbc.models.RouteBase;
import edu.pucmm.jdbc.utils.Constants;
import spark.Request;
import spark.Response;

/**
 * @author a.marte
 */
public class LogoutRoute extends RouteBase {

    public LogoutRoute() {
        super("/logout");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        spark.Session session = request.session(true);
        User user =session.attribute("user");
        if (user!=null){
            request.session().removeAttribute("user");
        }
        response.removeCookie(Constants.COOKIE);
        return "1";
    }

}
