package com.wonders.diamond.core.serializer;

import com.wonders.diamond.core.instance.DiamondInstance;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ningyang on 2017/1/2.
 * @author ningyang
 * 序列化diamond实例
 */
public class JsonInstanceSerializer<T> {

    private final ObjectMapper mapper;
    private final Class<T> payloadClass;
    private final JavaType type;

    public JsonInstanceSerializer(Class<T> payloadClass){
        this.mapper = new ObjectMapper();
        this.payloadClass = payloadClass;
        this.type = mapper.getTypeFactory().constructType(this.payloadClass);
    }

    public Class<T> deserialize(byte[] bytes) throws IOException {
        return mapper.readValue(bytes, type);
    }

    public byte[] serialize(DiamondInstance instance) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, instance);
        return out.toByteArray();
    }
}
