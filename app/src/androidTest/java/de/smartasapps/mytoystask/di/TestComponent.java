package de.smartasapps.mytoystask.di;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MyToysModule.class, TestModule.class})
public interface TestComponent extends MyToysGraph {
}
