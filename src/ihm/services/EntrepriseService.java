package ihm.services;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface EntrepriseService {

  /**
   * Blackliste une entreprise.
   * 
   * @param req la requête de la Servlet
   * @param resp la réponse de la Servlet
   * @throws IOException une exception
   */
  public void blacklistEntreprise(HttpServletRequest req, HttpServletResponse resp)
      throws IOException;

  /**
   * Donne les années académiques.
   * 
   * @param req la requête de la Servlet
   * @param resp la réponse de la Servlet
   * @throws IOException une exception
   */
  public void fillSelectAnneeAcademique(HttpServletRequest req, HttpServletResponse resp)
      throws IOException;

  /**
   * Permet d'obtenir une entreprise.
   * 
   * @param req la requête de la Servlet
   * @param resp la réponse de la Servlet
   * @throws IOException une exception
   */
  public void getEntreprise(HttpServletRequest req, HttpServletResponse resp) throws IOException;

  /**
   * Insère une entreprise.
   * 
   * @param req la requête de la Servlet
   * @param resp la réponse de la Servlet
   * @throws IOException une exception
   */
  public void insererEntreprise(HttpServletRequest req, HttpServletResponse resp)
      throws IOException;

  /**
   * Permet de visualiser des entreprises.
   * 
   * @param req la requête de la Servlet
   * @param resp la réponse de la Servlet
   * @throws IOException une exception
   */
  public void visualiserEntreprises(HttpServletRequest req, HttpServletResponse resp)
      throws IOException;

  /**
   * Permet de visualiser des entreprises en tant que professeur.
   * 
   * @param req la requête de la Servlet
   * @param resp la réponse de la Servlet
   * @throws IOException une exception
   */
  public void visualiserEntreprisesAsProf(HttpServletRequest req, HttpServletResponse resp)
      throws IOException;

  /**
   * Permet à un professeur de fusionner deux entreprises.
   * 
   * @param req la requête de la Servlet
   * @param resp la réponse de la Servlet
   * @throws IOException une exception
   */
  public void fusionnerDeuxEntreprises(HttpServletRequest req, HttpServletResponse resp)
      throws IOException;

}
