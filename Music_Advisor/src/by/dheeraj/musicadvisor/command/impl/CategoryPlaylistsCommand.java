package by.dheeraj.musicadvisor.command.impl;

import by.dheeraj.musicadvisor.constant.UserCommand;
import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.domain.playlist.PlayList;
import by.dheeraj.musicadvisor.service.SpotifyService;
import by.dheeraj.musicadvisor.view.View;

import static by.dheeraj.musicadvisor.constant.UserCommand.PLAYLISTS;

@Singleton(qualifier = UserCommand.Qualifier.PLAYLISTS)
public class CategoryPlaylistsCommand extends ApiCommand<PlayList> {

    @Inject
    public CategoryPlaylistsCommand(SpotifyService service, View console) {

        super(service, console, PLAYLISTS, PlayList.class);

    }

}
