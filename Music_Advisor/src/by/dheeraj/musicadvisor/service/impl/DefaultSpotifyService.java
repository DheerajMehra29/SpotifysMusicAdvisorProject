package by.dheeraj.musicadvisor.service.impl;

import by.dheeraj.musicadvisor.constant.Direction;
import by.dheeraj.musicadvisor.constant.UserCommand;
import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.context.holder.ContextHolder;
import by.dheeraj.musicadvisor.domain.Item;
import by.dheeraj.musicadvisor.domain.Response;
import by.dheeraj.musicadvisor.dto.ItemDto;
import by.dheeraj.musicadvisor.exception.RepositoryException;
import by.dheeraj.musicadvisor.exception.ServiceException;
import by.dheeraj.musicadvisor.factory.Factory;
import by.dheeraj.musicadvisor.repository.ResponseRepository;
import by.dheeraj.musicadvisor.service.SpotifyService;
import by.dheeraj.musicadvisor.tuple.Tuple;

import java.lang.reflect.Type;
import java.util.Objects;

import static by.dheeraj.musicadvisor.constant.AppConstant.KEY_COMMAND_CANNOT_BE_EXECUTED;
import static by.dheeraj.musicadvisor.constant.Direction.NEXT;
import static by.dheeraj.musicadvisor.constant.Direction.PREV;
import static by.dheeraj.musicadvisor.constant.UserCommand.PLAYLISTS;
import static by.dheeraj.musicadvisor.util.Props.getValue;
import static by.dheeraj.musicadvisor.util.Urls.getUrlTo;
import static java.lang.Math.floorMod;
import static java.util.Objects.nonNull;

@Singleton
public class DefaultSpotifyService implements SpotifyService {

    private static final String QUALIFIER_TYPE_TOKEN_FACTORY = "typeTokenFactory";

    private static final String QUALIFIER_RESOURCE_FACTORY = "resourceFactory";

    @Inject
    private ResponseRepository repository;

    @Inject
    private ContextHolder contextHolder;

    @Inject(qualifier = QUALIFIER_TYPE_TOKEN_FACTORY)
    private Factory<Class<? extends Item>, Type> typeTokenFactory;

    @Inject(qualifier = QUALIFIER_RESOURCE_FACTORY)
    private Factory<UserCommand, Tuple<String, String>> resourceFactory;

    @Override
    public <T extends Item> ItemDto getItem(UserCommand command, Class<T> entityClass) throws ServiceException {

        Tuple<String, String> resources = resourceFactory.get(command);

        Type typeToken = typeTokenFactory.get(entityClass);

        String url = getActualUrl(resources.first());

        try {

            Response<T> response = repository.getResponse(url, resources.second(), typeToken);

            contextHolder.setResponse(response);

            return toItemDto(response);

        } catch (RepositoryException e) {

            throw new ServiceException(e.getMessage());

        }

    }

    private String getActualUrl(String resource) throws ServiceException {

        Direction direction = contextHolder.getArgsHolder().getDirection();

        if (nonNull(direction)) {

            Response<? extends Item> response = contextHolder.getResponse();

            if (Objects.equals(direction, NEXT) ) {

                if (nonNull(response) && nonNull(response.getNext())) {

                    return response.getNext();

                } else {

                    throw new ServiceException(getValue(KEY_COMMAND_CANNOT_BE_EXECUTED));

                }

            }

            if (Objects.equals(direction, PREV)) {

                if (nonNull(response) && nonNull(response.getPrevious())) {

                    return response.getPrevious();

                } else {

                    throw new ServiceException(getValue(KEY_COMMAND_CANNOT_BE_EXECUTED));

                }

            }

        }

        UserCommand command = contextHolder.getArgsHolder().getUserCommand();

        int limit = contextHolder.getArgsHolder().getLimit();

        String categoryName = contextHolder.getArgsHolder().getCategoryId();

        return Objects.equals(command, PLAYLISTS) ? getUrlTo(resource.formatted(categoryName), limit)
                                                  : getUrlTo(resource, limit);

    }

    private <T extends Item> ItemDto toItemDto(Response<T> response) {

        int limit = contextHolder.getResponse().getLimit();

        int offset = contextHolder.getResponse().getOffset();

        int total = contextHolder.getResponse().getTotal();

        int totalPages;

        if (total < limit || total == limit) {

            totalPages = 1;

        } else {

            int mod = floorMod(total, limit);

            totalPages = mod == 0 ? total / limit : total / limit + 1;

        }

        int currentPage = offset / limit + 1;

        return new ItemDto(response.getItems(), totalPages, currentPage);

    }

}
