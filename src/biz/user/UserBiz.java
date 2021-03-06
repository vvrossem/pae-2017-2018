package biz.user;

public interface UserBiz extends UserDto {

  final int MAX_CARACTERES_PSEUDO = 30;
  final int MAX_CARACTERES_MDP = 30;
  final int MAX_CARACTERES_NOM = 50;
  final int MAX_CARACTERES_PRENOM = 30;
  final int MAX_CARACTERES_TEL = 15;
  final int MAX_CARACTERES_EMAIL = 100;
  final int MAX_CARACTERES_ANNEE_ACADEMIQUE = 9;
  final String REGEX_EMAIL = ".+@.*vinci\\..+";
  final String REGEX_TEL = "[0-9]+|\\+[0-9]+";
  final String REGEX_ANNEE_ACADEMIQUE = "^20[0-9]{2}-20[0-9]{2}$";

  enum EtatPlusAvance {
    AUCUN_CONTACT("aucunContact", 0), REFUSE("refuse", 1), INITIE("initie", 2), ACCEPTE("accepte",
        3), STAGE_EN_ORDRE("stageEnOrdre", 4);


    private final String nomEtat;
    private final int numEtat;

    @Override
    public String toString() {
      return this.nomEtat;
    }

    EtatPlusAvance(String nomEtat, int numEtat) {
      this.nomEtat = nomEtat;
      this.numEtat = numEtat;
    }

    public String getNomEtat() {
      return nomEtat;
    }

    public int getNumEtat() {
      return numEtat;
    }
  }

  /**
   * Vérifie si un mot de passe (non hashé) correspond à celui (hashé) stocké dans la base de
   * données.
   * 
   * @param mdp un mot de passe non hashé tel qu'introduit par un utilisateur
   * 
   * @return true si le mot de passe correspond à celui hashé dans la BD, false sinon
   */
  boolean verifierMotDePasse(String mdp);

}
