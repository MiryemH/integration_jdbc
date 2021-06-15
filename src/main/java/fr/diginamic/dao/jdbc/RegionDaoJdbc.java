package fr.diginamic.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.dao.RegionDao;
import fr.diginamic.entites.Region;
import fr.diginamic.utils.ConnectionMgr;

/**
 * Implémentation JDBC de {@link RegionDao}
 * 
 * @author DIGINAMIC
 *
 */
public class RegionDaoJdbc extends AbstractDaoJdbc implements RegionDao {

	@Override
	public void insert(Region region) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = ConnectionMgr.getConnection();
			stat = conn.prepareStatement("INSERT INTO REGION (NOM, CODE) VALUES (?,?)");
			stat.setString(1, region.getNom());
			stat.setString(2, region.getCode());

			stat.executeUpdate();
			System.out.println("Nouvelle région insérée: " + region.getNom());

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new RuntimeException("Une exception grave s'est produite. L'application va s'arrêter.");
		} finally {
			closeResources(conn, stat);
		}
	}

	@Override
	public List<Region> extraire() {
		ArrayList<Region> regions = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = ConnectionMgr.getConnection();
			stat = conn.prepareStatement("SELECT * FROM REGION");

			res = stat.executeQuery();
			while (res.next()) {
				int id = res.getInt("id");
				String code = res.getString("code");
				String nom = res.getString("nom");
				Region region = new Region(id, code, nom);

				regions.add(region);
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new RuntimeException("Une exception grave s'est produite. L'application va s'arrêter.");
		} finally {
			closeResources(conn, stat, res);
		}
		return regions;
	}

	@Override
	public Region extraireParNom(String nom) {

		Region selection = null;

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = ConnectionMgr.getConnection();
			stat = conn.prepareStatement("SELECT * FROM REGION reg WHERE nom=?");
			stat.setString(1, nom);
			res = stat.executeQuery();
			if (res.next()) {
				int id = res.getInt("id");
				String code = res.getString("code");

				selection = new Region(id, code, nom);
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new RuntimeException("Une exception grave s'est produite. L'application va s'arrêter.");
		} finally {
			closeResources(conn, stat, res);
		}
		return selection;
	}
}
