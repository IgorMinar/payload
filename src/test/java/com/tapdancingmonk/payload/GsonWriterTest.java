package com.tapdancingmonk.payload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


/**
 *
 * @author Igor Minar
 */
public class GsonWriterTest {

    private GsonWriter gsonWriter;


    @Before
    public void setUp() {
        gsonWriter = new GsonWriter();
    }


    @Test
    public void testGetSize() {
        TestClass tc = new TestClass("foo", 123, "baz");
        long size = gsonWriter.getSize(tc, null, null, null, MediaType.APPLICATION_JSON_TYPE);

        assertThat("size should be always -1",
                size, is(-1L));
    }


    @Test
    public void testIsWritable() {

        assertThat("application/json is writable",
            gsonWriter.isWriteable(null, null, null, MediaType.APPLICATION_JSON_TYPE),
            is(true));


        assertThat("application/vnd.foo+json is writable",
            gsonWriter.isWriteable(null, null, null, new MediaType("application", "vnd.foo+json")),
            is(true));


        assertThat("application/xml is not writable",
            gsonWriter.isWriteable(null, null, null, MediaType.APPLICATION_XML_TYPE),
            is(false));
    }


    @Test
    public void testWriteTo() throws IOException {
        TestClass tc = new TestClass("foo", 123, "baz");
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        gsonWriter.writeTo(tc, null, null, null, new MediaType("application", "vnd.foo+json"), null, os);

        assertThat("object was serialized to json correctly",
                os.toString(), is("{\"foo\":\"foo\",\"bar\":123}"));
    }


    static class TestClass {
        private final String foo;
        private final int bar;
        private final transient String baz;

        TestClass(String foo, int bar, String baz) {
            this.foo = foo;
            this.bar = bar;
            this.baz = baz;
        }

        public String getFoo() {
            return foo;
        }
    }
}
