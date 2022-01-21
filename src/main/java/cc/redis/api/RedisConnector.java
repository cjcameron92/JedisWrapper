package cc.redis.api;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;

import java.util.function.Consumer;

/**
 * Handles redis connection authenticity
 */
public interface RedisConnector {

    /**
     * Returns the Jedis object
     * @return {@link Jedis}
     */
    @NotNull Jedis getConnection();

    /**
     * Execute a runnable on a separate thread
     * @param runnable {@link Runnable}
     */
    void execute(Runnable runnable);

    /**
     * Manipulate {@link Jedis} object on a separate thread
     * @param consumer {@link Consumer<Jedis>}
     */
    default void execute(@NotNull Consumer<Jedis> consumer) {
        execute(() -> consumer.accept(getConnection()));
    }
}
