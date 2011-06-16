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
package com.emart.dao;

import com.jxva.dao.BaseDao;
import com.emart.model.TypeSupplier;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2010-07-17 16:02:56 by Automatic Generate Toolkit
 */
public class TypeSupplierDao extends BaseDao{

	public TypeSupplier getTypeSupplier(int mapId){
		return dao.get(TypeSupplier.class,mapId);
	}

	public int	save(TypeSupplier typeSupplier){
		return dao.save(typeSupplier);
	}

	public int update(TypeSupplier typeSupplier){
		return dao.update(typeSupplier);
	}

	public int delete(TypeSupplier typeSupplier){
		return dao.delete(typeSupplier);
	}

	public int delete(int mapId){
		return dao.delete(TypeSupplier.class,mapId);
	}

	public int saveOrUpdate(TypeSupplier typeSupplier){
		return dao.saveOrUpdate(typeSupplier);
	}

}
