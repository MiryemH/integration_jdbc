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
	 * Extrait la ville dont le nom et le numéro de département sont passés en
	 * paramètre
	 * 
	 * @param nom    nom de la ville
	 * @param numero numéro de département
	 * @return {@link Ville}
	 */
	Ville extraireParNomAndDept(String nom, String numero);

}