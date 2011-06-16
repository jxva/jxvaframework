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
package com.jxva.dao.entry;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-07 21:51:07 by Jxva
 */
public class RelationEntry {
	
	private ModelEntry previousModelEntry;
	private ModelEntry nextModelEntry;
	
	private int relation;
	private boolean isFetch;

	public int getRelation() {
		return relation;
	}
	public void setRelation(int relation) {
		this.relation = relation;
	}
	public boolean getIsFetch() {
		return isFetch;
	}
	public void setIsFetch(boolean isFetch) {
		this.isFetch = isFetch;
	}

	public void setPreviousModelEntry(ModelEntry previousModelEntry) {
		this.previousModelEntry = previousModelEntry;
	}
	public ModelEntry getPreviousModelEntry() {
		return previousModelEntry;
	}
	public void setNextModelEntry(ModelEntry nextModelEntry) {
		this.nextModelEntry = nextModelEntry;
	}
	public ModelEntry getNextModelEntry() {
		return nextModelEntry;
	}

}
