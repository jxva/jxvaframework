
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
package org.jxva.tree;

import java.io.Serializable;

public class NullNode extends Node implements Serializable {
	
	private static final long serialVersionUID = 7894825859078052908L;

		public java.lang.Integer getNodeid() {
			return 0;
		}

		public java.lang.Integer getParentid() {
			return -1;
		}

		public java.lang.Integer getRootid() {
			return 0;
		}

		public java.lang.Integer getLevelnum() {
			return 1;
		}

		public java.lang.String getLevelinfo() {
			return ",0";
		}

		public java.lang.String getNodename() {
			return "根结点";
		}

}
