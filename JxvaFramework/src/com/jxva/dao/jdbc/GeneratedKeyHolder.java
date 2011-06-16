/*
 * Copyright 2002-2008 the original author or authors.
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

package com.jxva.dao.jdbc;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Default implementation of the {@link KeyHolder} interface, to be used for
 * holding auto-generated keys (as potentially returned by JDBC insert statements).
 *
 * <p>Create an instance of this class for each insert operation, and pass
 * it to the corresponding {@link org.springframework.jdbc.core.JdbcTemplate}
 * or {org.springframework.jdbc.object.SqlUpdate} methods.
 *
 * @author Thomas Risberg
 * @author Juergen Hoeller
 * @since 1.1
 */
public class GeneratedKeyHolder implements KeyHolder {

	private final List<Map<String, Object>> keyList;


	/**
	 * Create a new GeneratedKeyHolder with a default list.
	 */
	public GeneratedKeyHolder() {
		this.keyList = new LinkedList<Map<String, Object>>();
	}

	/**
	 * Create a new GeneratedKeyHolder with a given list.
	 * @param keyList a list to hold maps of keys
	 */
	public GeneratedKeyHolder(List<Map<String, Object>> keyList) {
		this.keyList = keyList;
	}


	public Number getKey() throws SQLException {
		if (this.keyList.size() == 0) {
			return null;
		}
		if (this.keyList.size() > 1 || this.keyList.get(0).size() > 1) {
			throw new SQLException(
					"The getKey method should only be used when a single key is returned.  " +
					"The current key entry contains multiple keys: " + this.keyList);
		}
		Iterator<Object> keyIter = this.keyList.get(0).values().iterator();
		if (keyIter.hasNext()) {
			Object key = keyIter.next();
			if (!(key instanceof Number)) {
				throw new SQLException(
						"The generated key is not of a supported numeric type. " +
						"Unable to cast [" + (key != null ? key.getClass().getName() : null) +
						"] to [" + Number.class.getName() + "]");
			}
			return (Number) key;
		}
		else {
			throw new SQLException("Unable to retrieve the generated key. " +
					"Check that the table has an identity column enabled.");
		}
	}

	public Map<String, Object> getKeys() throws SQLException {
		if (this.keyList.size() == 0) {
			return null;
		}
		if (this.keyList.size() > 1)
			throw new SQLException(
					"The getKeys method should only be used when keys for a single row are returned.  " +
					"The current key list contains keys for multiple rows: " + this.keyList);
		return this.keyList.get(0);
	}

	public List<Map<String, Object>> getKeyList() {
		return this.keyList;
	}

}
