package it.unibz.inf.rted.convenience;

import it.unibz.inf.rted.util.LblTreeTest;
import org.junit.Test;
import org.w3c.dom.Node;

import static junit.framework.TestCase.assertEquals;

public class RTEDTest {

	private final String Emc2Text = "{equal{energy}{times{mass}{power{speedoflight}{two}}}}";
	private final String Ec2mText = "{equal{energy}{times{power{speedoflight}{two}}{mass}}}";
	private final String c2mEText = "{equal{times{power{speedoflight}{two}}{mass}}{energy}}";

	@Test
	public void testComputeDistance () throws Exception {
		final double permutation1 = 2;
		final double permutation2 = 4;
		assertEquals( permutation1, RTED.computeDistance( Emc2Text, Ec2mText ) );
		assertEquals( permutation2, RTED.computeDistance( Emc2Text, c2mEText ) );
		assertEquals( permutation1, RTED.computeDistance( c2mEText, Ec2mText ) );
	}

	@Test
	public void MathMLTests () throws Exception {
		final double permutation1 = 13;
		final double permutation2 = 21;
		Node Emc2Node = (new LblTreeTest()).Resource2Node( "Emc2.mml.xml" );
		Node Ec2mNode = (new LblTreeTest()).Resource2Node( "Ec2m.mml.xml" );
		Node c2mENode = (new LblTreeTest()).Resource2Node( "c2mE.mml.xml" );
		assertEquals( permutation1, RTED.computeDistance( Emc2Node, Ec2mNode ) );
		assertEquals( permutation2, RTED.computeDistance( Emc2Node, c2mENode ) );
		assertEquals( permutation1, RTED.computeDistance( c2mENode, Ec2mNode ) );
		assertEquals( permutation1, RTED.computeDistance( c2mENode, Ec2mNode ) );
	}
}