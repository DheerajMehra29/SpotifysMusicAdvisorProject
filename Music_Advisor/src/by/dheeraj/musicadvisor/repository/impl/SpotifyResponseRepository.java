package by.dheeraj.musicadvisor.repository.impl;

import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.domain.Item;
import by.dheeraj.musicadvisor.domain.Response;
import by.dheeraj.musicadvisor.domain.error.RegularError;
import by.dheeraj.musicadvisor.exception.ConfigurationException;
import by.dheeraj.musicadvisor.exception.RepositoryException;
import by.dheeraj.musicadvisor.exception.UtilException;
import by.dheeraj.musicadvisor.context.holder.ContextHolder;
import by.dheeraj.musicadvisor.http.AppHttpClient;
import by.dheeraj.musicadvisor.json.JsonHandler;
import by.dheeraj.musicadvisor.json.JsonMapper;
import by.dheeraj.musicadvisor.repository.ResponseRepository;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

import static by.dheeraj.musicadvisor.constant.AppConstant.ERROR;

@Singleton
public class SpotifyResponseRepository implements ResponseRepository {

    @Inject
    private ContextHolder contextHolder;

    @Inject
    private AppHttpClient client;

    @Inject
    private JsonHandler jsonHandler;

    @Inject
    private JsonMapper jsonMapper;

    @Override
    public <T extends Item> Response<T> getResponse(String url, String itemsName, Type typeToken) throws RepositoryException {

        String json;

        try {

            json = client.performGet(url, contextHolder.getAuthHeader());

        } catch (Exception e) {

            throw new ConfigurationException(e);

        }

        validate(json);

        JsonObject jsonObject = jsonHandler.getJsonObject(itemsName);

        return jsonMapper.toResponse(jsonObject, typeToken);

    }

    private void validate(String json) throws RepositoryException {

        try {

            if (jsonHandler.buildDOM(json).hasMember(ERROR)) {

                RegularError error = jsonMapper.toEntity(json, RegularError.class);

                throw new RepositoryException(error.getError().getMessage());

            }
            
        } catch (UtilException e) {

            throw new RepositoryException(e.getMessage());

        }

    }

}
