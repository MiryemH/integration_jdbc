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
import fr.diginamic.utils.ConnectionMgr;

/**
 * Implémentation JDBC de {@link RegionDao}
 * 
 * @author DIGINAMIC
 *
 */
public class DepartementDaoJdbc implements DepartementDao {

	@Override
	public void insert(Departement departement) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = ConnectionMgr.getConnection();
			stat = conn.prepareStatement("INSERT INTO DEPARTEMENT (NUMERO, ID_REGION) VALUES (?,?)");
			stat.setString(1, departement.getNumero());
			stat.setInt(2, departement.getRegion().getId());

			stat.executeUpdate();
			System.out.println("Nouvelle région insérée: " + departement.getNumero());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new RuntimeException("Une exception grave s'est produite. L'application va s'arrêter.");
		} finally {
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
	}

	@Override
	public List<Departement> extraire() {
		ArrayList<Departement> departements = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = ConnectionMgr.getConnection();
			stat = conn.prepareStatement("SELECT dept.id as id_dept, dept.numero, reg.id as reg_id, reg.code, reg.nom "
					+ "FROM DEPARTEMENT dept, REGION reg WHERE dept.id_region=reg.id");

			res = stat.executeQuery();
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
		return departements;
	}

	@Override
	public Departement extraireParNumero(String numero) {

		Departement selection = null;

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = ConnectionMgr.getConnection();
			stat = conn.prepareStatement("SELECT dept.id as id_dept, dept.numero, reg.id as reg_id, reg.code, reg.nom "
					+ "FROM DEPARTEMENT dept, REGION reg WHERE dept.id_region=reg.id AND dept.numero=?");
			stat.setString(1, numero);
			res = stat.executeQuery();
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
		return selection;
	}
}
