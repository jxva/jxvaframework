package com.jxva.entity;

import java.lang.reflect.Array;

public class HashCodeBuilder {
	
	public HashCodeBuilder() {}
	
	private int result = 17;

	public HashCodeBuilder append(boolean field) {
		result = 37 * result + (field ? 1 : 0);
		return this;
	}

	public HashCodeBuilder append(byte field) {
		result = 37 * result + (int) field;
		return this;
	}

	public HashCodeBuilder append(char field) {
		result = 37 * result + (int) field;
		return this;
	}

	public HashCodeBuilder append(short field) {
		result = 37 * result + (int) field;
		return this;
	}

	public HashCodeBuilder append(int field) {
		result = 37 * result + field;
		return this;
	}

	public HashCodeBuilder append(long field) {
		result = 37 * result + (int) (field ^ (field >>> 32));
		return this;
	}

	public HashCodeBuilder append(float field) {
		result = 37 * result + Float.floatToIntBits(field);
		return this;
	}

	public HashCodeBuilder append(double field) {
		append(Double.doubleToLongBits(field));
		return this;
	}

	public HashCodeBuilder append(Object field) {
		if (field == null)
			result = 0;
		else if (field.getClass().isArray()) {
			for (int i = Array.getLength(field) - 1; i >= 0; i--) {
				append(Array.get(field, i));
			}
		} else
			append(field.hashCode());
		return this;
	}

	public int toHashCode() {
		return result;
	}

	public int hashCode() {
		return result;
	}

	public String toString() {
		return String.valueOf(result);
	}
}