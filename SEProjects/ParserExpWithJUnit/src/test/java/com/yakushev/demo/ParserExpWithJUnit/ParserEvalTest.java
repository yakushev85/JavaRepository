package com.yakushev.demo.ParserExpWithJUnit;

import com.evalparser.ParserEval;
import com.evalparser.ParserEvalIndex;
import com.evalparser.exceptions.BadNumberEvaluateException;
import com.evalparser.exceptions.ParserEvaluateException;
import com.evalparser.functions.BasicFunctions;

import junit.framework.TestCase;

/**
 * Unit test for ParserEval.
 */
public class ParserEvalTest extends TestCase {
	private final static double DELTA = 0.000001;
	
	private final static String TXT_TESTEQUATION1_W_PARAMS = "   (  2*1.1*sqrt(next_test-some_test*2)+(sin(1))^2+"
			+ "(cos(1))^2-atan(0^5)+tan(0.0)+asin(0)+acos(1)+ln(1.0)+exp(0)	)  /  4.2";
	private final static String TXT_PARAM1 = "some_test";
	private final static double VALUE_PARAM1 = 22.1;
	private final static String TXT_PARAM2 = "next_test";
	private final static double VALUE_PARAM2 = 45.9;
	
	private final static String TXT_TESTEQUATION2__WOUT_PARAMS = "sin(456.7	/cos(3.45))*tan(   89.9)+asin(acos(0.56)*"
			+ "asin(0.9)/1000)*atan(9.9)-(ln(234))^      (exp(1.1))      ";
	
	private final static String TXT_TESTEQUATION3_DIVBYZERO = "(1/x)*cos(x*pi)+x+1";
	private final static String TXT_PARAM3 = "x";
	private final static double VALUE_PARAM3 = 0;
	
	private final static String TXT_TESTEQUATION4_INNER ="sin(cos(tan(sqrt(0)+1)+2*pi)+3*pi)";
	private final static String TXT_PARAMPI = "pi";
	
	private final static String TXT_TESTEQUATION4_BIGDOUBLE = "sqrt(761782.78217482*2.76237)+sin(1.3627186472)";
	
	private final static String TXT_TESTEQUATION5_WRONG = "sin())((";
	
	private final ParserEval parserEval;
	
    public ParserEvalTest( String testName ) {
        super(testName);
        parserEval = new ParserEvalIndex();
        parserEval.addFunctions(BasicFunctions.getInstance());
    }

    public void testEquationWithParams() throws ParserEvaluateException, BadNumberEvaluateException {
    	parserEval.setEquation(TXT_TESTEQUATION1_W_PARAMS);
    	parserEval.addVariable(TXT_PARAM1, VALUE_PARAM1);
    	parserEval.addVariable(TXT_PARAM2, VALUE_PARAM2);
    	double expectedValue = (2*1.1*Math.sqrt(VALUE_PARAM2-VALUE_PARAM1*2)+Math.pow((Math.sin(1)),2)+Math.pow((Math.cos(1)),2)
				-Math.atan(Math.pow(0,5))+Math.tan(0.0)+Math.asin(0)+Math.acos(1)+Math.log(1.0)+Math.exp(0))/4.2;
        assertEquals(expectedValue, parserEval.evalEquation(), DELTA);
    }
    
    public void testEquatoinWithOutParams() throws ParserEvaluateException, BadNumberEvaluateException {
    	parserEval.setEquation(TXT_TESTEQUATION2__WOUT_PARAMS);
    	double expectedValue = Math.sin(456.7/Math.cos(3.45))*Math.tan(89.9)+Math.asin(Math.acos(0.56)*Math.asin(0.9)/1000)
				*Math.atan(9.9)-Math.pow(Math.log(234),Math.exp(1.1));
    	assertEquals(expectedValue, parserEval.evalEquation(), DELTA);
    }
    
    public void testDivByZero() throws ParserEvaluateException {
    	parserEval.setEquation(TXT_TESTEQUATION3_DIVBYZERO);
    	parserEval.addVariable(TXT_PARAM3, VALUE_PARAM3);
    	
    	try {
    		parserEval.evalEquation();
    	} catch (BadNumberEvaluateException e) {
    		assertTrue(true);
    		return;
    	}

		fail();
    }
    
    public void testInnerAndPiVariable() throws ParserEvaluateException, BadNumberEvaluateException {
    	parserEval.setEquation(TXT_TESTEQUATION4_INNER);
    	parserEval.addVariable(TXT_PARAMPI, Math.PI);
    	double expectedvalue = Math.sin(Math.cos(Math.tan(Math.sqrt(0)+1)+2*Math.PI)+3*Math.PI);
    	assertEquals(expectedvalue, parserEval.evalEquation(), DELTA);
    }
    
    public void testBigDouble() throws ParserEvaluateException, BadNumberEvaluateException {
    	parserEval.setEquation(TXT_TESTEQUATION4_BIGDOUBLE);
    	double expectedValue = Math.sqrt(761782.78217482*2.76237)+Math.sin(1.3627186472);
    	assertEquals(expectedValue, parserEval.evalEquation(), DELTA);
    }
    
    public void testEmpty() throws ParserEvaluateException, BadNumberEvaluateException {
    	try {
	    	parserEval.setEquation("");
	    	parserEval.evalEquation();
			fail();
    	} catch (ParserEvaluateException e) {
    		assertTrue(true);
    	}
    }
    
    public void testWrong() throws ParserEvaluateException, BadNumberEvaluateException {
    	try {
	    	parserEval.setEquation(TXT_TESTEQUATION5_WRONG);
	    	parserEval.evalEquation();
	    	assertTrue(false);
    	} catch (ParserEvaluateException e) {
    		assertTrue(true);
    	}
    }
}



