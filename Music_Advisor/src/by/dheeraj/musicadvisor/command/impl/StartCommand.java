package by.dheeraj.musicadvisor.command.impl;


import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.command.Command;
import by.dheeraj.musicadvisor.constant.Status;
import by.dheeraj.musicadvisor.view.View;

import static by.dheeraj.musicadvisor.constant.AppConstant.QUALIFIER_START_COMMAND;
import static by.dheeraj.musicadvisor.constant.Status.OK;
import static by.dheeraj.musicadvisor.util.Props.getValue;

@Singleton(qualifier = QUALIFIER_START_COMMAND)
public class StartCommand implements Command {

    private static final String KEY_APP_HEADER = "app_header";

    @Inject
    private View console;

    @Override
    public Status execute() {

        console.displayln(getValue(KEY_APP_HEADER));

        return OK;

    }

}
