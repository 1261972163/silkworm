package com.jengine.transport.serialize.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nouuid on 2015/5/7.
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setDeserializationConfig(mapper.getDeserializationConfig().without(
                org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES));
        mapper.setSerializationConfig(mapper.getSerializationConfig().without(
                org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS));
    }

    private static ThreadLocal<StringWriter> sws = new ThreadLocal<StringWriter>() {
        protected StringWriter initialValue() {
            return new StringWriter(1024);
        }
    };
    private static ThreadLocal<JsonGenerator> gens = new ThreadLocal<JsonGenerator>() {
        protected JsonGenerator initialValue() {
            try {
                return new JsonFactory().createJsonGenerator(sws.get());
            } catch (IOException e) {
                logger.error("", e);
            }
            return null;
        }
    };

    public static <T> T formJson(Class<T> clazz, String message) {
        try {
            T bean = mapper.readValue(message, clazz);
            return bean;
        } catch (Exception e) {
            logger.error("", e);
        }

        return null;

    }

    public static <T> T formJson(TypeReference clazz, String message) {
        try {
            T bean = mapper.readValue(message, clazz);
            return bean;
        } catch (Exception e) {
            logger.error("", e);
        }

        return null;

    }

    public static <T> T formJson(JavaType clazz, String message) {
        try {
            T bean = mapper.readValue(message, clazz);
            return bean;
        } catch (Exception e) {
            logger.error("", e);
        }

        return null;

    }

    public static <T> T formByteJson(Class<T> clazz, byte[] message) {
        try {
            T bean = mapper.readValue(message, clazz);
            return bean;
        } catch (Exception e) {
            logger.error("", e);
        }

        return null;

    }

    public static String toJson(Object target) {
        JsonGenerator gen = gens.get();
        StringWriter sw = sws.get();
        int l = 0;
        try {
//            StringWriter sw = new StringWriter();
//            gen = new JsonFactory().createJsonGenerator(sw);
            mapper.writeValue(gen, target);

            String json = sw.toString();
            l = json.length();

            return json;
        } catch (Exception e) {
            logger.warn("", e);
            l = 8200;
        } finally {
            sw.reset(l);
            if (l >= 8192) {
                try {
                    if (gen != null) {
                        try {
                            gen.close();
                        } catch (IOException e) {
                            logger.error("", e);
                        }
                    }
                    gens.set(new JsonFactory().createJsonGenerator(sws.get()));
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
//        	if(gen != null) {
//        		try {
//					gen.close();
//				} catch (IOException e) {
//					logger.error("", e);
//				}
//        	}
        }
        return null;
    }

    public static byte[] toByteJson(Object target) {
        JsonGenerator gen = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            mapper.writeValue(out, target);

            return out.toByteArray();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (gen != null) {
                try {
                    gen.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
            try {
                out.close();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        return null;
    }

    public static <T> T formStream(Class<T> clazz, InputStream in) throws IOException {

        byte[] data = new byte[4];
        int c = in.read(data, 0, 4);
        int length = 0;
        if (c != -1) {
            ByteBuffer b = ByteBuffer.allocate(4).put(data);
            b.flip();
            length = b.getInt();
        } else {
            throw new IOException("getInt error");
        }
        byte[] flagByte = new byte[2];
        c = in.read(flagByte, 0, 2);
        char flag = 0;
        if (c != -1) {
            ByteBuffer b = ByteBuffer.allocate(2).put(flagByte);
            b.flip();
            flag = b.getChar();
        } else {
            throw new IOException("getChar error");
        }
        byte[] jb = new byte[length];
        in.read(jb, 0, length);
        if ('Y' == flag) {
            return formJson(clazz, new String(QuickLZ.decompress(jb)));
        } else {
            return formJson(clazz, new String(jb));
        }

    }

    public static void toStream(Object target, OutputStream os) throws IOException {
        String json = toJson(target);
        byte[] data = json.getBytes();
        if (data.length > 1024) {
            byte[] compData = QuickLZ.compress(data, 1);
            os.write(ByteBuffer.allocate(4).putInt(compData.length).array());
            os.write(ByteBuffer.allocate(2).putChar('Y').array());
            os.write(compData);
        } else {
            os.write(ByteBuffer.allocate(4).putInt(data.length).array());
            os.write(ByteBuffer.allocate(2).putChar('N').array());
            os.write(data);
        }

    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}

