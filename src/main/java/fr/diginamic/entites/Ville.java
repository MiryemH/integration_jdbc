package fr.diginamic.entites;

/**
 * Représente une ville française
 * 
 * @author DIGINAMIC
 *
 */
public class Ville {

	/** identifiant */
	private int id;
	/** nom */
	private String nom;
	/** code INSEE */
	private String code;
	/** population */
	private int population;

	/** département */
	private Departement departement;
	/** région */
	private Region region;

	/**
	 * Constructeur
	 * 
	 * @param id          id
	 * @param nom         nom de la ville
	 * @param code        code INSEE
	 * @param population  population totale
	 * @param departement département
	 * @param region      région
	 */
	public Ville(int id, String nom, String code, int population, Departement departement, Region region) {
		super();
		this.id = id;
		this.nom = nom;
		this.code = code;
		this.population = population;
		this.departement = departement;
		this.region = region;
	}

	/**
	 * Getter
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter
	 * 
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter
	 * 
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter
	 * 
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Setter
	 * 
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Getter
	 * 
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}

	/**
	 * Setter
	 * 
	 * @param population the population to set
	 */
	public void setPopulation(int population) {
		this.population = population;
	}

	/**
	 * Getter
	 * 
	 * @return the departement
	 */
	public Departement getDepartement() {
		return departement;
	}

	/**
	 * Setter
	 * 
	 * @param departement the departement to set
	 */
	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	/**
	 * Getter
	 * 
	 * @return the region
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * Setter
	 * 
	 * @param region the region to set
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

}
