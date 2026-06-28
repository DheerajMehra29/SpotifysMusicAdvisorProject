package by.dheeraj.musicadvisor.repository.impl;

import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.domain.error.AuthError;
import by.dheeraj.musicadvisor.domain.auth.Token;
import by.dheeraj.musicadvisor.exception.ConfigurationException;
import by.dheeraj.musicadvisor.exception.RepositoryException;
import by.dheeraj.musicadvisor.exception.UtilException;
import by.dheeraj.musicadvisor.http.AppHttpClient;
import by.dheeraj.musicadvisor.json.JsonHandler;
import by.dheeraj.musicadvisor.json.JsonMapper;
import by.dheeraj.musicadvisor.repository.AuthTokenRepository;

import static by.dheeraj.musicadvisor.constant.AppConstant.ERROR;

@Singleton
public class SpotifyAuthTokenRepository implements AuthTokenRepository {

    @Inject
    private AppHttpClient client;

    @Inject
    private JsonHandler jsonHandler;

    @Inject
    private JsonMapper jsonMapper;

    @Override
    public Token getToken(String url, String body) throws RepositoryException {

        String json;

        try {

            json = client.performPost(url, body);

        } catch (Exception e) {

            throw new ConfigurationException(e);

        }

        validate(json);

        return jsonMapper.toEntity(json, Token.class);

    }

    private void validate(String json) throws RepositoryException {

        try {

            if (jsonHandler.buildDOM(json).hasMember(ERROR)) {

                AuthError authError = jsonMapper.toEntity(json, AuthError.class);

                throw new RepositoryException(authError.getErrorDescription());

            }

        } catch (UtilException e) {

            throw new RepositoryException(e.getMessage());

        }

    }

}
