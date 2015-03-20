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
    public void setUpStreams() throws UnsupportedEncodingException {
	    PrintStream p = new PrintStream( outContent, true, "UTF-8" );
        System.setOut( p );
    }
    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
    @Test
    public void testFromString() throws Exception {
        LblTree lblTest = LblTree.fromString( "{equal{energy}{times{mass}{power{speedoflight}{two}}}}" );
        lblTest.prettyPrint();
        assertEquals( expectedPseudoMath, outContent.toString().replaceAll( "\r", "" ) );

    }
    @Test
    public void testFromXML() throws Exception {
        Node rootElement = Resource2Node( "pseudoMath.xml" );
        LblTree lblTest = LblTree.fromXML( rootElement );
        lblTest.prettyPrint();
        assertEquals( expectedPseudoMath, outContent.toString().replaceAll( "\r", "" ) );
    }

	@Test
	public void testFromMathML() throws Exception {
		final String MathMLTree = "*---+ 'math' \n" +
			"    +---+ 'semantics' \n" +
			"        +---+ 'mrow' \n" +
			"            +---+ 'mi' \n" +
			"                +---+ 'E' \n" +
			"            +---+ 'mo' \n" +
			"                +---+ '=' \n" +
			"            +---+ 'mrow' \n" +
			"                +---+ 'mi' \n" +
			"                    +---+ 'm' \n" +
			"                +---+ 'mo' \n" +
			"                    +---+ '\u2062' \n" +//InvisibleTimes
			"                +---+ 'msup' \n" +
			"                    +---+ 'mi' \n" +
			"                        +---+ 'c' \n" +
			"                    +---+ 'mn' \n" +
			"                        +---+ '2' \n" +
			"        +---+ 'annotation-xml' \n" +
			"            +---+ 'apply' \n" +
			"                +---+ 'eq' \n" +
			"                +---+ 'ci' \n" +
			"                    +---+ 'E' \n" +
			"                +---+ 'apply' \n" +
			"                    +---+ 'times' \n" +
			"                    +---+ 'ci' \n" +
			"                        +---+ 'm' \n" +
			"                    +---+ 'apply' \n" +
			"                        +---+ 'csymbol' \n" +
			"                            +---+ 'superscript' \n" +
			"                        +---+ 'ci' \n" +
			"                            +---+ 'c' \n" +
			"                        +---+ 'cn' \n" +
			"                            +---+ '2' \n" +
			"        +---+ 'annotation' \n" +
			"            +---+ 'E=mc^{2}' \n";
		Node rootElement = Resource2Node("Emc2.mml.xml");
		LblTree lblTest = LblTree.fromXML( rootElement );
		lblTest.prettyPrint();
		assertEquals( MathMLTree, outContent.toString("UTF-8").replaceAll( "\r", "" ) );
	}

	public Node Resource2Node (String ResourceName) throws ParserConfigurationException, IOException, URISyntaxException {
		Document doc = Resource2Doc( ResourceName );
		return doc.getDocumentElement();
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