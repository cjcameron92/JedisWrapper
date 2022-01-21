# Jedis Wrapper

## What is Jedis?
Jedis is a Redis client for java, more information can be found [here](https://github.com/redis/jedis).

## Getting Started
To get started with this Jedis Wrapper add it as a dependency.

Maven
```xml
<dependacny>
    <groupId>cc.redis.api</groupId>
    <artifactId>wrapper</artifactId>
    <version>2.0</version>
</dependacny>
```

Gradle
```groovy
api("cc.redis.api:wrapper:2.0")
```


### Creating your application
```java
JedisPool jedisPool = new JedisPool("localhost", 6379);
```
Optionals
```java
ExecutorService executorService = Executors.newCachedThreadPool();
String username = "root";
String password = "admin";
```

```java
RedisFactory redisFactory = new SimpleRedisFactory();
Redis redis = redisFactory.makeRedis(executorService, jedisPool, username, password);
```

or 
```java
RedisFactory redisFactory = new SimpleRedisFactory();
Redis redis = redisFactory.makeRedis(jedisPool);
```
Publishing
```java
redis.publish("EXAMPLE_CHANNEL", "SOME_DATA");
```
Subscribing
```java
redis.subscribe(str -> {     
    System.out.println(str);
    }, "EXAMPLE_CHANNEL", "ANOTHER_CHANNEL");
```
