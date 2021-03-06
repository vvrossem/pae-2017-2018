package dal.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import biz.factory.BizFactory;
import biz.user.UserDto;
import exceptions.FatalException;
import util.AppContext.DependanceInjection;
import util.Util;

class MockUserDaoImpl implements UserDao {

  // private boolean getUser;

  @DependanceInjection
  private BizFactory factory;

  /*
   * public MockUserDaoImpl(boolean getUser) { this.getUser = getUser; }
   */

  @Override
  public UserDto getUser(String pseudo) {
    if (pseudo == "@@@@@") {
      throw new FatalException();
    }

    if (!pseudo.equals("blehmann")) {
      return null;
    }
    String motDePasse = "azerty";
    UserDto user = factory.getUserVide();
    user.setPseudo(pseudo);
    user.setNumVersion(0);
    user.setIdUtilisateur(1);
    user.setMdp(BCrypt.hashpw(motDePasse, BCrypt.gensalt()));
    return user;

  }

  @Override
  public UserDto insertUser(UserDto user) {
    return user;
  }

  @Override
  public boolean pseudoUserExiste(String pseudo) {
    return pseudo.equals("pierre");
  }

  @Override
  public boolean emailUserExiste(String email) {
    return email.equals("pierre@vinci.be");
  }

  @Override
  public UserDto getUser(int idUtilisateur) {
    UserDto userDto = factory.getUserVide();
    if (idUtilisateur == -1) {
      throw new FatalException();
    }
    if (idUtilisateur == 99999) {
      return null;
    }
    if (idUtilisateur == 100) {
      userDto.setIdUtilisateur(100);
      userDto.setNumVersion(100);
      userDto.setAnneeAcademique(Util.localDateToYear(LocalDate.now()));
      userDto.setMdp("$2a$10$AvfajQ3KJrIwtaRL893v6eVmNKqCTjpg1wKU8DiwsLnN5wUKW.Iz6");
      return userDto;
    }

    // pour testUpdateEtatContacts4 dans ContactUccImplTest et confirmDataStage19 dans
    // StageUccImplTest
    if (idUtilisateur == 777) {
      userDto.setAnneeAcademique("2017-2018");
      userDto.setEstAdmin(true);
      return userDto;
    }
    // pour testUpdateEtatContacts5 dans ContactUccImplTest et confirmDataStage20 dans
    // StageUccImplTest
    if (idUtilisateur == 778) {
      userDto.setAnneeAcademique("2010-2011");
      return userDto;
    }
    // pour testUpdateEtatContacts6 et 9 dansContactUccImplTest
    if (idUtilisateur == 779 || idUtilisateur == 780 || idUtilisateur == 781 || idUtilisateur == 782
        || idUtilisateur == 800 || idUtilisateur == 801 || idUtilisateur == 802) {
      userDto.setNumVersion(0);
      userDto.setAnneeAcademique("2017-2018");
      return userDto;
    }
    userDto = factory.getUserVide();
    userDto.setIdUtilisateur(idUtilisateur);
    userDto.setNumVersion(idUtilisateur);
    userDto.setAnneeAcademique("2017-2018");
    return userDto;
  }

  @Override
  public UserDto updateUser(UserDto user) {
    return user;
  }


  @Override
  public HashMap<String, Integer> getStudentsStats(String anneeAcademique) {
    if (anneeAcademique == null) {
      throw new FatalException();
    }

    return new HashMap<>();
  }



  @Override
  public List<UserDto> getAllUsers(String anneeAcademique) {
    if (anneeAcademique == null) {
      throw new FatalException();
    }
    List<UserDto> users = new ArrayList<>();
    return users;
  }



  @Override
  public HashMap<String, Integer> getStudentStats(int idUser) {
    if (idUser == -2) {
      throw new FatalException();
    }
    return new HashMap<>();
  }

  /*
   * public boolean verify(boolean getUser) { return this.getUser == getUser; }
   */

}
