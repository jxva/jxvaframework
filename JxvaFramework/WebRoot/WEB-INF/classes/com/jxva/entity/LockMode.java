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
 *
 */
package com.jxva.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:28:39 by Jxva
 */
public final class LockMode implements Serializable {

	private static final long serialVersionUID = -2273180550978730721L;
	private final int level;
	private final String name;
	private static final Map<String,LockMode> INSTANCES = new HashMap<String,LockMode>();
	public static final LockMode NONE = new LockMode(0, "NONE");
	public static final LockMode READ = new LockMode(5, "READ");
	public static final LockMode UPGRADE = new LockMode(10, "UPGRADE");
	public static final LockMode UPGRADE_NOWAIT = new LockMode(10, "UPGRADE_NOWAIT");
	public static final LockMode WRITE = new LockMode(10, "WRITE");
	public static final LockMode FORCE = new LockMode( 15, "FORCE" );

	static {
		INSTANCES.put( NONE.name, NONE );
		INSTANCES.put( READ.name, READ );
		INSTANCES.put( UPGRADE.name, UPGRADE );
		INSTANCES.put( UPGRADE_NOWAIT.name, UPGRADE_NOWAIT );
		INSTANCES.put( WRITE.name, WRITE );
		INSTANCES.put( FORCE.name, FORCE );
	}

	private LockMode(int level, String name) {
		this.level=level;
		this.name=name;
	}
	public String toString() {
		return name;
	}

	public boolean greaterThan(LockMode mode) {
		return level > mode.level;
	}

	public boolean lessThan(LockMode mode) {
		return level < mode.level;
	}
	
	private Object readResolve() {
		return parse( name );
	}

	public static LockMode parse(String name) {
		return INSTANCES.get(name);
	}
}
