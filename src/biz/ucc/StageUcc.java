package biz.ucc;

import biz.stage.StageDto;



public interface StageUcc {

  StageDto confirmDataStage(StageDto stage, int idContact, int contactNumVersion, int idUser,
      int userNumVersion);

  /**
   * Récupère et renvoie un stage pour l'utilisateur donné.
   * 
   * @param idUtilisateur l'id de l'utilisateur concerné
   * @return un objet de type StageDto rempli ou vide
   */
  StageDto visualiserStage(int idUtilisateur);
}
