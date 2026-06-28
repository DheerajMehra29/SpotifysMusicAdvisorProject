package by.dheeraj.musicadvisor.command.impl;

import by.dheeraj.musicadvisor.command.Command;
import by.dheeraj.musicadvisor.constant.Status;
import by.dheeraj.musicadvisor.constant.UserCommand;
import by.dheeraj.musicadvisor.domain.Item;
import by.dheeraj.musicadvisor.dto.ItemDto;
import by.dheeraj.musicadvisor.exception.ServiceException;
import by.dheeraj.musicadvisor.service.SpotifyService;
import by.dheeraj.musicadvisor.view.View;

import static by.dheeraj.musicadvisor.constant.Status.BAD_REQUEST;
import static by.dheeraj.musicadvisor.constant.Status.OK;

public abstract class ApiCommand<T extends Item> implements Command {

    private final SpotifyService service;

    private final View console;

    private final UserCommand userCommand;

    private final Class<T> entityClass;

    public ApiCommand(SpotifyService service, View console,
                      UserCommand userCommand, Class<T> entityClass) {

        this.service = service;

        this.console = console;

        this.userCommand = userCommand;

        this.entityClass = entityClass;

    }
    
    @Override
    public Status execute() {

        Status status = OK;

        try {

            ItemDto itemDto = service.getItem(userCommand, entityClass);

            console.displayList(itemDto.items(), itemDto.currentPage(), itemDto.totalPages());

        } catch (ServiceException e) {

            status = BAD_REQUEST;

            console.displayln(e.getMessage());

        }

        return status;

    }

}