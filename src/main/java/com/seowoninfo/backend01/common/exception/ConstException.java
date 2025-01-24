package com.seowoninfo.backend01.common.exception;

public class ConstException {

	public enum ExceptionClass{
		
		MEMBER("회원")
		, SAMPLE("Sample")
		
		, TOKEN("jwtToken")
		;
		
		private String exceptionClass;
		
		ExceptionClass(String exceptionClass) { this.exceptionClass = exceptionClass;}
		
		public String getExceptionClass() {return exceptionClass;}
		
		@Override
		public String toString() {return getExceptionClass() + " Exception. ";}
		
	}
	
}
