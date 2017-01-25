package com.evalparser.functions;


public class TanFunction extends MathFunction {
	public TanFunction() {
		super.setName("tan");
	}
	
	@Override
	public double eval(double arg) {
		return Math.tan(arg);
	}
}
