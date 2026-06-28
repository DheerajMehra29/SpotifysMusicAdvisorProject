package by.dheeraj.musicadvisor.command.impl;

import by.dheeraj.musicadvisor.constant.UserCommand;
import by.dheeraj.musicadvisor.context.annotation.Inject;
import by.dheeraj.musicadvisor.context.annotation.Singleton;
import by.dheeraj.musicadvisor.domain.category.Category;
import by.dheeraj.musicadvisor.service.SpotifyService;
import by.dheeraj.musicadvisor.view.View;

import static by.dheeraj.musicadvisor.constant.UserCommand.CATEGORIES;

@Singleton(qualifier = UserCommand.Qualifier.CATEGORIES)
public class CategoriesCommand extends ApiCommand<Category> {

    @Inject
    public CategoriesCommand(SpotifyService service, View console) {

        super(service, console, CATEGORIES, Category.class);

    }

}
