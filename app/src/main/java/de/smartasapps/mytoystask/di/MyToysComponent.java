package de.smartasapps.mytoystask.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MyToysModule.class})
public interface MyToysComponent extends MyToysGraph {
}
