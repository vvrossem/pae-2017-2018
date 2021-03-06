package dal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import biz.contact.ContactDto;
import exceptions.FatalException;
import util.AppContext.DependanceInjection;
import util.MonLogger;
import util.Util;

public class ContactDaoImpl extends DaoGenerique<ContactDto> implements ContactDao {

  @DependanceInjection
  private EntrepriseDao entrepriseDao;

  @DependanceInjection
  private PersonneContactDao personneContactDao;

  @DependanceInjection
  private MonLogger monLogger;

  @Override
  public ContactDto getContact(int idContact) {
    ContactDto contact = super.get(idContact);
    contact.setEntrepriseDto(entrepriseDao.getEntreprise(contact.getEntreprise()));
    if (contact.getPersonneContact() != null) {
      contact.setPersonneContactDto(
          personneContactDao.getPersonneContact(contact.getPersonneContact()));
    }
    return contact;
  }

  @Override
  public List<ContactDto> getContacts(int idUser, int etat) {
    List<ContactDto> listeContacts;

    PreparedStatement ps =
        dalBackendServices.getPreparedStatement(appContext.getValueProp("query_get_contacts"));
    setPreparedStatement(ps, idUser, etat);
    try (ResultSet rs = ps.executeQuery()) {
      listeContacts = setResultSet(rs);
    } catch (SQLException ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Erreur accès DB");
    }

    return listeContacts;

  }

  @Override
  public List<ContactDto> listerContactsPourEntreprise(int idEntreprise) {
    List<ContactDto> listeContacts;

    PreparedStatement ps = dalBackendServices
        .getPreparedStatement(appContext.getValueProp("query_get_contacts_entreprise"));
    setPreparedStatement(ps, idEntreprise);
    try (ResultSet rs = ps.executeQuery()) {
      listeContacts = setResultSet(rs);
    } catch (SQLException ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Erreur accès DB");
    }

    return listeContacts;
  }

  @Override
  public List<ContactDto> listerContactsPourUtilisateur(int idUtilisateur) {
    List<ContactDto> listeContacts;

    PreparedStatement ps = dalBackendServices
        .getPreparedStatement(appContext.getValueProp("query_lister_contact_utilisateur"));
    setPreparedStatement(ps, idUtilisateur);
    try (ResultSet rs = ps.executeQuery()) {
      listeContacts = setResultSet(rs);
    } catch (SQLException ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Erreur accès DB");
    }
    for (ContactDto contactDto : listeContacts) {
      contactDto.setEntrepriseDto(entrepriseDao.getEntreprise(contactDto.getEntreprise()));
      if (contactDto.getPersonneContact() != null) {
        contactDto.setPersonneContactDto(
            personneContactDao.getPersonneContact(contactDto.getPersonneContact()));
      }
    }
    return listeContacts;
  }


  @Override
  public ContactDto insertContact(ContactDto contact) {
    return super.insert(contact);
  }

  @Override
  public List<ContactDto> listerContactDiffDeEtat(int idUtilisateur, int etat) {
    List<ContactDto> listeContacts;

    PreparedStatement ps = dalBackendServices
        .getPreparedStatement(appContext.getValueProp("query_lister_contact_different_de_etat"));
    setPreparedStatement(ps, idUtilisateur, etat);
    try (ResultSet rs = ps.executeQuery()) {
      listeContacts = setResultSet(rs);
    } catch (SQLException ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Erreur accès DB");
    }

    return listeContacts;
  }

  @Override
  public boolean existeContactForEntrepriseEtUser(int idUtilisateur, int idEntreprise) {
    PreparedStatement ps = dalBackendServices.getPreparedStatement(
        appContext.getValueProp("query_existe_contact_for_entreprise_et_user"));
    setPreparedStatement(ps, idUtilisateur, idEntreprise);

    try (ResultSet resultSet = ps.executeQuery()) {
      if (resultSet.next()) {
        return true;
      } else {
        return false;
      }
    } catch (SQLException ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Erreur acces Db");
    }
  }

  public ContactDto updateContact(ContactDto contact) {
    return this.update(contact);
  }

  @Override
  public boolean existeContactAvecEtat(int idUser, int etat) {
    PreparedStatement ps = dalBackendServices
        .getPreparedStatement(appContext.getValueProp("query_existe_contact_avec_etat"));
    setPreparedStatement(ps, idUser, etat);
    try (ResultSet rs = ps.executeQuery()) {
      if (rs.next()) {
        int nbContacts = rs.getInt(1);
        return nbContacts == 1;
      }
      return false;
    } catch (SQLException ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Erreur acces Db");
    }
  }

  @Override
  public boolean existeContactsAccepteOuEnOrdre(int idUser) {
    PreparedStatement ps = dalBackendServices
        .getPreparedStatement(appContext.getValueProp("query_existe_contact_accepte_ou_en_ordre"));
    setPreparedStatement(ps, idUser);

    try (ResultSet resultSet = ps.executeQuery()) {
      if (resultSet.next()) {
        return true;
      } else {
        return false;
      }
    } catch (SQLException ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Erreur acces Db");
    }

  }

}
