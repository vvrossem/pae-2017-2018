package dal.dao;

import java.util.List;
import biz.contact.ContactDto;
import exceptions.FatalException;

public interface ContactDao {
  /**
   * Récupère et renvoie le contact dont l'id est passé en paramètre.
   * 
   * @param idContact l'id du contact à récuperer
   * @return le contact correspondant à l'id, null si l'id n'existe pas
   */
  public ContactDto getContact(int idContact);

  /**
   * Récupère et renvoie la liste de contacts dont l'idUser et l'état sont passés en paramètre.
   * 
   * @param idUser l'id de l'utilisateur
   * @param etat l'état dans lequel se trouve le contact
   * @return une liste de contacts de l'utilisateur, cette liste est vide s'il n'y a pas de contact
   *         pour cet utilisateur
   */
  public List<ContactDto> getContacts(int idUser, int etat);

  /**
   * Récupère et renvoie la liste de contacts dont l'id de l'entreprise est passé en paramètre.
   * 
   * @param idEntreprise l'id de l'entreprise liée au contact
   * @return une liste de contacts de l'entreprise, une liste vide s'il n'y a pas de contacts pour
   *         cette entreprise
   */
  public List<ContactDto> listerContactsPourEntreprise(int idEntreprise);

  /**
   * Récupère et renvoie une liste de contacts pour un utilisateur donné.
   * 
   * @param utilisateur ,l'utilisateur dont on recherche les contacts
   * @return une liste de contacts de l'utilisateur, cette liste est vide s'il n'y a pas de contact
   *         pour cet utilisateur
   */
  public List<ContactDto> listerContactsPourUtilisateur(int utilisateur);

  /**
   * Insère un contact entre un utilisateur et une entreprise avec une personne de contact.
   * 
   * @param utilisateur l'utilisateur qui sera lié au contact avec l'entreprise
   * @return un objet de type ContactDto si l'insertion à réussie, null sinon
   */
  public ContactDto insertContact(ContactDto utilisateur);

  /**
   * Vérifie l'existence d'un contact entre un utilisateur et une entreprise.
   * 
   * @param idUtilisateur l'identifiant de l'utilisateur en base de données
   * @param idEntreprise l'identifiant de l'entreprise en base de données
   * @return vrai si le contact existe, faux sinon
   */
  public boolean existeContactForEntrepriseEtUser(int idUtilisateur, int idEntreprise);

  /**
   * Vérifie s'il existe un contact avec un état passé en paramètre pour l'utilisateur dont l'id est
   * passé en paramètre.
   * 
   * @param idUser l'id de l'utilisateur
   * @param etat l'état à vérifier
   * @return true s'il existe un contact avec l'état passé en paramètre pour cet utilisateur
   * @throws FatalException si il y a plus qu'un contact dans cet état
   * 
   */
  public boolean existeContactAvecEtat(int idUser, int etat);


  /**
   * Vérifie s'il y a un contact dans l'état accepté ou qu'un stage est en ordre pour l'utilisateur
   * concerné.
   * 
   * @param idUser l'id de l'utilisateur
   * @return true si il y a un contact accepté ou qu'un stage est en ordre
   */
  public boolean existeContactsAccepteOuEnOrdre(int idUser);

  List<ContactDto> listerContactDiffDeEtat(int idUtilisateur, int etat);


  /**
   * Met à jour l'entreprise du contact dont l'id et le numero de version sont passé en paramètre.
   * 
   * @param contact Un objet de type ContactDto représentant le contact entre un étudiant et une
   *        entreprise
   * @return true si le transfert s'est bien déroulé, throws exception sinon
   */

  public ContactDto updateContact(ContactDto contact);
}
