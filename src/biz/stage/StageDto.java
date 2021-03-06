package biz.stage;

import java.time.LocalDate;
import biz.entreprise.EntrepriseDto;
import biz.pdc.PersonneContactDto;
import biz.user.UserDto;

public interface StageDto {
  /**
   * Rempli le stage avec les différentes propriétés.
   * 
   * @param idStage le numéro de stage
   * @param anneeAcademique une chaîne de caractère représentant l'année académique du stage
   * @param dateSignature Un objet de type LocalDate représentant la date de signature
   * @param adresse une chaîne de caractère représentant représentant la rue du stage
   * @param numero une chaîne de caractère représentant le numéro de la rue du stage
   * @param boite une chaîne de caractère représentant la boîte de l'entreprise
   * @param codePostal une chaîne de caractère représentant représentant le code postal de
   *        l'entreprise
   * @param ville une chaîne de caractère représentant la ville de l'entreprise
   * @param responsable le numéro du responsable de l'entreprise
   * @param responsableDto un objet de type PersonneContactDto représentant le responsable
   * @param entreprise le numéro de l'entreprise lié au stage
   * @param entrepriseDto un objet de type EntrepriseDto représentant l'entreprise liéeau stage
   * @param utilisateur le numéro de l'utilisateur lié au stage
   * @param utilisateurDto un objet de type UserDto représentant l'utilisateur lié au stage
   * @param numVersion le numéro de version du stage
   */
  public void fillStage(int idStage, String anneeAcademique, LocalDate dateSignature,
      String adresse, String numero, String boite, String codePostal, String ville, int responsable,
      PersonneContactDto responsableDto, int entreprise, EntrepriseDto entrepriseDto,
      int utilisateur, UserDto utilisateurDto, int numVersion);

  // Getters
  public int getIdStage();

  public String getAnneeAcademique();

  public LocalDate getDateSignature();

  public String getAdresse();

  public String getNumero();

  public String getBoite();

  public String getCodePostal();

  public String getVille();

  public int getResponsable();

  public PersonneContactDto getResponsableDto();

  public int getEntreprise();

  public EntrepriseDto getEntrepriseDto();

  public int getUtilisateur();

  public UserDto getUtilisateurDto();

  public int getNumVersion();

  // Setters
  public void setIdStage(int idStage);

  public void setAnneeAcademique(String anneeAcademique);

  public void setDateSignature(LocalDate dateSignature);

  public void setAdresse(String adresse);

  public void setNumero(String numero);

  public void setBoite(String boite);

  public void setCodePostal(String codePostal);

  public void setVille(String ville);

  public void setResponsable(int responsable);

  public void setResponsableDto(PersonneContactDto responsableDto);

  public void setEntreprise(int entreprise);

  public void setEntrepriseDto(EntrepriseDto entrepriseDto);

  public void setUtilisateur(int utilisateur);

  public void setUtilisateurDto(UserDto utilisateurDto);

  public void setNumVersion(int numVersion);

}
