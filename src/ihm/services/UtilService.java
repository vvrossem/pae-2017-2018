package ihm.services;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import biz.user.UserDto;

public interface UtilService {

  /**
   * Donne l'utilisateur courant.
   * 
   * @param req la requête de la Servlet
   */
  public UserDto getCurrentUser(HttpServletRequest req);

  /**
   * Déconnecte l'utilisateur.
   * 
   * @param req la requête de la Servlet
   * @param resp la réponse de la Servlet
   */
  public void logout(HttpServletRequest req, HttpServletResponse resp);

  /**
   * Démarre une session.
   * 
   * @param req la requête de la Servlet
   * @param resp la réponse de la Servlet
   */
  public void startSession(UserDto user, HttpServletRequest req, HttpServletResponse resp);

  /**
   * Renvoie un code d'erreur au front-end.
   * 
   * @param resp la réponse de la Servlet
   * @param ex l'exception
   * @param code le code de l'erreur
   * @throws IOException une exception
   */
  public void renvoyerCodeErr(HttpServletResponse resp, Exception ex, int code) throws IOException;

  /**
   * Renvoie un code d'erreur ainsi qu'un message au front-end.
   * 
   * @param resp la réponse de la Servlet
   * @param messageErreur le message d'erreur
   * @param code le code de l'erreur
   * @throws IOException une exception
   */
  public void renvoyerCodeErrEtMess(HttpServletResponse resp, String messageErreur, int code)
      throws IOException;

  /**
   * Renvoie un code d'erreur, un message ainsi qu'un objet au front-end.
   * 
   * @param resp la réponse de la Servlet
   * @param ex l'exception
   * @param code le code de l'erreur
   * @param object l'objet
   * @param gens le json
   * @throws IOException une exception
   */
  public void renvoyerCodeErrEtMessEtObj(HttpServletResponse resp, Exception ex, int code,
      Object object, Genson gens) throws IOException;

  /**
   * Vérifie sur l'utilisateur présent dans le front-end correspond à celui présent dans le
   * back-end.
   * 
   * @param req la requête de la Servlet
   * @param resp la réponse de la Servlet
   * @param idStudentFrontEnd l'id de l'étudiant dans le front-end
   * @return true si l'utilisateur correspond sinon false
   * @throws IOException une exception
   */
  public boolean verifStud(HttpServletRequest req, HttpServletResponse resp, int idStudentFrontEnd)
      throws IOException;

  /**
   * Renvoie l'utilisateur courant dans la session.
   * 
   * @param req la requête de la Servlet
   * @param resp la réponse de la Servlet
   * @throws IOException une exception
   */
  public void whoami(HttpServletRequest req, HttpServletResponse resp) throws IOException;

}
