package fr.diginamic.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.dao.RegionDao;
import fr.diginamic.dao.VilleDao;
import fr.diginamic.entites.Departement;
import fr.diginamic.entites.Region;
import fr.diginamic.entites.Ville;
import fr.diginamic.exception.TechnicalException;
import fr.diginamic.utils.ConnectionMgr;

/**
 * Implémentation JDBC de {@link RegionDao}
 * 
 * @author DIGINAMIC
 *
 */
public class VilleDaoJdbc implements VilleDao {

	private Connection conn = null;
	private PreparedStatement statInsert = null;
	private PreparedStatement statExtractAll = null;
	private PreparedStatement statExtractParNom = null;

	public VilleDaoJdbc() {
		conn = ConnectionMgr.getConnection();
		try {
			statInsert = conn.prepareStatement(
					"INSERT INTO VILLE (NOM, CODE, POPULATION, ID_REGION, ID_DEPT) VALUES (?,?,?,?,?)");
			statExtractAll = conn.prepareStatement(
					"SELECT vi.id as id_ville, vi.code as code_ville, vi.nom as nom_ville, vi.population, "
							+ "dept.id as dept_id, dept.numero, "
							+ "reg.id as reg_id, reg.code as reg_code, reg.nom as reg_nom "
							+ "FROM VILLE vi, DEPARTEMENT dept, REGION reg "
							+ "WHERE vi.id_dept=dept.id AND vi.id_region=reg.id");
			statExtractParNom = conn.prepareStatement(
					"SELECT vi.id as id_ville, vi.code as code_ville, vi.nom as nom_ville, vi.population, "
							+ "dept.id as dept_id, dept.numero, "
							+ "reg.id as reg_id, reg.code as reg_code, reg.nom as reg_nom "
							+ "FROM VILLE vi, DEPARTEMENT dept, REGION reg "
							+ "WHERE vi.id_dept=dept.id AND vi.id_region=reg.id " + "AND vi.nom=?");
		} catch (SQLException e) {
			throw new TechnicalException("Impossible de créer le PreparedStatement de DepartementDaoJdbc", e);
		}
	}

	@Override
	public void insert(Ville ville) {
		try {
			statInsert.setString(1, ville.getNom());
			statInsert.setString(2, ville.getCode());
			statInsert.setInt(3, ville.getPopulation());
			statInsert.setInt(4, ville.getRegion().getId());
			statInsert.setInt(5, ville.getDepartement().getId());

			statInsert.executeUpdate();
			System.out.println("Nouvelle ville insérée: " + ville.getNom());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new RuntimeException("Une exception grave s'est produite. L'application va s'arrêter.");
		}
	}

	@Override
	public List<Ville> extraire() {
		ArrayList<Ville> villes = new ArrayList<>();

		ResultSet res = null;
		try {
			res = statExtractAll.executeQuery();
			while (res.next()) {
				int id = res.getInt("id_ville");
				String codeVille = res.getString("code_ville");
				String nomVille = res.getString("nom_ville");
				int population = res.getInt("population");

				int idReg = res.getInt("reg_id");
				String codeReg = res.getString("reg_code");
				String nomReg = res.getString("reg_nom");
				Region region = new Region(idReg, codeReg, nomReg);

				int idDept = res.getInt("dept_id");
				String numero = res.getString("numero");
				Departement dept = new Departement(idDept, numero, region);

				Ville ville = new Ville(id, nomVille, codeVille, population, dept, region);
				villes.add(ville);
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
		return villes;
	}

	@Override
	public Ville extraireParNom(String nom) {

		Ville selection = null;

		ResultSet res = null;
		try {
			statExtractParNom.setString(1, nom);
			res = statExtractParNom.executeQuery();
			if (res.next()) {
				int id = res.getInt("id_ville");
				String codeVille = res.getString("code_ville");
				int population = res.getInt("population");

				int idReg = res.getInt("reg_id");
				String codeReg = res.getString("reg_code");
				String nomReg = res.getString("reg_nom");
				Region region = new Region(idReg, codeReg, nomReg);

				int idDept = res.getInt("dept_id");
				String numero = res.getString("numero");
				Departement dept = new Departement(idDept, numero, region);

				selection = new Ville(id, nom, codeVille, population, dept, region);
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
