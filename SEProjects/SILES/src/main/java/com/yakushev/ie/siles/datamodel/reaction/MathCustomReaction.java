package com.yakushev.ie.siles.datamodel.reaction;

import com.evalparser.ParserEval;
import com.evalparser.ParserEvalIndex;
import com.evalparser.exceptions.BadNumberEvaluateException;
import com.evalparser.exceptions.ParserEvaluateException;
import com.evalparser.functions.BasicFunctions;

public class MathCustomReaction extends CustomReaction {
	private static final String TXT_ERRORPARSER = "Синтаксически неправильное выражение. ";
	private static final String TXT_ERRORBADNUMBER = "Неправильное числовое значение. ";

	public MathCustomReaction(Reaction creaction) {
		super(creaction);
	}

	@Override
	public String getCustomAnswer(String msg) {
		String equation = this.getSubValue(msg);
		ParserEval parserEval = new ParserEvalIndex(equation);
		parserEval.addFunctions(BasicFunctions.getInstance());
		
		double result = 0.0;
		
		try {
			result = parserEval.evalEquation();
		} catch (ParserEvaluateException e) {
			return TXT_ERRORPARSER + e.getMessage();
		} catch (BadNumberEvaluateException e) {
			return TXT_ERRORBADNUMBER + e.getMessage();
		}
		
		return this.customReaction.getReaction() + " " + equation + " = " + result;
	}

}
