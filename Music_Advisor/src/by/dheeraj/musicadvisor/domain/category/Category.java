package by.dheeraj.musicadvisor.domain.category;

import by.dheeraj.musicadvisor.domain.ExternalUrls;
import by.dheeraj.musicadvisor.domain.Item;

import static by.dheeraj.musicadvisor.util.Props.getValue;

public class Category extends Item {

    public static final String KEY_CATEGORY = "category";

    public Category(String id, String name, ExternalUrls externalUrls) {

        super(id, name, externalUrls);

    }

    @Override
    public String presentation() {

        return getValue(KEY_CATEGORY).formatted(getName(), getId());
        
    }

}
