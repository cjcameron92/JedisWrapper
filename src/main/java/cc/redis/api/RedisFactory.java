package cc.redis.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simple factory to create {@link Redis} objects
 */
public interface RedisFactory {

    /**
     * Returns Redis object
     *
     * @param executorService - ExecutorService
     * @param pool - JedisPool
     * @param username - Redis username if needed
     * @param password - Redis password if needed
     * @return {@link Redis}
     */
    Redis makeRedis(@NotNull ExecutorService executorService, @NotNull JedisPool pool, @Nullable String username, @Nullable String password);

    /**
     * Returns Redis object
     *
     * @param pool - JedisPool
     * @param password - Redis password
     * @return {@link Redis}
     */
    default Redis makeRedis(@NotNull JedisPool pool, @Nullable String password) {
        return makeRedis(Executors.newCachedThreadPool(), pool, null, password);
    }

    /**
     * Returns Redis object
     *
     * @param pool - JedisPool
     * @return {@link Redis}
     */
    default Redis makeRedis(@NotNull JedisPool pool) {
        return makeRedis(pool, null);
    }
}
