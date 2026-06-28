package by.dheeraj.musicadvisor.command.impl;

import by.dheeraj.musicadvisor.constant.UserCommand;
import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.domain.album.Album;
import by.dheeraj.musicadvisor.service.SpotifyService;
import by.dheeraj.musicadvisor.view.View;

import static by.dheeraj.musicadvisor.constant.UserCommand.NEW;

@Singleton(qualifier = UserCommand.Qualifier.NEW)
public class NewCommand extends ApiCommand<Album> {

    @Inject
    public NewCommand(SpotifyService service, View console) {

        super(service, console, NEW, Album.class);

    }

}
