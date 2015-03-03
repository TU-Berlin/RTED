package it.unibz.inf.rted.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class LblTreeTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }
    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
    @Test
    public void testFromString() throws Exception {
        final String expected = "*---+ '=' \n" +
                "    +---+ 'E' \n" +
                "    +---+ '*' \n" +
                "        +---+ 'm' \n" +
                "        +---+ '^' \n" +
                "            +---+ 'c' \n" +
                "            +---+ '2' \n";
        LblTree lblTest = LblTree.fromString( "{={E}{*{m}{^{c}{2}}}}" );
        lblTest.prettyPrint();
        assertEquals(expected, outContent.toString());
    }
}