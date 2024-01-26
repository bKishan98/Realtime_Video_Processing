package bibhu.kishan.kafka.project;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ByteArrayDeserializer implements Deserializer<byte[]> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration needed
    }

    @Override
    public byte[] deserialize(String topic, byte[] data) {
        return data;
    }

    @Override
    public void close() {
        // No resources to be released
    }
}
