package dal.dao;

import java.util.HashMap;
import java.util.List;
import biz.entreprise.EntrepriseDto;

public interface EntrepriseDao {

  /**
   * Récupère toutes les entreprise présentent dans la base de données dont l'attribut
   * est_supprime=false.
   * 
   * @return une List de EntrepriseDto
   */
  List<EntrepriseDto> getAllEntreprises();

  /**
   * Récupère toutes les entreprises présentes dans la base de données dont l'attribut
   * est_supprime=false ainsi que le nombre d'étudiants ayant en stage en fonction de l'année
   * académique donnée.
   * 
   * @param anneeAcademique le filtre pour le nombre d'étudiants
   * @return une List contenant les entreprises et leur nombre d'étudiants en stage
   */
  List<HashMap<String, Object>> getAllEntreprisesWithNumberOfStudents(String anneeAcademique);

  /**
   * Récupère une entreprise via son identifiant.
   * 
   * @param idEntreprise l'identifiant de l'entreprise dans la base de données
   * @return un objet de type EntrepriseDto si 'idEntreprise' existe, null sinon
   */
  EntrepriseDto getEntreprise(int idEntreprise);

  /**
   * Insere une entreprise dans la db.
   * 
   * @param entreprise l'entreprise à inserer
   * @return l'entreprise inséree si l'insertion a réussie, null sinon
   */
  EntrepriseDto insertEntreprise(EntrepriseDto entreprise);

  /**
   * Vérifie si il existe une entreprise avec la denomination passée en paramètre.
   * 
   * @param denomination la dénomination de l'entreprise
   * @return true si une entreprise possède cette dénomination,false sinon
   */
  boolean denominationEntrepriseExiste(String denomination);

  /**
   * Récupère les années académiques des étudiants présents dans la base de données.
   * 
   * @return une List sans doublon d'années académiques
   */
  List<String> getAnneesAcademiques();

  /**
   * Modifie l'attribut 'est_supprime' de l'entreprise dont l'id est passé en paramètre à true.
   * 
   * @param entreprise un objet de type EntrepriseDto représentant l'entreprise
   * @return true si l'update s'est bien fait
   * @throws Exception si l'update ne s'est pas fait
   */
  public EntrepriseDto updateEntreprise(EntrepriseDto entreprise);


}
