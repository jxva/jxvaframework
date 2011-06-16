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
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2009-03-24 14:26:37 by Jxva
 */
public interface Query {

	public String getQueryString();

	public String[] getNamedParameters();

	public Iterator iterate();

	public List list();

	public Object uniqueResult();

	public int executeUpdate();

	public Query setMaxResults(int maxResults);

	public Query setFirstResult(int firstResult);

	public Query setReadOnly(boolean readOnly);

	public Query setCacheable(boolean cacheable);

	public Query setFetchSize(int fetchSize);

	public Query setParameter(int position, Object val);

	public Query setParameter(String name, Object val);

	public Query setProperties(Map<String, Object> bean);

	public Query setString(int position, String val);

	public Query setCharacter(int position, char val);

	public Query setBoolean(int position, boolean val);

	public Query setByte(int position, byte val);

	public Query setShort(int position, short val);

	public Query setInteger(int position, int val);

	public Query setLong(int position, long val);

	public Query setFloat(int position, float val);

	public Query setDouble(int position, double val);

	public Query setBinary(int position, byte[] val);

	public Query setText(int position, String val);

	public Query setSerializable(int position, Serializable val);

	public Query setLocale(int position, Locale locale);

	public Query setBigDecimal(int position, BigDecimal number);

	public Query setBigInteger(int position, BigInteger number);

	public Query setDate(int position, Date date);

	public Query setTime(int position, Date date);

	public Query setTimestamp(int position, Date date);

	public Query setCalendar(int position, Calendar calendar);

	public Query setCalendarDate(int position, Calendar calendar);

	public Query setString(String name, String val);

	public Query setCharacter(String name, char val);

	public Query setBoolean(String name, boolean val);

	public Query setByte(String name, byte val);

	public Query setShort(String name, short val);

	public Query setInteger(String name, int val);

	public Query setLong(String name, long val);

	public Query setFloat(String name, float val);

	public Query setDouble(String name, double val);

	public Query setBinary(String name, byte[] val);

	public Query setText(String name, String val);

	public Query setSerializable(String name, Serializable val);

	public Query setLocale(String name, Locale locale);

	public Query setBigDecimal(String name, BigDecimal number);

	public Query setBigInteger(String name, BigInteger number);

	public Query setDate(String name, Date date);

	public Query setTime(String name, Date date);

	public Query setTimestamp(String name, Date date);

	public Query setCalendar(String name, Calendar calendar);

	public Query setCalendarDate(String name, Calendar calendar);

}
