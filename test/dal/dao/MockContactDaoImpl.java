package dal.dao;

import java.util.ArrayList;
import java.util.List;
import biz.contact.ContactBiz.Etat;
import biz.contact.ContactDto;
import biz.factory.BizFactory;
import exceptions.FatalException;
import util.AppContext.DependanceInjection;

public class MockContactDaoImpl implements ContactDao {

  @DependanceInjection
  private BizFactory factory;


  @Override
  public List<ContactDto> listerContactsPourUtilisateur(int idUtilisateur) {
    List<ContactDto> liste = new ArrayList<>();
    if (idUtilisateur == 17) {
      // throw new CeciNestPasUneException("Ceci n'est pas une exception");
      throw new FatalException();
    }
    if (idUtilisateur % 2 == 0) {
      ContactDto contactVide = factory.getContactVide();
      liste.add(contactVide);
    }
    return liste;
  }

  @Override
  public ContactDto insertContact(ContactDto utilisateur) {
    return utilisateur;
  }

  /*
   * @Override public ContactDto insertContactUtilisateurSansPersonneContact(ContactDto utilisateur)
   * { return utilisateur; }
   */

  @Override
  public boolean existeContactForEntrepriseEtUser(int idUtilisateur, int idEntreprise) {
    if (idUtilisateur == 1 && idEntreprise == 1) {
      return true;
    } else if (idUtilisateur == Integer.MAX_VALUE) {
      throw new FatalException();
    }
    return false;
  }

  @Override
  public ContactDto getContact(int idContact) {
    if (idContact == -1) {
      throw new FatalException();
    }
    if (idContact == Integer.MAX_VALUE) {
      return null;
    }
    ContactDto contact = factory.getContactVide();
    contact.setIdContact(idContact);
    if (idContact == 779) {
      contact.setEtat(0);
      return contact;
    }
    if (idContact == 780) {
      contact.setEtat(1);
      return contact;
    }
    if (idContact == 781) {
      contact.setEtat(2);
      return contact;
    }
    if (idContact == 782) {
      contact.setEtat(3);
      return contact;
    }
    if (idContact == 1) {
      contact.setNumVersion(1);
    }
    if (idContact == 2) {
      contact.setNumVersion(2);
    }
    return contact;
  }

  @Override
  public boolean existeContactAvecEtat(int idUser, int etat) {
    if (idUser == 800 && etat == 3) {
      return true;
    }
    if (idUser == 801 && etat == 4) {
      return true;
    }
    return false;
  }

  @Override
  public boolean existeContactsAccepteOuEnOrdre(int idUser) {
    return (idUser == 800);
  }


  @Override
  public List<ContactDto> listerContactDiffDeEtat(int idUtilisateur, int etat) {
    List<ContactDto> listeContacts = new ArrayList<>();
    if (idUtilisateur == 779 || idUtilisateur == 780 || idUtilisateur == 781
        || idUtilisateur == 782) {
      return listeContacts;
    }
    return null;
  }

  @Override
  public List<ContactDto> getContacts(int idUser, int etat) {
    List<ContactDto> listeContacts = new ArrayList<>();
    ContactDto contact = factory.getContactVide();
    if (idUser == 2 && etat == Etat.ACCEPTE.getNumEtat()) {
      contact.setIdContact(1);
      contact.setEntreprise(2);
      listeContacts.add(contact);
      return listeContacts;
    }
    if (idUser == 4) {
      contact.setEtat(Etat.STAGE_EN_ORDRE.getNumEtat());
      contact.setIdContact(1);
      contact.setEntreprise(2);
      listeContacts.add(contact);
      return listeContacts;
    }
    if (idUser == 6 && etat == Etat.ACCEPTE.getNumEtat()) {
      contact.setIdContact(2);
      contact.setEntreprise(2);
      listeContacts.add(contact);
      return listeContacts;
    }
    if (idUser == 7 && etat == Etat.ACCEPTE.getNumEtat()) {
      contact.setIdContact(1);
      contact.setEntreprise(3);
      listeContacts.add(contact);
      return listeContacts;
    }

    return listeContacts;
  }

  @Override
  public List<ContactDto> listerContactsPourEntreprise(int idEntreprise) {
    ArrayList<ContactDto> list = new ArrayList<>();
    list.add(factory.getContactVide());
    return list;
  }


  @Override
  public ContactDto updateContact(ContactDto contact) {
    // TODO Auto-generated method stub
    return null;
  }

}
