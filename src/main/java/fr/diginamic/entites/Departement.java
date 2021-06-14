package fr.diginamic.entites;

/**
 * Représente un département français
 * 
 * @author DIGINAMIC
 *
 */
public class Departement {

	/** id */
	private int id;
	/** numéro de département */
	private String numero;
	/** region */
	private Region region;

	/**
	 * Constructeur
	 * 
	 * @param id     identifiant
	 * @param numero numéro de département
	 * @param region region
	 */
	public Departement(int id, String numero, Region region) {
		this.id = id;
		this.numero = numero;
		this.region = region;
	}

	/**
	 * Getter
	 * 
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Setter
	 * 
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
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
