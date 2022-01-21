package cc.redis.api.simple;

import cc.redis.api.RedisConnector;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ExecutorService;

public record SimpleRedisConnector(ExecutorService executorService, JedisPool jedisPool, String username, String password) implements RedisConnector {

    @Override public @NotNull Jedis getConnection() {
        try (var jedis = jedisPool.getResource()) {
            if (this.username != null && this.password != null) {
                jedis.auth(this.username, this.password);
            } else if (this.password != null) {
                jedis.auth(this.password);
            }
            return jedis;
        }
    }

    @Override public void execute(Runnable runnable) {
        executorService.submit(runnable);
    }
}
