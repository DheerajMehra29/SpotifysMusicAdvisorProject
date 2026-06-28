package by.dheeraj.musicadvisor.repository;

import by.dheeraj.musicadvisor.domain.Item;
import by.dheeraj.musicadvisor.domain.Response;
import by.dheeraj.musicadvisor.exception.RepositoryException;

import java.lang.reflect.Type;

public interface ResponseRepository {

    <T extends Item> Response<T> getResponse(String url, String itemsName, Type typeToken) throws RepositoryException;

}
