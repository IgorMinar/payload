package com.tapdancingmonk.payload;

import java.io.ByteArrayInputStream;
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
public class GsonReaderTest {

    private GsonReader gsonReader;


    @Before
    public void setUp() {
        gsonReader = new GsonReader();
    }


    @Test
    public void testIsReadable() {

        assertThat("application/json is readable",
            gsonReader.isReadable(null, null, null, MediaType.APPLICATION_JSON_TYPE),
            is(true));


        assertThat("application/vnd.foo+json is readable",
            gsonReader.isReadable(null, null, null, new MediaType("application", "vnd.foo+json")),
            is(true));


        assertThat("application/xml is not readable",
            gsonReader.isReadable(null, null, null, MediaType.APPLICATION_XML_TYPE),
            is(false));
    }


    @Test
    public void testReadFrom() throws IOException {
        ByteArrayInputStream is = new ByteArrayInputStream("{\"foo\":\"foo\",\"bar\":123}".getBytes());
        TestClass tc = (TestClass) gsonReader.readFrom(TestClass.class, null, null, null, null, is);

        assertThat("the json message was deserialized correctly",
                tc, is(new TestClass("foo", 123, "baz")));
    }


    
    static class TestClass {
        private final String foo;
        private final int bar;
        private final transient String baz;

        /** Gson constructor */
        private TestClass() {
            this(null, 0, null);
        }

        TestClass(String foo, int bar, String baz) {
            this.foo = foo;
            this.bar = bar;
            this.baz = baz;
        }

        public String getFoo() {
            return foo;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TestClass other = (TestClass) obj;
            if ((this.foo == null) ? (other.foo != null) : !this.foo.equals(other.foo)) {
                return false;
            }
            if (this.bar != other.bar) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + (this.foo != null ? this.foo.hashCode() : 0);
            hash = 23 * hash + this.bar;
            return hash;
        }
    }
}
