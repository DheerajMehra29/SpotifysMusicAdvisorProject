package by.dheeraj.musicadvisor.context.configurator;

import by.dheeraj.musicadvisor.context.ApplicationContext;

public interface ObjectConfigurator {

    void configure(Object configurableObject, ApplicationContext context);

}
