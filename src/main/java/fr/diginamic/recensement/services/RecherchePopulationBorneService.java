package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 *
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) {
		try {
			System.out.println("Quel est le code du département recherché ? ");
			String choix = scanner.nextLine();

			// Vérifier si le département existe
			boolean departementTrouve = false;
			for (Ville ville : rec.getVilles()) {
				if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
					departementTrouve = true;
					break;
				}
			}
			if (!departementTrouve) {
				throw new Exception("Le département avec le code " + choix + " n'existe pas.");
			}

			System.out.println("Choississez une population minimum (en milliers d'habitants): ");
			String saisieMin = scanner.nextLine();
			int min = Integer.parseInt(saisieMin) * 1000;

			System.out.println("Choississez une population maximum (en milliers d'habitants): ");
			String saisieMax = scanner.nextLine();
			int max = Integer.parseInt(saisieMax) * 1000;

			// Vérifier min et max
			if (min < 0 || max < 0) {
				throw new Exception("Les populations minimale et maximale doivent être positives.");
			}

			if (min > max) {
				throw new Exception("La population minimum doit être inférieure à la population maximum.");
			}

			boolean villesTrouvees = false;
			List<Ville> villes = rec.getVilles();
			for (Ville ville : villes) {
				if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
					if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
						System.out.println(ville);
						villesTrouvees = true;
					}
				}
			}

			if (!villesTrouvees) {
				System.out.println("Aucune ville ne correspond aux critères de recherche.");
			}
		} catch (NumberFormatException e) {
			throw new RuntimeException("Erreur : Veuillez saisir un nombre valide pour les populations.", e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}