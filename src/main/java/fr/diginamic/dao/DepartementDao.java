package fr.diginamic.dao;

import java.util.List;

import fr.diginamic.entites.Departement;

/**
 * DAO {@link Departement}
 * 
 * @author DIGINAMIC
 *
 */
public interface DepartementDao {

	/**
	 * Extrait la liste des départements
	 * 
	 * @return List
	 */
	List<Departement> extraire();

	/**
	 * Insère un nouveau département
	 * 
	 * @param departement nv département
	 */
	void insert(Departement departement);

	/**
	 * Extrait le département dont le numéro est passé en paramètre
	 * 
	 * @param numero numéro du département
	 * @return {@link Departement}
	 */
	Departement extraireParNumero(String numero);

}