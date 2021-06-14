package fr.diginamic.exception;

/**
 * Exception technique grave
 * 
 * @author DIGINAMIC
 *
 */
public class TechnicalException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 * 
	 * @param message message
	 */
	public TechnicalException(String message) {
		super(message);
	}

	/**
	 * Constructeur
	 * 
	 * @param message message
	 * @param cause   cause racine
	 */
	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
	}
}
