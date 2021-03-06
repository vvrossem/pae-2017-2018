package biz.ucc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import biz.contact.ContactDto;
import biz.entreprise.EntrepriseBiz;
import biz.entreprise.EntrepriseDto;
import biz.pdc.PersonneContactDto;
import biz.stage.StageDto;
import biz.user.UserBiz;
import dal.dao.ContactDao;
import dal.dao.EntrepriseDao;
import dal.dao.PersonneContactDao;
import dal.dao.StageDao;
import dal.services.DalServices;
import exceptions.BizException;
import util.AppContext.DependanceInjection;
import util.MonLogger;
import util.Util;

public class EntrepriseUccImpl implements EntrepriseUcc {

  @DependanceInjection
  private EntrepriseDao entrepriseDao;

  @DependanceInjection
  private ContactDao contactDao;

  @DependanceInjection
  private StageDao stageDao;

  @DependanceInjection
  private PersonneContactDao personneContactDao;

  @DependanceInjection
  private DalServices dalServices;

  @DependanceInjection
  private MonLogger monLogger;

  @Override
  public List<EntrepriseDto> visualiserEntreprises() {
    try {
      dalServices.startTransaction();
      return entrepriseDao.getAllEntreprises();
    } catch (Exception ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      dalServices.rollback();
      throw ex;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public List<HashMap<String, Object>> visualiserEntreprisesAsProf(String anneeAcademique) {
    Util.checkFormatString(anneeAcademique, UserBiz.MAX_CARACTERES_ANNEE_ACADEMIQUE,
        UserBiz.REGEX_ANNEE_ACADEMIQUE, "Le format de l'année académique est incorrect");

    try {
      dalServices.startTransaction();
      return entrepriseDao.getAllEntreprisesWithNumberOfStudents(anneeAcademique);
    } catch (Exception ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      dalServices.rollback();
      throw ex;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public EntrepriseDto getEntreprise(int idEntreprise) {

    try {
      dalServices.startTransaction();
      return entrepriseDao.getEntreprise(idEntreprise);
    } catch (Exception ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      dalServices.rollback();
      throw ex;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public EntrepriseDto blacklistEntreprise(int idEntreprise, int entNumVersion) {

    try {
      dalServices.startTransaction();
      EntrepriseDto entrepriseDb = entrepriseDao.getEntreprise(idEntreprise);
      if (entrepriseDb == null) {
        throw new BizException("L'entreprise n'existe pas");
      }
      entrepriseDb.setNumVersion(entNumVersion);
      entrepriseDb.setEstBlackListe(true);
      return entrepriseDao.updateEntreprise(entrepriseDb);
    } catch (Exception ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      dalServices.rollback();
      throw ex;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public EntrepriseDto insertEntreprise(EntrepriseDto entreprise) {

    Util.checkFormatString(entreprise.getDenomination(), EntrepriseBiz.MAX_CARACTERES_DENOMINATION,
        "Le format de la dénomination est incorrect");
    Util.checkFormatString(entreprise.getAdresse(), EntrepriseBiz.MAX_CARACTERES_ADRESSE,
        "Le format de la rue est incorrect");
    Util.checkFormatString(entreprise.getCodePostal(), EntrepriseBiz.MAX_CARACTERES_CODE_POSTAL,
        "Le format du code postal est incorrect");
    Util.checkFormatString(entreprise.getVille(), EntrepriseBiz.MAX_CARACTERES_VILLE,
        "Le format de la ville est incorrect");
    // Le champ tel peut être vide
    if (entreprise.getTel() != null && !entreprise.getTel().equals("")) {
      Util.checkFormatString(entreprise.getTel(), EntrepriseBiz.MAX_CARACTERES_TEL,
          EntrepriseBiz.REGEX_TEL, "Le format du numero de téléphone est incorrect");
    }
    // Le champ email peut être vide
    if (entreprise.getEmail() != null && !entreprise.getEmail().equals("")) {
      Util.checkFormatString(entreprise.getEmail(), EntrepriseBiz.MAX_CARACTERES_EMAIL,
          EntrepriseBiz.REGEX_EMAIL, "Le format de l'email est incorrect");
    }

    try {
      dalServices.startTransaction();
      if (entrepriseDao.denominationEntrepriseExiste(entreprise.getDenomination())) {
        throw new BizException("La dénomination de l'entreprise existe déjà !");
      }
      entreprise.setEstBlackListe(false);
      entreprise.setEstSupprime(false);
      entreprise.setNumVersion(1);
      return entrepriseDao.insertEntreprise(entreprise);
    } catch (Exception ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      dalServices.rollback();
      throw ex;
    } finally {
      dalServices.commitTransaction();
    }
  }

  @Override
  public List<String> getAnneesAcademiques() {
    List<String> anneesAcademiques;
    try {
      dalServices.startTransaction();
      anneesAcademiques = entrepriseDao.getAnneesAcademiques();
    } catch (Exception ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      dalServices.rollback();
      throw ex;
    } finally {
      dalServices.commitTransaction();
    }
    if (anneesAcademiques.isEmpty()) {
      anneesAcademiques.add(Util.localDateToYear(LocalDate.now()));
    }
    return anneesAcademiques;
  }

  @Override
  public EntrepriseDto fusionnerEntreprise1AvecEntreprise2(int idEntreprise1, int numVersEntr1,
      int idEntreprise2, int numVersEntr2) {
    EntrepriseDto entreprise1;
    EntrepriseDto entreprise2;
    try {
      dalServices.startTransaction();
      // get entreprise 1 et 2
      entreprise1 = entrepriseDao.getEntreprise(idEntreprise1);
      entreprise2 = entrepriseDao.getEntreprise(idEntreprise2);
      if (entreprise1 == null || entreprise2 == null) {
        throw new BizException("entreprise inexistant");
      }
      entreprise1.setNumVersion(numVersEntr1);
      entreprise2.setNumVersion(numVersEntr2);
      if (entreprise1.getEstSupprime()) {
        throw new BizException("L'entreprise 1 est déjà supprimée");
      }
      if (entreprise2.getEstSupprime()) {
        throw new BizException("L'entreprise 2 est déjà supprimée");
      }

      // ************************** TRANSFERT stages,pdc et contacts*********************
      List<StageDto> stagesEntreprise1 = stageDao.listerStagesPourEntreprise(idEntreprise1);
      for (StageDto stageDto : stagesEntreprise1) {
        stageDto.setEntreprise(idEntreprise2);
        stageDao.updateStage(stageDto);
      }
      List<PersonneContactDto> pdcEntreprise1 =
          personneContactDao.listerPersonnesContactByIdEntreprise(idEntreprise1);
      for (PersonneContactDto pdc : pdcEntreprise1) {
        pdc.setEntreprise(idEntreprise2);
        personneContactDao.updatePersonneContact(pdc);
      }
      List<ContactDto> contactsEntreprise1 = contactDao.listerContactsPourEntreprise(idEntreprise1);
      for (ContactDto contactDto : contactsEntreprise1) {
        contactDto.setEntreprise(idEntreprise2);
        contactDao.updateContact(contactDto);
      }
      // *********************************************************************************
      entreprise1.setEstSupprime(true);
      entrepriseDao.updateEntreprise(entreprise1);
    } catch (Exception ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      dalServices.rollback();
      throw ex;
    } finally {
      dalServices.commitTransaction();
    }
    return entreprise2;
  }

}
