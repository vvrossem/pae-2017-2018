package biz.contact;

import biz.entreprise.EntrepriseDto;
import biz.pdc.PersonneContactDto;
import biz.user.UserDto;

public interface ContactDto {

  /**
   * Rempli le contact avec les différents paramètres.
   * 
   * @param idContact le numéro de contact
   * @param utilisateur le numéro d'utilisateur
   * @param utilisateurDto un objet UserDto représentant l'utilisateur
   * @param entreprise le numéro d'entreprise
   * @param entrepriseDto un objet de type EntrepriseDto représentant l'entreprise
   * @param personneContact le numéro de la personne de contact
   * @param personneContactDto un objet de type PersonneContactDto représentant la personne de
   *        contact
   * @param etat le numéro de l'état du contact entre l'entreprise et l'utilisateur
   * @param anneeAcademique une chaîne de caractère représentant l'année académique
   * @param numVersion le numéro de version du contact
   */
  public void fillContact(int idContact, int utilisateur, UserDto utilisateurDto, int entreprise,
      EntrepriseDto entrepriseDto, Integer personneContact, PersonneContactDto personneContactDto,
      int etat, String anneeAcademique, int numVersion);

  // getters
  public int getIdContact();

  public int getUtilisateur();

  public UserDto getUtilisateurDto();

  public int getEntreprise();

  public EntrepriseDto getEntrepriseDto();

  public Integer getPersonneContact();

  public PersonneContactDto getPersonneContactDto();

  public int getEtat();

  public String getAnneeAcademique();

  public int getNumVersion();

  // setters
  public void setIdContact(int idContact);

  public void setUtilisateur(int utilisateur);

  public void setUtilisateurDto(UserDto utilisateurDto);

  public void setEntreprise(int entreprise);

  public void setEntrepriseDto(EntrepriseDto entrepriseDto);

  public void setPersonneContact(Integer personneContact);

  public void setPersonneContactDto(PersonneContactDto personneContactDto);

  public void setEtat(int etat);

  public void setAnneeAcademique(String anneeAcademique);

  public void setNumVersion(int numVersion);

}
