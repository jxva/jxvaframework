package org.jxva.dao;

import java.sql.SQLException;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOException;

public interface DaoCallback<T> {
	T doInDao(DAO dao) throws DAOException, SQLException;
}
