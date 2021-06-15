package fr.diginamic.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.dao.RegionDao;
import fr.diginamic.entites.Region;
import fr.diginamic.exception.TechnicalException;
import fr.diginamic.utils.ConnectionMgr;

/**
 * Implémentation JDBC de {@link RegionDao}
 * 
 * @author DIGINAMIC
 *
 */
public class RegionDaoJdbc implements RegionDao {

	private Connection conn = null;
	private PreparedStatement statInsert = null;
	private PreparedStatement statExtractAll = null;
	private PreparedStatement statExtractParNom = null;

	public RegionDaoJdbc() {
		conn = ConnectionMgr.getConnection();
		try {
			statInsert = conn.prepareStatement("INSERT INTO REGION (NOM, CODE) VALUES (?,?)");
			statExtractAll = conn.prepareStatement("SELECT * FROM REGION");
			statExtractParNom = conn.prepareStatement("SELECT * FROM REGION reg WHERE nom=?");
		} catch (SQLException e) {
			throw new TechnicalException("Impossible de créer le PreparedStatement de DepartementDaoJdbc", e);
		}
	}

	@Override
	public void insert(Region region) {
		try {
			statInsert.setString(1, region.getNom());
			statInsert.setString(2, region.getCode());

			statInsert.executeUpdate();
			System.out.println("Nouvelle région insérée: " + region.getNom());

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new RuntimeException("Une exception grave s'est produite. L'application va s'arrêter.");
		}
	}

	@Override
	public List<Region> extraire() {
		ArrayList<Region> regions = new ArrayList<>();

		ResultSet res = null;
		try {
			res = statExtractAll.executeQuery();
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
			try {
				if (res != null) {
					res.close();
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return regions;
	}

	@Override
	public Region extraireParNom(String nom) {

		Region selection = null;

		ResultSet res = null;
		try {
			statExtractParNom.setString(1, nom);
			res = statExtractParNom.executeQuery();
			if (res.next()) {
				int id = res.getInt("id");
				String code = res.getString("code");

				selection = new Region(id, code, nom);
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new RuntimeException("Une exception grave s'est produite. L'application va s'arrêter.");
		} finally {
			try {
				if (res != null) {
					res.close();
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return selection;
	}

	/**
	 * Fermeture des ressources SQL
	 * 
	 * @param conn       connexion
	 * @param statInsert statement
	 * @param res        resultset
	 */
	public void close() {
		try {
			if (statInsert != null) {
				statInsert.close();
			}
			if (statExtractAll != null) {
				statExtractAll.close();
			}
			if (statExtractParNom != null) {
				statExtractParNom.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
