package it.unibz.inf.rted.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class LblTreeTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final String expectedPseudoMath = "*---+ 'equal' \n" +
            "    +---+ 'energy' \n" +
            "    +---+ 'times' \n" +
            "        +---+ 'mass' \n" +
            "        +---+ 'power' \n" +
            "            +---+ 'speedoflight' \n" +
            "            +---+ 'two' \n";
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
        LblTree lblTest = LblTree.fromString( "{equal{energy}{times{mass}{power{speedoflight}{two}}}}" );
        lblTest.prettyPrint();
        assertEquals(expectedPseudoMath, outContent.toString());

    }
    @Test
    public void testFromXML() throws Exception {
        Document doc = Resource2Doc("pseudoMath.xml");
        Node rootElement = doc.getDocumentElement();
        LblTree lblTest = LblTree.fromXML( rootElement );
        lblTest.prettyPrint();
        assertEquals(expectedPseudoMath, outContent.toString());
    }


    /**
     * Helper program: Transforms a String to a XML Document.
     *
     * @param ResourceName     the name of the xml resource
     * @return parsed document
     * @throws javax.xml.parsers.ParserConfigurationException the parser configuration exception
     * @throws IOException                  Signals that an I/O exception has occurred.
     */
    public Document Resource2Doc(String ResourceName)
            throws ParserConfigurationException, IOException, URISyntaxException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        try {
            return builder.parse( this.getClass().getClassLoader().getResourceAsStream( ResourceName ) );
        } catch (SAXException e) {
            System.out.println("cannot parse following resource\\n\\n" + ResourceName);
            e.printStackTrace();
            return null;
        }

    }

}