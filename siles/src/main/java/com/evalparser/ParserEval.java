package com.evalparser;

import java.util.ArrayList;
import java.util.HashMap;

import com.evalparser.exceptions.BadNumberEvaluateException;
import com.evalparser.exceptions.ParserEvaluateException;
import com.evalparser.functions.MathFunction;

public abstract class ParserEval {
	private final HashMap<String,Double> paramsEq;
	private final ArrayList<MathFunction> functions;
	private String equation;
	
	public ParserEval() {
		paramsEq = new HashMap<>();
		functions = new ArrayList<>();
	}
	
	protected HashMap<String,Double> getParamsEq() {
		return paramsEq;
	}
	
	protected ArrayList<MathFunction> getFunctions() {
		return functions;
	}
	
	public void addVariable(String nameVar, double evalVar)
	{
		paramsEq.put(nameVar, evalVar);
	}
	
	public void removeVariable(String nameVar) {
		paramsEq.remove(nameVar);
	}
	
	public void addFunction(MathFunction f) {
		functions.add(f);
	}
	
	public void removeFunction(MathFunction f) {
		functions.remove(f);
	}
	
	public void addFunctions(ArrayList<MathFunction> fs) {
		functions.addAll(fs);
	}
	
	public void removeFunctions(ArrayList<MathFunction> fs) {
		functions.removeAll(fs);
	}
	
	public String getEquation() {
		return equation;
	}

	public void setEquation(String equation) {
		this.equation = equation;
	}
	
	abstract public double evalEquation() throws ParserEvaluateException, BadNumberEvaluateException;
	
	@Override
	public String toString() {
		return this.getEquation();
	}
}
