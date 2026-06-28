package by.dheeraj.musicadvisor.service;

import by.dheeraj.musicadvisor.constant.UserCommand;
import by.dheeraj.musicadvisor.domain.Item;
import by.dheeraj.musicadvisor.dto.ItemDto;
import by.dheeraj.musicadvisor.exception.ServiceException;

public interface SpotifyService {

    <T extends Item> ItemDto getItem(UserCommand command, Class<T> entityClass) throws ServiceException;

}
