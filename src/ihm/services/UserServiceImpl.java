package ihm.services;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import biz.factory.BizFactory;
import biz.ucc.UserUcc;
import biz.user.UserDto;
import exceptions.OptimisticLockException;
import util.AppContext.DependanceInjection;
import util.Util;

public class UserServiceImpl implements UserService {

  @DependanceInjection
  UtilService utilService;
  @DependanceInjection
  BizFactory factory;
  @DependanceInjection
  UserUcc userUcc;

  private Genson genson = new GensonBuilder().useIndentation(true)
      .useDateFormat(new SimpleDateFormat("dd-MM-yyyy")).create();

  private Genson geUser =
      new GensonBuilder().useIndentation(true).exclude("mdp").exclude("dateInscription").create();

  private Genson geSetMdp =
      new GensonBuilder().useIndentation(true).exclude("dateNaissance").create();

  private Genson geSignIn = new GensonBuilder().useIndentation(true).exclude("mdp").exclude("tel")
      .exclude("email").exclude("dateInscription").exclude("dateNaissance").create();

  private Genson geUserCurrYear = new GensonBuilder().useIndentation(true).exclude("mdp").create();

  @Override
  public void fillChartStudentAsStudent(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.setContentType("application/json");
    resp.getOutputStream().write(genson
        .serialize(
            userUcc.getStudentStats(((UserDto) req.getAttribute("currentUser")).getIdUtilisateur()))
        .getBytes(Charset.forName("UTF-8")));
  }

  @Override
  public void fillChartStudentAsProf(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    UserDto selectedStud = factory.getUserVide();
    selectedStud.setIdUtilisateur(Integer.parseInt(req.getParameter("selectedStud")));
    resp.setContentType("application/json");
    resp.getOutputStream()
        .write(genson.serialize(userUcc.getStudentStats(selectedStud.getIdUtilisateur()))
            .getBytes(Charset.forName("UTF-8")));
  }

  @Override
  public void fillChartTeacher(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.setContentType("application/json");
    resp.getOutputStream()
        .write(genson.serialize(userUcc.getStudentsStats()).getBytes(Charset.forName("UTF-8")));
  }

  @Override
  public void getInfosPersoUser(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    int idUser = Integer.parseInt(req.getParameter("selectedStudId"));
    utilService.verifStud(req, resp, idUser);
    UserDto userDb = userUcc.trouverUtilisateurById(idUser);
    if (userDb != null) {
      resp.setContentType("application/json");
      resp.getOutputStream().write(geUser.serialize(userDb).getBytes(Charset.forName("UTF-8")));
    } else {
      utilService.renvoyerCodeErrEtMess(resp, "Erreur de programmation", 500);
    }
  }

  @Override
  public void setInfosPersoUser(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String json = req.getParameter("user");

    Map<String, Object> mapJson = genson.deserialize(json, Map.class);
    if (!utilService.verifStud(req, resp, Integer.parseInt("" + mapJson.get("idUtilisateur")))) {
      return;
    }

    UserDto userDb;

    try {
      userDb = userUcc.updateInfoUtilisateur(Integer.parseInt("" + mapJson.get("idUtilisateur")),
          Integer.parseInt("" + mapJson.get("numVersion")), "" + mapJson.get("nom"),
          "" + mapJson.get("prenom"), Util.jsonToLocalDate(json, "dateNaissance"),
          "" + mapJson.get("email"), "" + mapJson.get("tel"));

      sendUserToFrontend(req, resp, userDb);
    } catch (OptimisticLockException ex) {
      userDb = (UserDto) ex.getObjetEnDb();
      utilService.renvoyerCodeErrEtMessEtObj(resp, ex, 409, userDb, geUser);
    }
  }

  @Override
  public void setMdpUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String userId = req.getParameter("userId");
    String userNumVersion = req.getParameter("userNumVersion");
    String mdpActuel = req.getParameter("mdpActuel");
    String newMdp1 = req.getParameter("nouveauMdp1");
    String newMdp2 = req.getParameter("nouveauMdp2");
    if (!utilService.verifStud(req, resp, Integer.parseInt(userId))) {
      return;
    }

    UserDto userDb;

    try {
      userDb = userUcc.updateMdpUtilisateur(Integer.parseInt(userId),
          Integer.parseInt(userNumVersion), mdpActuel, newMdp1, newMdp2);
      sendUserToFrontend(req, resp, userDb);

    } catch (OptimisticLockException ex) {
      userDb = (UserDto) ex.getObjetEnDb();
      utilService.renvoyerCodeErrEtMessEtObj(resp, ex, 409, userDb, geUser);
    }
  }

  private void sendUserToFrontend(HttpServletRequest req, HttpServletResponse resp, UserDto userDb)
      throws IOException {
    if (userDb != null) {
      resp.setContentType("application/json");
      resp.getOutputStream().write(geUser.serialize(userDb).getBytes(Charset.forName("UTF-8")));
    } else {
      utilService.renvoyerCodeErrEtMess(resp, "Erreur de programmation", 500);
    }
  }

  @Override
  public void signin(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String json = req.getParameter("userAVerif");
    Map<String, String> mapJson = genson.deserialize(json, Map.class);

    // Si l'utilisateur peut se connecter
    UserDto userDb = userUcc.seConnecter(mapJson.get("pseudo"), mapJson.get("mdp"));

    if (userDb != null) {
      json = geSignIn.serialize(userDb);
      utilService.startSession(userDb, req, resp);
      resp.setContentType("application/json");
      resp.getOutputStream().write(json.getBytes(Charset.forName("UTF-8")));
    } else {
      utilService.renvoyerCodeErrEtMess(resp,
          "Le nom d'utilisateur ou le mot de passe est incorrect", 401);
    }
  }

  @Override
  public void signup(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String json = req.getParameter("newUser");
    UserDto userAVerif = factory.getUserVide();

    geSetMdp.deserializeInto(json, userAVerif);

    userAVerif.setDateNaissance(Util.jsonToLocalDate(json, "dateNaissance"));

    UserDto userDb = userUcc.sinscrire(userAVerif);
    if (userDb != null) {
      Genson gen = new GensonBuilder().useIndentation(true).exclude("mdp").exclude("tel")
          .exclude("email").exclude("dateInscription").exclude("dateNaissance").create();
      json = gen.serialize(userDb);

      utilService.startSession(userDb, req, resp);
      resp.setContentType("application/json");
      resp.getOutputStream().write(json.getBytes(Charset.forName("UTF-8")));
    } else {
      utilService.renvoyerCodeErrEtMess(resp, "Erreur de programmation", 500);
    }
  }

  @Override
  public void visualiserStudCurYear(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    List<UserDto> users = userUcc.visualiserUsersAnneeCourante();
    if (users != null) {
      resp.setContentType("application/json");
      resp.getOutputStream()
          .write(geUserCurrYear.serialize(users).getBytes(Charset.forName("UTF-8")));
    } else {
      utilService.renvoyerCodeErrEtMess(resp, "Erreur de programmation", 500);
    }
  }
}
