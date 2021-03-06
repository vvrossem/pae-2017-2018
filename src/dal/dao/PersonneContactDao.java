package dal.dao;

import java.util.List;
import biz.pdc.PersonneContactDto;

public interface PersonneContactDao {
  /**
   * Récupère et renvoie une liste de personnes de contact pour une entreprise donnée.
   * 
   * @param idEntreprise l'id de l'entreprise concernée
   * @return une liste de PersonneContactDto remplie ou vide
   */
  public List<PersonneContactDto> listerPersonnesContactByIdEntreprise(int idEntreprise);

  /**
   * Insère une personne de contact dans la base de données.
   * 
   * @param personneContact la personne de contact à insérer
   * @return un objet de type PersonneContactDto si l'insertion à réussie, null sinon
   */
  public PersonneContactDto insertPersonneContact(PersonneContactDto personneContact);

  /**
   * Vérifie si une personne de contact est déjà lié à une entreprise.
   * 
   * @param idPersonneContact la personne de contact a vérifier
   * @param idEntreprise l'entreprise à vérifier
   * @return vrai si il y a déjà un contact, faux sinon
   */
  public boolean personneDeContactAppartientEntreprise(int idPersonneContact, int idEntreprise);

  /**
   * renvoi la personne de contact correspondante à l'id passé en paramètre.
   * 
   * @param idPersonneContact l'id de la personne de contact
   * @return l'objet PersonneContactDto correspondant à l'id si la personne de contact existe, null
   *         sinon
   */
  public PersonneContactDto getPersonneContact(int idPersonneContact);

  /**
   * Récupère et renvoie une liste de personnes de contact responsables de stages.
   * 
   * @return une liste de PersonneContactDto
   */
  public List<PersonneContactDto> getResponsablesStage();

  /**
   * Met à jour l'entreprise de la personne de contact dont l'id et le numero de version sont passé
   * en paramètre.
   * 
   * @param personneContact un objet de type PersonneContactDto représentant la personne de contact
   * @return true si le transfert s'est bien déroulé, throws exception sinon
   */
  public PersonneContactDto updatePersonneContact(PersonneContactDto personneContact);

}
