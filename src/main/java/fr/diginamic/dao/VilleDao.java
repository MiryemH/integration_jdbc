package fr.diginamic.dao;

import java.util.List;

import fr.diginamic.entites.Ville;

/**
 * DAO {@link VilleDao}
 * 
 * @author DIGINAMIC
 *
 */
public interface VilleDao {

	/**
	 * Extrait la liste des villes
	 * 
	 * @return List
	 */
	List<Ville> extraire();

	/**
	 * Insère une nouvelle ville
	 * 
	 * @param ville nvlle ville
	 */
	void insert(Ville ville);

	/**
	 * Extrait la ville dont le nom est passé en paramètre
	 * 
	 * @param nom nom de la ville
	 * @return {@link Ville}
	 */
	Ville extraireParNom(String nom);

}