package de.smartasapps.mytoystask.di;

import dagger.Component;

@Component(modules = {MyToysModule.class})
public interface MyToysComponent extends MyToysGraph {
}
