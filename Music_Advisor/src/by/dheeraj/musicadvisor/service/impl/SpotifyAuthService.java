package by.dheeraj.musicadvisor.service.impl;

import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.domain.auth.Token;
import by.dheeraj.musicadvisor.exception.RepositoryException;
import by.dheeraj.musicadvisor.exception.ServiceException;
import by.dheeraj.musicadvisor.repository.AuthTokenRepository;
import by.dheeraj.musicadvisor.service.AuthService;

import static by.dheeraj.musicadvisor.constant.Delimiter.SPACE;
import static by.dheeraj.musicadvisor.util.Urls.getBodyToAccessToken;
import static by.dheeraj.musicadvisor.util.Urls.getUrlToToken;

@Singleton
public class SpotifyAuthService implements AuthService {

    @Inject
    private AuthTokenRepository repository;

    @Override
    public String getAuthHeader(String authCode) throws ServiceException {

        String url = getUrlToToken();

        String body = getBodyToAccessToken(authCode);

        try {

            Token token = repository.getToken(url, body);

            return token.getTokenType() + SPACE.getSign() + token.getAccessToken();

        } catch (RepositoryException e) {

            throw new ServiceException(e.getMessage());

        }

    }

}
