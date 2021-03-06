package biz.stage;

import java.time.LocalDate;
import biz.entreprise.EntrepriseDto;
import biz.pdc.PersonneContactDto;
import biz.user.UserDto;

public class StageImpl implements StageBiz {

  private int idStage;
  private String anneeAcademique;
  private LocalDate dateSignature;
  private String adresse;
  private String numero;
  private String boite;
  private String codePostal;
  private String ville;
  private int responsable;
  private PersonneContactDto responsableDto;
  private int entreprise;
  private EntrepriseDto entrepriseDto;
  private int utilisateur;
  private UserDto utilisateurDto;
  private int numVersion;


  @Override
  public void fillStage(int idStage, String anneeAcademique, LocalDate dateSignature,
      String adresse, String numero, String boite, String codePostal, String ville, int responsable,
      PersonneContactDto responsableDto, int entreprise, EntrepriseDto entrepriseDto,
      int utilisateur, UserDto utilisateurDto, int numVersion) {
    this.idStage = idStage;
    this.anneeAcademique = anneeAcademique;
    this.dateSignature = dateSignature;
    this.adresse = adresse;
    this.numero = numero;
    this.boite = boite;
    this.codePostal = codePostal;
    this.ville = ville;
    this.responsable = responsable;
    this.responsableDto = responsableDto;
    this.entreprise = entreprise;
    this.entrepriseDto = entrepriseDto;
    this.utilisateur = utilisateur;
    this.utilisateurDto = utilisateurDto;
    this.numVersion = numVersion;
  }

  @Override
  public String toString() {
    return "StageImpl [idStage=" + idStage + ", anneeAcademique=" + anneeAcademique
        + ", dateSignature=" + dateSignature + ", adresse=" + adresse + ", numero=" + numero
        + ", boite=" + boite + ", codePostal=" + codePostal + ", ville=" + ville + ", responsable="
        + responsable + ", responsableDto=" + responsableDto + ", entreprise=" + entreprise
        + ", entrepriseDto=" + entrepriseDto + ", utilisateur=" + utilisateur + ", utilisateurDto="
        + utilisateurDto + ", numVersion=" + numVersion + "]";
  }

  public int getIdStage() {
    return idStage;
  }

  public String getAnneeAcademique() {
    return anneeAcademique;
  }

  public LocalDate getDateSignature() {
    return dateSignature;
  }

  public String getAdresse() {
    return adresse;
  }

  public String getNumero() {
    return numero;
  }

  public String getBoite() {
    return boite;
  }

  public String getCodePostal() {
    return codePostal;
  }

  public String getVille() {
    return ville;
  }

  public int getResponsable() {
    return responsable;
  }

  public PersonneContactDto getResponsableDto() {
    return responsableDto;
  }

  public int getEntreprise() {
    return entreprise;
  }

  public EntrepriseDto getEntrepriseDto() {
    return entrepriseDto;
  }

  public int getUtilisateur() {
    return utilisateur;
  }

  public UserDto getUtilisateurDto() {
    return utilisateurDto;
  }

  public int getNumVersion() {
    return numVersion;
  }

  public void setIdStage(int idStage) {
    this.idStage = idStage;
  }

  public void setAnneeAcademique(String anneeAcademique) {
    this.anneeAcademique = anneeAcademique;
  }

  public void setDateSignature(LocalDate dateSignature) {
    this.dateSignature = dateSignature;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public void setBoite(String boite) {
    this.boite = boite;
  }

  public void setCodePostal(String codePostal) {
    this.codePostal = codePostal;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public void setResponsable(int responsable) {
    this.responsable = responsable;
  }

  public void setResponsableDto(PersonneContactDto responsableDto) {
    this.responsableDto = responsableDto;
  }

  public void setEntreprise(int entreprise) {
    this.entreprise = entreprise;
  }

  public void setEntrepriseDto(EntrepriseDto entrepriseDto) {
    this.entrepriseDto = entrepriseDto;
  }

  public void setUtilisateur(int utilisateur) {
    this.utilisateur = utilisateur;
  }

  public void setUtilisateurDto(UserDto utilisateurDto) {
    this.utilisateurDto = utilisateurDto;
  }

  public void setNumVersion(int numVersion) {
    this.numVersion = numVersion;
  }
}
