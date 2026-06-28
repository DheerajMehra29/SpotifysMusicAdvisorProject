package by.dheeraj.musicadvisor.service;

import by.dheeraj.musicadvisor.exception.ServiceException;

public interface AuthService {

    String getAuthHeader(String authCode) throws ServiceException;

}
