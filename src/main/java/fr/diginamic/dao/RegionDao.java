package fr.diginamic.dao;

import java.util.List;

import fr.diginamic.entites.Region;

/**
 * DAO {@link Region}
 * 
 * @author DIGINAMIC
 *
 */
public interface RegionDao {

	/**
	 * Extrait la liste des régions
	 * 
	 * @return List
	 */
	List<Region> extraire();

	/**
	 * Insère une nouvelle région
	 * 
	 * @param region nvlle région
	 */
	void insert(Region region);

	/**
	 * Extrait la région dont le nom est passé en paramètre
	 * 
	 * @param nom nom de la région
	 * @return {@link Region}
	 */
	Region extraireParNom(String nom);

}