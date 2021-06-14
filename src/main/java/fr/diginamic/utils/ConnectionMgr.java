package fr.diginamic.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.mariadb.jdbc.Driver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import fr.diginamic.exception.TechnicalException;

/**
 * Cette classe fournit une méthode qui retourne une Connection à la base de
 * données. Un pool de connexions permet de gérer les connexions à la base de
 * données, ce qui garanit de biens meilleures performances car les connexions
 * sont ouvertes à l'avance dans le pool. Dès que vous en fermez une, une
 * connexion est automatiquement réouverte dans le pool. IMPORTANT: les
 * paramètres d'accès à la base de données sont dans le fichier database.xml.
 * 
 * @author DIGINAMIC
 *
 */
public class ConnectionMgr {

	/** Pool de connexions */
	private static ComboPooledDataSource connPool;

	/**
	 * Retourne une connexion à la base de données
	 * 
	 * @return {@link Connection}
	 */
	public static Connection getConnection() {

		// On regarde si l'objet configuration est null ou pas
		if (connPool == null) {

			// S'il est null, on invoque la méthode ci-dessous pour charger la configuration
			// du fichier database.xml ainsi que le driver JDBC
			loadConfiguration();
		}

		try {
			return connPool.getConnection();
		} catch (SQLException e) {
			throw new TechnicalException("Impossible d'ouvrir une connexion à la base de données", e);
		}
	}

	/**
	 * Cette méthode charge le driver JDBC ainsi que le fichier database.xml. Elle
	 * n'est exécutée qu'une seule fois.
	 */
	private static void loadConfiguration() {

		try {
			// Chargement du Driver
			DriverManager.registerDriver(new Driver());
		} catch (SQLException e) {
			throw new RuntimeException("Impossible de trouver le driver JDBC.");
		}

		try {
			// Chargement du fichier XML
			Configurations configurations = new Configurations();
			Configuration configuration = configurations.xml("database.xml");

			String driverClass = configuration.getString("database.driver");
			String url = configuration.getString("database.url");
			String user = configuration.getString("database.user");
			String pwd = configuration.getString("database.pwd");

			connPool = new ComboPooledDataSource();
			connPool.setDriverClass(driverClass); // loads the jdbc driver
			connPool.setJdbcUrl(url);
			connPool.setUser(user);
			connPool.setPassword(pwd);
			connPool.setMaxPoolSize(25);

		} catch (ConfigurationException e) {
			throw new TechnicalException("Impossible de lire la configuration de l'application", e);
		} catch (PropertyVetoException e) {
			throw new TechnicalException("Impossible de créer le pool de connexions", e);
		}
	}

	/**
	 * Ferme le pool de connexions
	 */
	public static void close() {
		connPool.close();
	}
}
