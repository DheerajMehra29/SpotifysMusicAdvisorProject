package by.dheeraj.musicadvisor.command.impl;

import by.dheeraj.musicadvisor.constant.UserCommand;
import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.command.Command;
import by.dheeraj.musicadvisor.constant.Status;
import by.dheeraj.musicadvisor.exception.ServiceException;
import by.dheeraj.musicadvisor.context.holder.ContextHolder;
import by.dheeraj.musicadvisor.http.AppHttpServer;
import by.dheeraj.musicadvisor.service.AuthService;
import by.dheeraj.musicadvisor.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static by.dheeraj.musicadvisor.constant.AppConstant.*;
import static by.dheeraj.musicadvisor.constant.Status.BAD_REQUEST;
import static by.dheeraj.musicadvisor.constant.Status.OK;
import static by.dheeraj.musicadvisor.util.Parsers.splitQuery;
import static by.dheeraj.musicadvisor.util.Props.getValue;
import static by.dheeraj.musicadvisor.util.Urls.getUrlToAuthorize;
import static java.util.Objects.nonNull;

@Singleton(qualifier = UserCommand.Qualifier.AUTH)
public class AuthCommand implements Command {

    private static final String KEY_ALREADY_LOGGED_IN = "already_logged_in";

    private static final String KEY_SUCCESS = "success";

    private static final String KEY_USE_LINK = "use_link";

    private static final String KEY_ACCESS_DENIED = "access_denied";

    private static final String KEY_CODE_RECEIVED = "code_received";

    @Inject
    private View console;

    @Inject
    private ContextHolder contextHolder;

    @Inject
    private AppHttpServer server;

    @Inject
    private AuthService authService;

    @Override
    public Status execute() {

        if (nonNull(contextHolder.getAuthHeader())) {

            console.displayln(getValue(KEY_ALREADY_LOGGED_IN));

            return BAD_REQUEST;

        }

        Status status = OK;

        try {

            server.start();

            String url = getUrlToAuthorize();

            console.displayf(getValue(KEY_USE_LINK), url);

            server.stop();

            Map<String, List<String>> splintedQuery = splitQuery(server.getQuery());

            if (splintedQuery.isEmpty() || splintedQuery.containsKey(ERROR)) {

                console.displayln(getValue(KEY_ACCESS_DENIED));

                return BAD_REQUEST;

            }

            String authCode = splintedQuery.get(KEY_CODE).get(0);

            console.displayln(getValue(KEY_CODE_RECEIVED));

            String authHeader = authService.getAuthHeader(authCode);

            contextHolder.setAuthHeader(authHeader);

            console.displayln(getValue(KEY_SUCCESS));

        } catch (IOException | ServiceException e) {

            status = BAD_REQUEST;

            console.displayln(e.getMessage());

        }

        return status;

    }

}
