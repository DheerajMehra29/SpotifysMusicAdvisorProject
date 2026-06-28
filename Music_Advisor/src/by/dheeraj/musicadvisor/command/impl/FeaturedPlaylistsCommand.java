package by.dheeraj.musicadvisor.command.impl;

import by.dheeraj.musicadvisor.constant.UserCommand;
import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.domain.playlist.PlayList;
import by.dheeraj.musicadvisor.service.SpotifyService;
import by.dheeraj.musicadvisor.view.View;

import static by.dheeraj.musicadvisor.constant.UserCommand.FEATURED;

@Singleton(qualifier = UserCommand.Qualifier.FEATURED)
public class FeaturedPlaylistsCommand extends ApiCommand<PlayList> {

    @Inject
    public FeaturedPlaylistsCommand(SpotifyService service, View console) {

        super(service, console, FEATURED, PlayList.class);

    }

}
