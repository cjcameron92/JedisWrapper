package cc.redis.api;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * Simple class to map data
 */
public interface RedisMapper {

    Function<String, Integer> INTEGER_MAPPER = Integer::parseInt;
    Function<String, Double> DOUBLE_MAPPER = Double::parseDouble;
    Function<String, Long> LONG_MAPPER = Long::parseLong;;
    Function<String, Float> FLOAT_MAPPER = Float::parseFloat;

    Function<byte[], String> BYTE_MAPPER = String::new;
    Function<String, byte[]> STRING_MAPPER = s -> s.getBytes(StandardCharsets.UTF_8);
    Function<byte[], Integer> BYTE_TO_INTEGER = bytes -> INTEGER_MAPPER.apply(BYTE_MAPPER.apply(bytes));
    Function<byte[], Double> BYTE_TO_DOUBLE = bytes -> DOUBLE_MAPPER.apply(BYTE_MAPPER.apply(bytes));
    Function<byte[], Long> BYTE_TO_LONG = bytes -> LONG_MAPPER.apply(BYTE_MAPPER.apply(bytes));
    Function<byte[], Float> BYTE_TO_FLOAT = bytes -> FLOAT_MAPPER.apply(BYTE_MAPPER.apply(bytes));
}
