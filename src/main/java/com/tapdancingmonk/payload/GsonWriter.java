package com.tapdancingmonk.payload;

import com.google.gson.Gson;
import com.sun.jersey.spi.resource.Singleton;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Igor Minar
 */
@Provider
@Singleton
public class GsonWriter implements MessageBodyWriter<Object> {

    private final Gson gson;

    public GsonWriter() {
        gson = new Gson();

    }


    public boolean isWriteable(Class<?> type, Type genericType,
                               Annotation[] annotations, MediaType mediaType) {
        return mediaType.toString().endsWith("json");
    }


    public long getSize(Object t, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    
    public void writeTo(Object t, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {
        OutputStreamWriter osw = new OutputStreamWriter(entityStream);
        gson.toJson(t, osw);
        osw.close();
    }
}
