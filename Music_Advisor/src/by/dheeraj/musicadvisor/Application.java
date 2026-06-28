package by.dheeraj.musicadvisor;

import by.dheeraj.musicadvisor.context.ApplicationContext;
import by.dheeraj.musicadvisor.context.ObjectFactory;
import by.dheeraj.musicadvisor.context.config.Config;
import by.dheeraj.musicadvisor.context.config.impl.DefaultConfig;

public class Application {

    public static ApplicationContext run(String packagesPrefix) {

        ApplicationContext initialContext = new ApplicationContext();

        Config config = new DefaultConfig(packagesPrefix);

        initialContext.setConfig(config);

        ObjectFactory objectFactory = new ObjectFactory(initialContext);

        initialContext.setObjectFactory(objectFactory);

        initialContext.createSingletons();

        ApplicationContext applicationContext = initialContext.getObject(ApplicationContext.class);

        initialContext.copyTo(applicationContext);

        return applicationContext;

    }

}
