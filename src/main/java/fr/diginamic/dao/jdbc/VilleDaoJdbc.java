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
import fr.diginamic.utils.ConnectionMgr;

/**
 * Implémentation JDBC de {@link RegionDao}
 * 
 * @author DIGINAMIC
 *
 */
public class VilleDaoJdbc extends AbstractDaoJdbc implements VilleDao {

	@Override
	public void insert(Ville ville) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = ConnectionMgr.getConnection();
			stat = conn.prepareStatement(
					"INSERT INTO VILLE (NOM, CODE, POPULATION, ID_REGION, ID_DEPT) VALUES (?,?,?,?,?)");
			stat.setString(1, ville.getNom());
			stat.setString(2, ville.getCode());
			stat.setInt(3, ville.getPopulation());
			stat.setInt(4, ville.getRegion().getId());
			stat.setInt(5, ville.getDepartement().getId());

			stat.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new RuntimeException("Une exception grave s'est produite. L'application va s'arrêter.");
		} finally {
			closeResources(conn, stat);
		}
	}

	@Override
	public List<Ville> extraire() {
		ArrayList<Ville> villes = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = ConnectionMgr.getConnection();
			stat = conn.prepareStatement(
					"SELECT vi.id as id_ville, vi.code as code_ville, vi.nom as nom_ville, vi.population, "
							+ "dept.id as dept_id, dept.numero, "
							+ "reg.id as reg_id, reg.code as reg_code, reg.nom as reg_nom "
							+ "FROM VILLE vi, DEPARTEMENT dept, REGION reg "
							+ "WHERE vi.id_dept=dept.id AND vi.id_region=reg.id");

			res = stat.executeQuery();
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
			closeResources(conn, stat, res);
		}
		return villes;
	}

	@Override
	public Ville extraireParNomAndDept(String nom, String numero) {

		Ville selection = null;

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = ConnectionMgr.getConnection();
			stat = conn.prepareStatement(
					"SELECT vi.id as id_ville, vi.code as code_ville, vi.nom as nom_ville, vi.population, "
							+ "dept.id as dept_id, dept.numero, "
							+ "reg.id as reg_id, reg.code as reg_code, reg.nom as reg_nom "
							+ "FROM VILLE vi, DEPARTEMENT dept, REGION reg "
							+ "WHERE vi.id_dept=dept.id AND vi.id_region=reg.id " + "AND vi.nom=? AND dept.numero=?");
			stat.setString(1, nom);
			stat.setString(2, numero);
			res = stat.executeQuery();
			if (res.next()) {
				int id = res.getInt("id_ville");
				String codeVille = res.getString("code_ville");
				int population = res.getInt("population");

				int idReg = res.getInt("reg_id");
				String codeReg = res.getString("reg_code");
				String nomReg = res.getString("reg_nom");
				Region region = new Region(idReg, codeReg, nomReg);

				int idDept = res.getInt("dept_id");
				Departement dept = new Departement(idDept, numero, region);

				selection = new Ville(id, nom, codeVille, population, dept, region);
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
