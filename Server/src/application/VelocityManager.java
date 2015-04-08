package application;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityManager {

    private static VelocityManager instance;
    private VelocityEngine velocityEngine;

    // Fuck it. Singletons!
    private VelocityManager() {
        initializeVelocityEngine();
    }

    public static VelocityManager getInstance() {
        if (instance == null) instance = new VelocityManager();
        return instance;
    }

    public Template getTemplate(String pathToTemplate) {
        return velocityEngine.getTemplate(pathToTemplate);
    }

    private void initializeVelocityEngine() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
    }
}
