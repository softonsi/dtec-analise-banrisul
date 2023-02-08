package br.com.softon.dtec.utils;

public class DoubleResultCarrier {

	private Long val1;
	private Long val2;

	public DoubleResultCarrier(long val1, long val2) {
		this.val1 = val1;
		this.val2 = val2;

	}
	
	public long val1() {
		return val1;
	}
	
	public long val2() {
		return val2;
	}
	
}
