package edu.pucmm.jdbc.models;

import java.util.Properties;
import org.apache.velocity.app.VelocityEngine;
import spark.Route;

/**
 * @author a.marte
 */
public abstract class RouteBase implements Route {

    public VelocityEngine velocityEngine = new VelocityEngine();
    public String path;

    protected RouteBase(String path) {
        this.path = path;
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "file");
        properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init(properties);
    }
}
