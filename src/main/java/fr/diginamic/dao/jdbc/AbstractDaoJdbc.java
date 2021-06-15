package fr.diginamic.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDaoJdbc {

	protected final void closeResources(Connection conn, Statement stat) {
		try {
			if (stat != null) {
				stat.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	protected void closeResources(Connection conn, PreparedStatement stat, ResultSet res) {
		try {
			if (res != null) {
				res.close();
			}
			if (stat != null) {
				stat.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

}
