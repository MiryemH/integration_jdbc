package fr.diginamic.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.dao.DepartementDao;
import fr.diginamic.dao.RegionDao;
import fr.diginamic.entites.Departement;
import fr.diginamic.entites.Region;
import fr.diginamic.exception.TechnicalException;
import fr.diginamic.utils.ConnectionMgr;

/**
 * Implémentation JDBC de {@link RegionDao}
 * 
 * @author DIGINAMIC
 *
 */
public class DepartementDaoJdbc implements DepartementDao {

	private Connection conn = null;
	private PreparedStatement statInsert = null;
	private PreparedStatement statExtractAll = null;
	private PreparedStatement statExtractParNumero = null;

	public DepartementDaoJdbc() {
		conn = ConnectionMgr.getConnection();
		try {
			statInsert = conn.prepareStatement("INSERT INTO DEPARTEMENT (NUMERO, ID_REGION) VALUES (?,?)");
			statExtractAll = conn
					.prepareStatement("SELECT dept.id as id_dept, dept.numero, reg.id as reg_id, reg.code, reg.nom "
							+ "FROM DEPARTEMENT dept, REGION reg WHERE dept.id_region=reg.id");
			statExtractParNumero = conn
					.prepareStatement("SELECT dept.id as id_dept, dept.numero, reg.id as reg_id, reg.code, reg.nom "
							+ "FROM DEPARTEMENT dept, REGION reg WHERE dept.id_region=reg.id AND dept.numero=?");
		} catch (SQLException e) {
			throw new TechnicalException("Impossible de créer le PreparedStatement de DepartementDaoJdbc", e);
		}
	}

	@Override
	public void insert(Departement departement) {
		try {
			statInsert.setString(1, departement.getNumero());
			statInsert.setInt(2, departement.getRegion().getId());

			statInsert.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new RuntimeException("Une exception grave s'est produite. L'application va s'arrêter.");
		}
	}

	@Override
	public List<Departement> extraire() {
		ArrayList<Departement> departements = new ArrayList<>();

		ResultSet res = null;
		try {
			res = statExtractAll.executeQuery();
			while (res.next()) {
				int id = res.getInt("id");
				String numero = res.getString("numero");

				int idRegion = res.getInt("reg_id");
				String code = res.getString("code");
				String nom = res.getString("nom");
				Region region = new Region(idRegion, code, nom);

				Departement dept = new Departement(id, numero, region);

				departements.add(dept);
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
				throw new TechnicalException("Impossible de fermer le resultSet", e);
			}
		}
		return departements;
	}

	@Override
	public Departement extraireParNumero(String numero) {

		Departement selection = null;
		ResultSet res = null;
		try {
			statExtractParNumero.setString(1, numero);
			res = statExtractParNumero.executeQuery();
			if (res.next()) {
				int id = res.getInt("id");

				int idRegion = res.getInt("reg_id");
				String code = res.getString("code");
				String nom = res.getString("nom");
				Region region = new Region(idRegion, code, nom);

				selection = new Departement(id, numero, region);
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
				throw new TechnicalException("Impossible de fermer le resultSet", e);
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
			if (statExtractParNumero != null) {
				statExtractParNumero.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
