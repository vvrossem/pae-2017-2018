package ihm.services;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import biz.contact.ContactDto;
import biz.factory.BizFactory;
import biz.ucc.ContactUcc;
import biz.user.UserDto;
import exceptions.OptimisticLockException;
import util.AppContext;
import util.AppContext.DependanceInjection;

public class ContactServiceImpl implements ContactService {

  @DependanceInjection
  UtilService utilService;
  @DependanceInjection
  BizFactory factory;
  @DependanceInjection
  ContactUcc contactUcc;
  @DependanceInjection
  AppContext appContext;

  private Genson genson = new GensonBuilder().useIndentation(true)
      .useDateFormat(new SimpleDateFormat("dd-MM-yyyy")).create();

  private Genson geUser =
      new GensonBuilder().useIndentation(true).exclude("mdp").exclude("dateInscription").create();

  private Genson geUpdateContact = new GensonBuilder().exclude("entrepriseDto")
      .exclude("personneContactDto").exclude("utilisateurDto").useIndentation(true).create();

  @Override
  public void creerContactAsStud(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String json = req.getParameter("selectedStud");
    Map<String, Object> jsonMap = genson.deserialize(json, Map.class);
    if (!utilService.verifStud(req, resp, Integer.parseInt("" + jsonMap.get("idUtilisateur")))) {
      return;
    }


    json = req.getParameter("newContact");
    Map<String, Object> jsonSt = genson.deserialize(json, Map.class);
    ContactDto contactDb;
    try {
      contactDb =
          contactUcc.creerContactUtilisateur(Integer.parseInt("" + jsonMap.get("idUtilisateur")),
              Integer.parseInt("" + jsonMap.get("numVersion")),
              Integer.parseInt("" + jsonSt.get("idEntreprise")), jsonSt.get("idPersonneContact"));

    } catch (OptimisticLockException ex) {
      utilService.renvoyerCodeErrEtMessEtObj(resp, ex, 409, ex.getObjetEnDb(), genson);
      return;
    }

    if (contactDb != null) {
      json = geUser.serialize(contactDb);
      resp.setContentType("application/json");
      resp.getOutputStream().write(json.getBytes(Charset.forName("UTF-8")));
    } else {
      utilService.renvoyerCodeErrEtMess(resp, "Erreur de programmation", 500);
    }
  }

  @Override
  public void creerContactAsProf(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String json = req.getParameter("newContact");
    Map<String, Object> jsonMap = genson.deserialize(json, Map.class);

    String user = req.getParameter("selectedStud");
    Map<String, Object> jsonMapUser = genson.deserialize(user, Map.class);

    ContactDto contactDb;
    try {
      contactDb = contactUcc.creerContactUtilisateur(
          Integer.parseInt("" + jsonMapUser.get("idUtilisateur")),
          Integer.parseInt("" + jsonMapUser.get("numVersion")),
          Integer.parseInt("" + jsonMap.get("idEntreprise")), jsonMap.get("idPersonneContact"));

    } catch (OptimisticLockException ex) {
      utilService.renvoyerCodeErr(resp, ex, 409);
      return;
    }
    if (contactDb != null) {
      json = genson.serialize(contactDb);
      resp.setContentType("application/json");
      resp.getOutputStream().write(json.getBytes(Charset.forName("UTF-8")));
    } else {
      utilService.renvoyerCodeErrEtMess(resp, "Erreur de programmation", 500);
    }
  }


  @Override
  public void modifierEtatContactsAsStud(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String json = req.getParameter("selectedStud");
    Map<String, Object> jsonMap = genson.deserialize(json, Map.class);
    if (!utilService.verifStud(req, resp, Integer.parseInt("" + jsonMap.get("idUtilisateur")))) {
      return;
    }
    modifierEtatContacts(req, resp, jsonMap);
  }

  @Override
  public void modifierEtatContactsAsProf(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String json = req.getParameter("selectedStud");
    Map<String, Object> jsonMapUser = genson.deserialize(json, Map.class);
    modifierEtatContacts(req, resp, jsonMapUser);
  }

  private void modifierEtatContacts(HttpServletRequest req, HttpServletResponse resp,
      Map<String, Object> jsonMap) throws IOException {
    String json = req.getParameter("contacts");

    ContactDto[] tabContacts = (ContactDto[]) geUpdateContact.deserialize(json,
        appContext.getClassValueProp("[Lbiz.contact.ContactDto"));


    UserDto userDb;
    try {
      userDb = contactUcc.updateEtatContacts(Arrays.asList(tabContacts),
          Integer.parseInt("" + jsonMap.get("idUtilisateur")),
          Integer.parseInt("" + jsonMap.get("numVersion")));
      if (userDb == null) {
        utilService.renvoyerCodeErrEtMess(resp, "Erreur de programmation", 500);
      }
    } catch (OptimisticLockException ex) {
      Object object = ex.getObjetEnDb();
      if (object.getClass() == appContext.getClassValueProp("biz.user.UserDto")) {
        userDb = (UserDto) ex.getObjetEnDb();
        utilService.renvoyerCodeErrEtMessEtObj(resp, ex, 409, userDb, genson);
      } else {
        utilService.renvoyerCodeErr(resp, ex, 409);
      }
      return;
    }
    json = genson.serialize(userDb);
    resp.setContentType("application/json");
    resp.getOutputStream().write(json.getBytes(Charset.forName("UTF-8")));
  }

  @Override
  public void visualiserContacts(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    List<ContactDto> contacts = contactUcc
        .listerContactsUtilisateur(((UserDto) req.getAttribute("currentUser")).getIdUtilisateur());
    if (contacts != null) {
      resp.setContentType("application/json");
      resp.getOutputStream().write(genson.serialize(contacts).getBytes(Charset.forName("UTF-8")));
    } else {
      utilService.renvoyerCodeErrEtMess(resp, "Erreur de programmation", 500);
    }
  }

  @Override
  public void visualiserContactsAsProf(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    List<ContactDto> contacts =
        contactUcc.listerContactsUtilisateur(Integer.parseInt(req.getParameter("selectedStud")));
    if (contacts != null) {
      resp.setContentType("application/json");
      resp.getOutputStream().write(genson.serialize(contacts).getBytes(Charset.forName("UTF-8")));
    } else {
      utilService.renvoyerCodeErrEtMess(resp, "Erreur de programmation", 500);
    }
  }
}
