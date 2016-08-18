package de.smartasapps.mytoystask.di;


public class DIProvider {
    private static MyToysGraph graph;

    private DIProvider() {
    }

    public static MyToysGraph getInstance() {
        if (graph == null) {
            graph = DaggerMyToysComponent.create();
        }
        return graph;
    }
}
