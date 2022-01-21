package cc.redis.api.simple;

import cc.redis.api.Redis;
import cc.redis.api.RedisConnector;
import cc.redis.api.RedisFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleRedisFactory implements RedisFactory {

    @Override public Redis makeRedis(@NotNull ExecutorService executorService, @NotNull JedisPool pool, @Nullable String username, @Nullable String password) {
        return new SimpleRedis(executorService, pool, username, password);
    }

    private static final class SimpleRedis extends JedisPubSub implements Redis {

        private final RedisConnector connector;
        private final Set<Subscription<?>> subscriptions;

        public SimpleRedis(@NotNull ExecutorService executorService, @NotNull JedisPool pool, @Nullable String username, @Nullable String password) {
            this.connector = new SimpleRedisConnector(executorService, pool, username, password);
            this.subscriptions = new CopyOnWriteArraySet<>();
        }

        @Override public void onMessage(String channel, String message) {
            connector.execute(() -> this.subscriptions.stream().filter(subscription -> Arrays.asList(subscription.channels).contains(channel)).findFirst().ifPresent(subscription -> subscription.subscribe(message)));
        }

        @Override public <T> void subscribe(Function<String, T> mapper, Consumer<T> consumer, String... channel) {
            this.connector.execute(jedis -> jedis.subscribe(this, channel));
            this.subscriptions.add(new Subscription<>(channel, mapper, consumer));
        }

        @Override public void publish(String channel, String message) {
            this.connector.execute(jedis -> jedis.publish(channel, message));
        }

        private record Subscription<Type>(String[] channels, Function<String, Type> mapper, Consumer<Type> consumer) {

            void subscribe(@NotNull String str) {
                consumer.accept(mapper.apply(str));
            }
        }
    }
}
