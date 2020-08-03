package edu.pucmm.jdbc.routes;

import edu.pucmm.jdbc.models.RouteBase;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import spark.Request;
import spark.Response;

import java.io.StringWriter;

/**
 * @author a.marte
 */
public class SnakeRoute extends RouteBase {

    public SnakeRoute() {
        super("/game");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Template template = velocityEngine.getTemplate("/templates/snake.html");
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

}
