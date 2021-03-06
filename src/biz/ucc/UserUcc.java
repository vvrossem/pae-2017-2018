package biz.ucc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import biz.user.UserDto;

public interface UserUcc {

  /**
   * Connecte l'utilisateur en vérifiant son pseudo et son mot de passe.
   * 
   * @param pseudo le pseudo de l'utilisateur
   * @param mdp le mot de passe de l'utilisateur
   * @return un UserDto si le mot de passe et le pseudo correspondent, null sinon
   */

  UserDto seConnecter(String pseudo, String mdp);

  /**
   * Inscrit un nouvel utilisateur en vérifiant les différents attributs de l'utilisateur.
   * 
   * @param user le nouvel utilisateur à vérifier et à inscrire
   * @return un objet de type UserDto si l'inscription en base de données a réussie, null sinon
   */
  UserDto sinscrire(UserDto user);

  /**
   * Séléctionne un utilisateur en base de données avec son identifiant.
   *
   * @param userId l'utilisateur a trouvé dans la base de données
   * @return un objet de type UserDto si l'utilisateur est en base de données, null sinon
   */
  UserDto trouverUtilisateurById(int userId);

  /**
   * Met à jour les données de l'utilisateur sauf le mot de passe.
   * 
   * @param idUser l'id de l'utilisateur
   * @param userNumVersion le numéro de version de l'utilisateur
   * @param nom le nom de l'utilisateur
   * @param prenom le prénom de l'utilisateur
   * @param dateNaissance la date de naissance de l'utilisateur
   * @param email l'email de l'utilisateur
   * @param tel le numéro de téléphone de l'utilisateur
   * @return un objet de type UserDto si la mise à jour à réussie, null sinon
   */
  UserDto updateInfoUtilisateur(int idUser, int userNumVersion, String nom, String prenom,
      LocalDate dateNaissance, String email, String tel);

  /**
   * Met à jour le mot de passe d'un utilisateur.
   * 
   * @param idUser l'id de l'utilisateur
   * @param userNumVersion le numéro de version de l'utilisateur
   * @param mdpActuel le mot de passe actuel de l'utilisateur
   * @param newMotDePasse1 le nouveau mot de passe de l'utilisateur
   * @param newMotDePasse2 le nouveau mot de passe de l'utilisateur
   * @return vrai si le mot de passe à été changé en base de données, faux sinon
   */
  UserDto updateMdpUtilisateur(int idUser, int userNumVersion, String mdpActuel,
      String newMotDePasse1, String newMotDePasse2);

  /**
   * Récupère et renvoie une liste d'utilisateurs pour l'année académique en cours.
   * 
   * @return une liste de UsersDto
   */
  List<UserDto> visualiserUsersAnneeCourante();

  HashMap<String, Integer> getStudentsStats();

  HashMap<String, Integer> getStudentStats(int idUser);


}
