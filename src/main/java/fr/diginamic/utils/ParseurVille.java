package fr.diginamic.utils;

import fr.diginamic.entites.Departement;
import fr.diginamic.entites.Region;
import fr.diginamic.entites.Ville;

/**
 * Permet de constituer notre recensement progressivement é partir des lignes du
 * fichier
 * 
 * @author DIGINAMIC
 *
 */
public class ParseurVille {

	/**
	 * Ajoute une ligne représentant une ville au recensement
	 * 
	 * @param recensement recensement é compléter
	 * @param ligne       ligne de laquelle on extrait une ville
	 */
	public static Ville toVille(String ligne) {

		String[] morceaux = ligne.split(";");
		String codeRegion = morceaux[0];
		String nomRegion = morceaux[1];
		String numero = morceaux[2];
		String codeCommune = morceaux[5];
		String nomCommune = morceaux[6];
		String population = morceaux[7];
		int populationTotale = Integer.parseInt(population.replace(" ", "").trim());

		Region region = new Region(0, codeRegion, nomRegion);
		Departement departement = new Departement(0, numero, region);
		return new Ville(0, nomCommune, codeCommune, populationTotale, departement, region);

	}

}