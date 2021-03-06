package biz.ucc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import biz.contact.ContactDto;
import biz.factory.BizFactory;
import biz.user.UserDto;
import exceptions.BizException;
import exceptions.FatalException;
import exceptions.OptimisticLockException;
import util.AppContext;
import util.AppContext.DependanceInjection;

public class ContactUccImplTest {
  @DependanceInjection
  private ContactUcc contactUcc;
  @DependanceInjection
  private BizFactory factory;

  List<ContactDto> listeContacts;
  private ContactDto contact;
  private UserDto userTrue;



  @Before
  public void setUp() throws Exception {
    AppContext appContext = new AppContext();
    appContext.loadProps("test.properties");
    appContext.recurDepInj(this);
    listeContacts = new ArrayList<>();
    contact = factory.getContactVide();
    contact.setAnneeAcademique("2017-2018");
    userTrue = factory.getUserVide();
    userTrue.setAnneeAcademique("2017-2018");

  }

  @Test(expected = FatalException.class)
  public void testCreerContactUtilisateur1() {
    contactUcc.creerContactUtilisateur(-1, 0, 2, 1);
  }

  // test user inexistant
  @Test(expected = BizException.class)
  public void testCreerContactUtilisateur2() {
    contactUcc.creerContactUtilisateur(99999, 0, 2, 1);
  }

  // test entreprise inexistant
  @Test(expected = BizException.class)
  public void testCreerContactUtilisateur3() {
    contactUcc.creerContactUtilisateur(1, 0, 9999999, 1);
  }

  // test user admin
  @Test(expected = BizException.class)
  public void testCreerContactUtilisateur4() {
    contactUcc.creerContactUtilisateur(777, 0, 1, 1);
  }

  // test user année aca != année aca courante
  @Test(expected = BizException.class)
  public void testCreerContactUtilisateur5() {
    contactUcc.creerContactUtilisateur(778, 0, 1, 1);
  }

  // test numVersion user diff
  @Test(expected = OptimisticLockException.class)
  public void testCreerContactUtilisateur6() {
    contactUcc.creerContactUtilisateur(100, 99, 2, 2);
  }

  // test avec contact accepte au préalable
  @Test(expected = BizException.class)
  public void testCreerContactUtilisateur7() {
    contactUcc.creerContactUtilisateur(800, 0, 2, 2);
  }

  // test avec existeContactForEntrepriseEtUser
  @Test(expected = BizException.class)
  public void testCreerContactUtilisateur8() {
    contactUcc.creerContactUtilisateur(1, 1, 1, 2);
  }

  // test idPersonneContact null
  @Test
  public void testCreerContactUtilisateur9() {
    assertNotNull(contactUcc.creerContactUtilisateur(1, 1, 2, null));
  }

  // test personneDeContact n'Appartient pas à l'Entreprise
  @Test(expected = BizException.class)
  public void testCreerContactUtilisateur10() {
    contactUcc.creerContactUtilisateur(1, 1, 2, 1);
  }

  // test idPersonneContact non null
  @Test
  public void testCreerContactUtilisateur11() {
    assertNotNull(contactUcc.creerContactUtilisateur(1, 1, 2, 2));
  }

  @Test(expected = BizException.class)
  public void testListerContactUtilisateur1() {
    contactUcc.listerContactsUtilisateur(99999);
  }


  // @Test(expected = BizException.class)
  // public void testListerContactUtilisateur2() {
  // user.setIdUtilisateur(-1);
  // contactUcc.listerContactUtilisateur(user);
  // }

  @Test
  public void testListerContactUtilisateur3() {
    listeContacts = contactUcc.listerContactsUtilisateur(15);
    assertTrue(listeContacts.isEmpty());
  }

  @Test
  public void testListerContactUtilisateur4() {
    listeContacts = contactUcc.listerContactsUtilisateur(16);
    assertTrue(!listeContacts.isEmpty());
  }

  @Test(expected = FatalException.class)
  public void testListerContactUtilisateur5() {
    listeContacts = contactUcc.listerContactsUtilisateur(17);
  }

  @Test(expected = BizException.class)
  public void testListerContactUtilisateur6() {
    contactUcc.listerContactsUtilisateur(777);
  }

  // test si liste en param est null
  @Test(expected = FatalException.class)
  public void testUpdateEtatContacts1() {
    listeContacts = null;
    contactUcc.updateEtatContacts(listeContacts, 0, 0);
  }

  // test si liste en param est vide
  @Test(expected = BizException.class)
  public void testUpdateEtatContacts2() {
    contactUcc.updateEtatContacts(listeContacts, 0, 0);
  }

  // test si id user en param existe
  @Test(expected = BizException.class)
  public void testUpdateEtatContacts3() {
    listeContacts.add(contact);
    contactUcc.updateEtatContacts(listeContacts, 99999, 0);
  }

  // test si (id) user en param correspond a un user administrateur(=prof)
  @Test(expected = BizException.class)
  public void testUpdateEtatContacts4() {
    listeContacts.add(contact);
    contactUcc.updateEtatContacts(listeContacts, 777, 0);
  }

  // test si (id) user en param a bien l'annee academique == annee academique courante
  @Test(expected = BizException.class)
  public void testUpdateEtatContacts5() {
    listeContacts.add(contact);
    contactUcc.updateEtatContacts(listeContacts, 778, 0);
  }

  // test si (id) user en param a bien num version == num version en param
  @Test(expected = OptimisticLockException.class)
  public void testUpdateEtatContacts6() {
    listeContacts.add(contact);
    contactUcc.updateEtatContacts(listeContacts, 779, -1);
  }

  // test si les contacts dans la liste de contacts en param ont bien num version == num version de
  // la "db"
  @Test(expected = OptimisticLockException.class)
  public void testUpdateEtatContacts7() {
    contact.setNumVersion(-1);
    listeContacts.add(contact);
    contactUcc.updateEtatContacts(listeContacts, 779, 0);
  }

  // test si il existe un contact à l'état accepte ds la "db"
  @Test(expected = BizException.class)
  public void testUpdateEtatContacts8() {
    listeContacts.add(contact);
    contactUcc.updateEtatContacts(listeContacts, 800, 0);
  }

  // test si il existe un contact à l'état stage en ordre ds la "db"
  @Test(expected = BizException.class)
  public void testUpdateEtatContacts9() {
    listeContacts.add(contact);
    contactUcc.updateEtatContacts(listeContacts, 801, 0);
  }

  // test update valide (ici l'etat est à null car getContact de MockContactDaoImpl renvoie un
  // contact vide pour l'id 802)
  @Test(expected = BizException.class)
  public void testUpdateEtatContacts10() {
    listeContacts.add(contact);
    contactUcc.updateEtatContacts(listeContacts, 802, 0);
  }

  // test si le contact existe en db
  @Test(expected = BizException.class)
  public void testUpdateEtatContacts12() {
    contact.setIdContact(Integer.MAX_VALUE);
    listeContacts.add(contact);
    contactUcc.updateEtatContacts(listeContacts, 800, 0);
  }

  /**
   * tests update valide (parcours de tout le code de la méthode)
   */
  @Test
  public void testUpdateEtatContacts11() {
    for (int etat = 0; etat <= 4; etat++) {
      for (int idUser = 779; idUser <= 782; idUser++) {
        try {
          testUpdateAvecEtat(etat, idUser);
        } catch (Exception e) {
        }
      }
    }
  }


  private void testUpdateAvecEtat(int etatChange, int idUser) {
    contact.setIdContact(idUser);
    contact.setEtat(etatChange);
    listeContacts.add(contact);
    contactUcc.updateEtatContacts(listeContacts, idUser, 0);
    try {
      setUp();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
