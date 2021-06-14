package fr.diginamic.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.entites.Ville;

/**
 * Classe qui parse le fichier de recensement et extrait une liste de
 * {@link Ville}
 * 
 * @author DIGINAMIC
 *
 */
public class RecensementUtils {

	/**
	 * Lit le contenu du fichier en paramétre, contenant des données de recensement
	 * avec le format attendu, et retourne une instance de la classe Recensement
	 * avec toutes ses villes
	 * 
	 * @param cheminFichier chemin d'accés au fichier sur le disque dur
	 * @return {@link Recensement}
	 */
	public static List<Ville> lire(String cheminFichier) {
		List<Ville> villes = new ArrayList<>();

		try {
			Path pathOrigine = Paths.get(cheminFichier);
			List<String> lignes = Files.readAllLines(pathOrigine, StandardCharsets.UTF_8);

			// On supprime la ligne d'entéte avec les noms des colonnes
			lignes.remove(0);

			for (String ligne : lignes) {
				villes.add(ParseurVille.toVille(ligne));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return villes;
	}
}