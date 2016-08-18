package de.smartasapps.mytoystask.network;

import java.util.List;

public class NavigationEntry {
    public NavigationEntryType type;
    public String url;
    public String label;
    public List<NavigationEntry> children;
}
