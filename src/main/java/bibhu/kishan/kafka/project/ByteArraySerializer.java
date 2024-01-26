package bibhu.kishan.kafka.project;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ByteArraySerializer implements Serializer<byte[]> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration needed
    }

    @Override
    public byte[] serialize(String topic, byte[] data) {
        return data;
    }

    @Override
    public void close() {
        // No resources to be released
    }
}
