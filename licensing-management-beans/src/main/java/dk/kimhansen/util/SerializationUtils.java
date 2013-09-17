package dk.kimhansen.util;

import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SerializationUtils {

    public static byte[] serialize(final Object toSerialize) {
        Kryo kryo = new Kryo();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (Output output = new Output(outputStream)) {
            kryo.writeObject(output, toSerialize);
        }
        return outputStream.toByteArray();
    }

    public static <T> T deserialize(final byte[] toDeserialize, final Class<T> type) {
        Kryo kryo = new Kryo();
        try (Input input = new Input(toDeserialize)) {
            return kryo.readObject(input, type);
        }
    }

}
