package by.dheeraj.musicadvisor.domain.album;

import by.dheeraj.musicadvisor.domain.ExternalUrls;
import by.dheeraj.musicadvisor.domain.Item;

import static by.dheeraj.musicadvisor.util.Props.getValue;

public class Artist extends Item {

    private static final String KEY_ARTIST = "artist";

    public Artist(String id, String name, ExternalUrls externalUrls) {

        super(id, name, externalUrls);

    }

    @Override
    public String presentation() {

        return getValue(KEY_ARTIST).formatted(getName(), getExternalUrls().getSpotify());

    }

}
