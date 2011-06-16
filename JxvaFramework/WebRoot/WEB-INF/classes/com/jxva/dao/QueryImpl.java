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
package com.jxva.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-07 16:13:19 by Jxva
 */
public class QueryImpl implements Query {

	private final Statement statement;
	private final JdbcTemplate template;
	
	public QueryImpl(Statement statement,JdbcTemplate template){
		this.statement=statement;
		this.template=template;
	}
	
	public int executeUpdate() {
		return template.update(statement.getSql());
	}

	
	public String[] getNamedParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getQueryString() {
		return statement.getSql();
	}

	
	public Iterator iterate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List list() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setBigDecimal(int position, BigDecimal number) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setBigDecimal(String name, BigDecimal number) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setBigInteger(int position, BigInteger number) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setBigInteger(String name, BigInteger number) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setBinary(int position, byte[] val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setBinary(String name, byte[] val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setBoolean(int position, boolean val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setBoolean(String name, boolean val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setByte(int position, byte val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setByte(String name, byte val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setCacheable(boolean cacheable) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setCalendar(int position, Calendar calendar) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setCalendar(String name, Calendar calendar) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setCalendarDate(int position, Calendar calendar) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setCalendarDate(String name, Calendar calendar) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setCharacter(int position, char val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setCharacter(String name, char val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setDate(int position, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setDate(String name, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setDouble(int position, double val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setDouble(String name, double val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setFetchSize(int fetchSize) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setFirstResult(int firstResult) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setFloat(int position, float val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setFloat(String name, float val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setInteger(int position, int val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setInteger(String name, int val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setLocale(int position, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setLocale(String name, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setLong(int position, long val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setLong(String name, long val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setMaxResults(int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setParameter(int position, Object val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setParameter(String name, Object val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setProperties(Map<String, Object> bean) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setSerializable(int position, Serializable val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setSerializable(String name, Serializable val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setShort(int position, short val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setShort(String name, short val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setString(int position, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setString(String name, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setText(int position, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setText(String name, String val) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setTime(int position, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setTime(String name, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setTimestamp(int position, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Query setTimestamp(String name, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Object uniqueResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
