package de.smartasapps.mytoystask.di;


import android.support.annotation.VisibleForTesting;

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

    @VisibleForTesting
    public static void setGraph(MyToysGraph graph) {
        DIProvider.graph = graph;
    }

}
