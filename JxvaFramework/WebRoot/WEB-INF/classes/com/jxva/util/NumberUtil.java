/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jxva.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * 
 * Miscellaneous utility methods for number conversion and parsing.
 * Mainly for internal use within the framework; consider Jakarta's
 * Commons Lang for a more comprehensive suite of string utilities.
 *
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:25:46 by Jxva
 */
public abstract class NumberUtil {
	
	public static final char[] NUM_CHAR_TABLE={'0','1','2','3','4','5','6','7','8','9'};

	
	public static boolean isNumeric(String str){
		if(!StringUtil.hasText(str))return false;
		char[] cArray=str.toCharArray();
		for(char c:cArray){
			if(!Character.isDigit(c)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 将float数处理成precision位小数,四舍五入
	 * @param value float数
	 * @param precision 位数
	 * @return float
	 */
	public static float truncate(float value,int precision){
		return Float.parseFloat(Double.toString(truncate((double)value,precision)));
	}
	
	/**
	 * 将double数处理成precision位小数,四舍五入
	 * @param value double数
	 * @param precision 位数
	 * @return double
	 */
	public static double truncate(double value,int precision){
		String str=Double.toString(value);
		String precisionstr=str.substring(str.indexOf('.')+1);
		if(precisionstr.length()<=precision){
			return value;
		}else{
			long tmp=Math.round(value*Math.pow(10,precisionstr.length()));
			for(int i=0;i<precisionstr.length()-precision-1;i++){
				tmp/=10;
			}
			if(tmp%10>=5){
				tmp/=10;
				tmp+=1;
			}else{
				tmp/=10;
			}
			return ((double)tmp)/Math.pow(10,precision);
		}
	}
	
	/**
	 * 将float数转换为int数,四舍五入
	 * @param value float数
	 * @return int 
	 */
	public static int toInt(float value){
		return (new Float(truncate(value,0))).intValue();
	}
	
	/**
	 * 将double数转换为int数,四舍五入
	 * @param value double数
	 * @return int 
	 */
	public static int toInt(double value){
		return (new Double(truncate(value,0))).intValue();
	}
	
	
	
	/**
	 * Convert the given number into an instance of the given target class.
	 * @param number the number to convert
	 * @param targetClass the target class to convert to
	 * @return the converted number
	 * @throws IllegalArgumentException if the target class is not supported
	 * (i.e. not a standard Number subclass as included in the JDK)
	 * @see java.lang.Byte
	 * @see java.lang.Short
	 * @see java.lang.Integer
	 * @see java.lang.Long
	 * @see java.math.BigInteger
	 * @see java.lang.Float
	 * @see java.lang.Double
	 * @see java.math.BigDecimal
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> T convertNumberToTargetClass(Number number, Class<T> targetClass)
			throws IllegalArgumentException {

		Assert.notNull(number, "Number must not be null");
		Assert.notNull(targetClass, "Target class must not be null");

		if (targetClass.isInstance(number)) {
			return (T) number;
		}
		else if (targetClass.equals(Byte.class)) {
			long value = number.longValue();
			if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
				raiseOverflowException(number, targetClass);
			}
			return (T) new Byte(number.byteValue());
		}
		else if (targetClass.equals(Short.class)) {
			long value = number.longValue();
			if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
				raiseOverflowException(number, targetClass);
			}
			return (T) new Short(number.shortValue());
		}
		else if (targetClass.equals(Integer.class)) {
			long value = number.longValue();
			if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
				raiseOverflowException(number, targetClass);
			}
			return (T) new Integer(number.intValue());
		}
		else if (targetClass.equals(Long.class)) {
			return (T) new Long(number.longValue());
		}
		else if (targetClass.equals(BigInteger.class)) {
			if (number instanceof BigDecimal) {
				// do not lose precision - use BigDecimal's own conversion
				return (T) ((BigDecimal) number).toBigInteger();
			}
			else {
				// original value is not a Big* number - use standard long conversion
				return (T) BigInteger.valueOf(number.longValue());
			}
		}
		else if (targetClass.equals(Float.class)) {
			return (T) new Float(number.floatValue());
		}
		else if (targetClass.equals(Double.class)) {
			return (T) new Double(number.doubleValue());
		}
		else if (targetClass.equals(BigDecimal.class)) {
			// always use BigDecimal(String) here to avoid unpredictability of BigDecimal(double)
			// (see BigDecimal javadoc for details)
			return (T) new BigDecimal(number.toString());
		}
		else {
			throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" +
					number.getClass().getName() + "] to unknown target class [" + targetClass.getName() + "]");
		}
	}

	/**
	 * Raise an overflow exception for the given number and target class.
	 * @param number the number we tried to convert
	 * @param targetClass the target class we tried to convert to
	 */
	private static void raiseOverflowException(Number number, Class<?> targetClass) {
		throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" +
				number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
	}

	/**
	 * Parse the given text into a number instance of the given target class,
	 * using the corresponding <code>decode</code> / <code>valueOf</code> methods.
	 * <p>Trims the input <code>String</code> before attempting to parse the number.
	 * Supports numbers in hex format (with leading "0x", "0X" or "#") as well.
	 * @param text the text to convert
	 * @param targetClass the target class to parse into
	 * @return the parsed number
	 * @throws IllegalArgumentException if the target class is not supported
	 * (i.e. not a standard Number subclass as included in the JDK)
	 * @see java.lang.Byte#decode
	 * @see java.lang.Short#decode
	 * @see java.lang.Integer#decode
	 * @see java.lang.Long#decode
	 * @see #decodeBigInteger(String)
	 * @see java.lang.Float#valueOf
	 * @see java.lang.Double#valueOf
	 * @see java.math.BigDecimal#BigDecimal(String)
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> T parseNumber(String text, Class<T> targetClass) {
		Assert.notNull(text, "Text must not be null");
		Assert.notNull(targetClass, "Target class must not be null");
		String trimmed = StringUtil.deleteWhitespace(text);

		if (targetClass.equals(Byte.class)) {
			return (T) (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
		}
		else if (targetClass.equals(Short.class)) {
			return (T) (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
		}
		else if (targetClass.equals(Integer.class)) {
			return (T) (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
		}
		else if (targetClass.equals(Long.class)) {
			return (T) (isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
		}
		else if (targetClass.equals(BigInteger.class)) {
			return (T) (isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
		}
		else if (targetClass.equals(Float.class)) {
			return (T) Float.valueOf(trimmed);
		}
		else if (targetClass.equals(Double.class)) {
			return (T) Double.valueOf(trimmed);
		}
		else if (targetClass.equals(BigDecimal.class) || targetClass.equals(Number.class)) {
			return (T) new BigDecimal(trimmed);
		}
		else {
			throw new IllegalArgumentException(
					"Cannot convert String [" + text + "] to target class [" + targetClass.getName() + "]");
		}
	}

	/**
	 * Parse the given text into a number instance of the given target class,
	 * using the given NumberFormat. Trims the input <code>String</code>
	 * before attempting to parse the number.
	 * @param text the text to convert
	 * @param targetClass the target class to parse into
	 * @param numberFormat the NumberFormat to use for parsing (if <code>null</code>,
	 * this method falls back to <code>parseNumber(String, Class)</code>)
	 * @return the parsed number
	 * @throws IllegalArgumentException if the target class is not supported
	 * (i.e. not a standard Number subclass as included in the JDK)
	 * @see java.text.NumberFormat#parse
	 * @see #convertNumberToTargetClass
	 * @see #parseNumber(String, Class)
	 */
	public static <T extends Number> T parseNumber(String text, Class<T> targetClass, NumberFormat numberFormat) {
		if (numberFormat != null) {
			Assert.notNull(text, "Text must not be null");
			Assert.notNull(targetClass, "Target class must not be null");
			DecimalFormat decimalFormat = null;
			boolean resetBigDecimal = false;
			if (numberFormat instanceof DecimalFormat) {
				decimalFormat = (DecimalFormat) numberFormat;
				if (BigDecimal.class.equals(targetClass) && !decimalFormat.isParseBigDecimal()) {
					decimalFormat.setParseBigDecimal(true);
					resetBigDecimal = true;
				}
			}
			try {
				Number number = numberFormat.parse(StringUtil.deleteWhitespace(text));
				return convertNumberToTargetClass(number, targetClass);
			}
			catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse number: " + ex.getMessage());
			}
			finally {
				if (resetBigDecimal) {
					decimalFormat.setParseBigDecimal(false);
				}
			}
		}
		else {
			return parseNumber(text, targetClass);
		}
	}

	/**
	 * Determine whether the given value String indicates a hex number, i.e. needs to be
	 * passed into <code>Integer.decode</code> instead of <code>Integer.valueOf</code> (etc).
	 */
	private static boolean isHexNumber(String value) {
		int index = (value.startsWith("-") ? 1 : 0);
		return (value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index));
	}

	/**
	 * Decode a {@link java.math.BigInteger} from a {@link String} value.
	 * Supports decimal, hex and octal notation.
	 * @see BigInteger#BigInteger(String, int)
	 */
	private static BigInteger decodeBigInteger(String value) {
		int radix = 10;
		int index = 0;
		boolean negative = false;

		// Handle minus sign, if present.
		if (value.startsWith("-")) {
			negative = true;
			index++;
		}

		// Handle radix specifier, if present.
		if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
			index += 2;
			radix = 16;
		}
		else if (value.startsWith("#", index)) {
			index++;
			radix = 16;
		}
		else if (value.startsWith("0", index) && value.length() > 1 + index) {
			index++;
			radix = 8;
		}

		BigInteger result = new BigInteger(value.substring(index), radix);
		return (negative ? result.negate() : result);
	}
	

	
}
