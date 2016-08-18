package de.smartasapps.mytoystask.network;

import com.google.gson.annotations.SerializedName;

public enum NavigationEntryType {
    @SerializedName("section")
    SECTION,
    @SerializedName("label")
    LABEL,
    @SerializedName("node")
    NODE;
}
