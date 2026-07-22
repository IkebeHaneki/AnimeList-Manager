package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestEvent {

    @Test
    void testEventCapturesDescriptionAndTime() {
        Event event = new Event("Anime added");

        assertEquals("Anime added", event.getDescription());
        assertNotNull(event.getDate());
        assertTrue(event.toString().contains("Anime added"));
    }
}
