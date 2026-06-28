package by.dheeraj.musicadvisor.controller;

import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.command.Command;
import by.dheeraj.musicadvisor.constant.Status;
import by.dheeraj.musicadvisor.constant.UserCommand;
import by.dheeraj.musicadvisor.context.ApplicationContext;
import by.dheeraj.musicadvisor.context.holder.ContextHolder;

import java.util.Objects;

import static by.dheeraj.musicadvisor.constant.AppConstant.QUALIFIER_ARGS_COMMAND;
import static by.dheeraj.musicadvisor.constant.AppConstant.QUALIFIER_START_COMMAND;
import static by.dheeraj.musicadvisor.constant.Status.BAD_REQUEST;
import static by.dheeraj.musicadvisor.constant.UserCommand.EXIT;

@Singleton
public class AppController {

    @Inject
    ApplicationContext context;

    @Inject(qualifier = QUALIFIER_START_COMMAND)
    private Command greetingCommand;

    @Inject(qualifier = QUALIFIER_ARGS_COMMAND)
    private Command argsCommand;

    @Inject
    private ContextHolder contextHolder;

    public void start() {

        greetingCommand.execute();

        while (true) {

            Status status = argsCommand.execute();

            if (status.equals(BAD_REQUEST)) {

                continue;

            }

            UserCommand userCommand = contextHolder.getArgsHolder().getUserCommand();

            if (Objects.equals(userCommand, EXIT)) {

                break;

            }

            Command command = context.getObject(userCommand.getName(), Command.class);

            command.execute();

        }

    }

}
