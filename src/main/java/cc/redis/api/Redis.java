package cc.redis.api;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Easy utility to handle and send data between redis
 */
public interface Redis {

    /**
     * {@inheritDoc}
     *
     * @param mapper - map data received by redis sub
     * @param consumer - manipulate data
     * @param channel - listen to the channels
     * @param <T> - Type data parsed will be mapped to
     */
    <T> void subscribe(Function<String, T> mapper, Consumer<T> consumer, String... channel);


    /**
     * {@inheritDoc}
     *
     * @param consumer - manipulate data
     * @param channel  - listen to the channels
     */
    default void subscribe(Consumer<String> consumer,String... channel) {
        subscribe($ -> $, consumer, channel);
    }


    /**
     * {@inheritDoc}
     *
     * @param channel - channel you'd like to send the data to
     * @param message - message or data you'd like to send out
     */
    void publish(String channel, String message);
}
