package edu.pucmm.jdbc.routes;

import edu.pucmm.jdbc.models.RouteBase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import org.h2.util.IOUtils;
import spark.Request;
import spark.Response;

/**
 *
 * @author a.marte
 */
public class UploadPictureRoute extends RouteBase {

    public UploadPictureRoute() {
        super("/upload");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/tmp"));
        Part part = request.raw().getPart("picture");

        try (InputStream inputStream = part.getInputStream()) {
            OutputStream out = new FileOutputStream(System.getProperty("user.home") + "/Desktop/" + part.getSubmittedFileName());
            IOUtils.copy(inputStream, out);
            out.flush();
            out.close();
        }
        System.out.println("mostrar foto" + part);
        return "1";
    }
}
