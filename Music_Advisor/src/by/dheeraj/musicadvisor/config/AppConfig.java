package by.dheeraj.musicadvisor.config;

import by.dheeraj.musicadvisor.constant.UserCommand;
import by.dheeraj.musicadvisor.domain.category.Category;
import by.dheeraj.musicadvisor.domain.playlist.PlayList;
import by.dheeraj.musicadvisor.tuple.Tuple;
import by.dheeraj.musicadvisor.context.annotation.Configuration;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.domain.Item;
import by.dheeraj.musicadvisor.domain.Response;
import by.dheeraj.musicadvisor.domain.album.Album;
import by.dheeraj.musicadvisor.factory.Factory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Scanner;

import static by.dheeraj.musicadvisor.factory.Factory.of;

@Configuration
public class AppConfig {

    private static final String NEW_RELEASES = "new-releases";

    private static final String ALBUMS = "albums";

    private static final String CATEGORIES = "categories";

    private static final String FEATURED_PLAYLISTS = "featured-playlists";

    private static final String PLAYLISTS = "playlists";

    private static final String CATEGORIES_PLAYLISTS = "categories/%s/playlists";

    @Singleton
    public Scanner scanner() {

        return new Scanner(System.in);

    }

    @Singleton
    public Factory<Class<? extends Item>, Type> typeTokenFactory(Factory<Class<? extends Item>, Type> factory) {

//        Factory<Class<? extends Item>, Type> factory = of();

        factory.add(Album.class, new TypeToken<Response<Album>>() {}.getType());

        factory.add(Category.class, new TypeToken<Response<Category>>() {}.getType());

        factory.add(PlayList.class, new TypeToken<Response<PlayList>>() {}.getType());

        return factory;

    }

    @Singleton
    public Factory<UserCommand, Tuple<String, String>> resourceFactory() {

        Factory<UserCommand, Tuple<String, String>> factory = of();

        factory.add(UserCommand.NEW, new Tuple<>(NEW_RELEASES, ALBUMS));

        factory.add(UserCommand.CATEGORIES, new Tuple<>(CATEGORIES, CATEGORIES));

        factory.add(UserCommand.FEATURED, new Tuple<>(FEATURED_PLAYLISTS, PLAYLISTS));

        factory.add(UserCommand.PLAYLISTS, new Tuple<>(CATEGORIES_PLAYLISTS, PLAYLISTS));

        return factory;

    }

}
