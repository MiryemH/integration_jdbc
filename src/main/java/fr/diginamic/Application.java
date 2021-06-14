package fr.diginamic;

import java.util.List;

import fr.diginamic.dao.jdbc.DepartementDaoJdbc;
import fr.diginamic.dao.jdbc.RegionDaoJdbc;
import fr.diginamic.dao.jdbc.VilleDaoJdbc;
import fr.diginamic.entites.Departement;
import fr.diginamic.entites.Region;
import fr.diginamic.entites.Ville;
import fr.diginamic.utils.RecensementUtils;

/**
 * Application d'intégration du fichier de recensement
 * 
 * @author RichardBONNAMY
 *
 */
public class Application {

	/**
	 * Point d'entrée de l'application d'intégration du fichier de recensement
	 * 
	 * @param args non utilisé ici
	 */
	public static void main(String[] args) {

		RegionDaoJdbc regionDao = new RegionDaoJdbc();
		DepartementDaoJdbc deptDao = new DepartementDaoJdbc();
		VilleDaoJdbc villeDao = new VilleDaoJdbc();

		List<Ville> villes = RecensementUtils.lire("C:/Temp/recensement.csv");

		for (Ville ville : villes) {
			Region region = regionDao.extraireParNom(ville.getRegion().getNom());
			if (region == null) {
				regionDao.insert(ville.getRegion());
				region = regionDao.extraireParNom(ville.getRegion().getNom());
			}
			ville.getDepartement().setRegion(region);
			ville.setRegion(region);

			Departement dept = deptDao.extraireParNumero(ville.getDepartement().getNumero());
			if (dept == null) {
				deptDao.insert(ville.getDepartement());
				dept = deptDao.extraireParNumero(ville.getDepartement().getNumero());
			}
			ville.setDepartement(dept);

			Ville villeFromBase = villeDao.extraireParNom(ville.getNom());
			if (villeFromBase == null) {
				villeDao.insert(ville);
			}
		}

	}

}
