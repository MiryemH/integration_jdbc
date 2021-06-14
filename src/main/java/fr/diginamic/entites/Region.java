package fr.diginamic.entites;

/**
 * Représente une région française
 * 
 * @author DIGINAMIC
 *
 */
public class Region {

	/** id */
	private int id;
	/** code INSEE */
	private String code;
	/** nom de la région */
	private String nom;

	/**
	 * Constructeur
	 * 
	 * @param id   identifiant
	 * @param code code INSEE
	 * @param nom  nom de la région
	 */
	public Region(int id, String code, String nom) {
		super();
		this.id = id;
		this.code = code;
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

}
