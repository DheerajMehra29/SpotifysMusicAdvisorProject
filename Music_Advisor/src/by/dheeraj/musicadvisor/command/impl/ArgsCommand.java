package by.dheeraj.musicadvisor.command.impl;

import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.command.Command;
import by.dheeraj.musicadvisor.constant.Status;
import by.dheeraj.musicadvisor.exception.UtilException;
import by.dheeraj.musicadvisor.context.holder.ArgsHolder;
import by.dheeraj.musicadvisor.context.holder.ContextHolder;
import by.dheeraj.musicadvisor.view.View;

import java.util.Objects;

import static by.dheeraj.musicadvisor.constant.AppConstant.KEY_COMMAND_CANNOT_BE_EXECUTED;
import static by.dheeraj.musicadvisor.constant.AppConstant.QUALIFIER_ARGS_COMMAND;
import static by.dheeraj.musicadvisor.constant.Status.BAD_REQUEST;
import static by.dheeraj.musicadvisor.constant.Status.OK;
import static by.dheeraj.musicadvisor.constant.UserCommand.AUTH;
import static by.dheeraj.musicadvisor.constant.UserCommand.EXIT;
import static by.dheeraj.musicadvisor.util.Parsers.collectArgs;
import static by.dheeraj.musicadvisor.util.Props.getValue;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Singleton(qualifier = QUALIFIER_ARGS_COMMAND)
public class ArgsCommand implements Command {

    private static final String KEY_ENTER_COMMAND_ARGS = "enter_command_args";

    private static final String KEY_PROVIDE_ACCESS = "provide_access";

    private static final String KEY_BYE = "bye";

    @Inject
    private View console;

    @Inject
    private ContextHolder contextHolder;

    @Override
    public Status execute() {

        console.display(getValue(KEY_ENTER_COMMAND_ARGS));

        Status status = OK;

        try {

            ArgsHolder argsHolder = collectArgs(console.getArgs());

            if (nonNull(argsHolder.getUserCommand())) {

                contextHolder.setArgsHolder(argsHolder);

            } else if (nonNull(argsHolder.getDirection())) {

                contextHolder.getArgsHolder().setDirection(argsHolder.getDirection());

            }

            if (Objects.equals(argsHolder.getUserCommand(), EXIT)) {

                console.displayln(getValue(KEY_BYE));

                return status;

            }

            argsHolder = contextHolder.getArgsHolder();

            if ((Objects.equals(argsHolder.getUserCommand(), AUTH) || isNull(argsHolder.getUserCommand())) &&
                 nonNull(argsHolder.getDirection())) {

                console.displayln(getValue(KEY_COMMAND_CANNOT_BE_EXECUTED));

                return BAD_REQUEST;

            }

            if (!Objects.equals(argsHolder.getUserCommand(), AUTH) && isNull(contextHolder.getAuthHeader())) {

                console.displayln(getValue(KEY_PROVIDE_ACCESS));

                return BAD_REQUEST;

            }

        } catch (UtilException e) {

            status = BAD_REQUEST;

            console.displayln(e.getMessage());

        }

        return status;
        
    }

}
