package by.dheeraj.musicadvisor;

import by.dheeraj.musicadvisor.context.ApplicationContext;
import by.dheeraj.musicadvisor.controller.AppController;

public class Main {

    private static final String PACKAGES_PREFIX = "by.dheeraj.musicadvisor";

    public static void main(String[] args) {

        ApplicationContext context = Application.run(PACKAGES_PREFIX);

        AppController controller = context.getObject(AppController.class);

        controller.start();

    }

}
