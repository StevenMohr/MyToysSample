package de.smartasapps.mytoystask.network;

import com.google.gson.JsonPrimitive;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NavigationEntryConverterTest {
    private NavigationEntryConverter converterUnderTest;

    @Before
    public void setUp() throws Exception {
        converterUnderTest = new NavigationEntryConverter();
    }

    @Test
    public void deserialize_Section() throws Exception {
        assertThat(converterUnderTest.deserialize(new JsonPrimitive("section"), null, null)).isEqualTo(NavigationEntryType.SECTION);
    }

    @Test
    public void deserialize_Node() throws Exception {
        assertThat(converterUnderTest.deserialize(new JsonPrimitive("node"), null, null)).isEqualTo(NavigationEntryType.NODE);
    }

    @Test
    public void deserialize_Link() throws Exception {
        assertThat(converterUnderTest.deserialize(new JsonPrimitive("link"), null, null)).isEqualTo(NavigationEntryType.LINK);
    }

    @Test
    public void deserialize_Other() throws Exception {
        assertThat(converterUnderTest.deserialize(new JsonPrimitive("external-link"), null, null)).isEqualTo(NavigationEntryType.LINK);
    }

}