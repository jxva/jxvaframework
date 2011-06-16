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
package com.emart.model;

import java.io.Serializable;
import com.jxva.dao.annotation.Table;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2010-07-17 16:02:56 by Automatic Generate Toolkit
 */
@Table(name="tbl_type_supplier",increment="mapId",primaryKeys={"mapId"})
public class TypeSupplier implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer mapId;
	private java.lang.Integer typeId;
	private java.lang.Integer productId;
	private java.lang.Integer supplierId;

	public java.lang.Integer getMapId(){
		return this.mapId;
	}
	public void setMapId(java.lang.Integer mapId){
		this.mapId=mapId;
	}

	public java.lang.Integer getTypeId(){
		return this.typeId;
	}
	public void setTypeId(java.lang.Integer typeId){
		this.typeId=typeId;
	}

	public java.lang.Integer getProductId(){
		return this.productId;
	}
	public void setProductId(java.lang.Integer productId){
		this.productId=productId;
	}

	public java.lang.Integer getSupplierId(){
		return this.supplierId;
	}
	public void setSupplierId(java.lang.Integer supplierId){
		this.supplierId=supplierId;
	}

	public boolean equals(Object obj){
		return super.equals(obj);
	}

	public int hashCode(){
		return super.hashCode();
	}

	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("[ ");
		sb.append("mapId=").append(mapId).append(',');
		sb.append("typeId=").append(typeId).append(',');
		sb.append("productId=").append(productId).append(',');
		sb.append("supplierId=").append(supplierId).append(" ]");
		return sb.toString();
	}

}
