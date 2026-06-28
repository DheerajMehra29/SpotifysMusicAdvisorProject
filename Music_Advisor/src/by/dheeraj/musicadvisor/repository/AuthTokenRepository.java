package by.dheeraj.musicadvisor.repository;

import by.dheeraj.musicadvisor.domain.auth.Token;
import by.dheeraj.musicadvisor.exception.RepositoryException;

public interface AuthTokenRepository {

    Token getToken(String url, String body) throws RepositoryException;

}
