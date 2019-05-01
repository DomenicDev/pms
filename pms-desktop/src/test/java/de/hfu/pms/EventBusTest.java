package de.hfu.pms;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventBusTest {

    private EventBus eventBus = new EventBus();

    @Test
    public void whenStringHandledBySubscriber_thenSuccess() {
        SampleListener listener = new SampleListener();
        eventBus.register(listener);

        String text = "Hello Guava";
        eventBus.post(text);

        assertEquals(text, listener.getLastText());
    }

    private class SampleListener {

        private String lastText = null;

        @Subscribe
        public void receiveEvent(String event) {
            this.lastText = event;
        }

        String getLastText() {
            return lastText;
        }
    }

}
