package de.smartasapps.mytoystask.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class NavigationEntryConverter implements JsonDeserializer<NavigationEntryType> {
    @Override
    public NavigationEntryType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String jsonString = json.getAsString();
        switch (jsonString) {
            case "section":
                return NavigationEntryType.SECTION;
            case "node":
                return NavigationEntryType.NODE;
            default:
                return NavigationEntryType.LINK;
        }
    }
}
