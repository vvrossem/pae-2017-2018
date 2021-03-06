package dal.dao;

import java.util.ArrayList;
import java.util.List;
import biz.factory.BizFactory;
import biz.pdc.PersonneContactDto;
import exceptions.FatalException;
import util.AppContext.DependanceInjection;

public class MockPersonneContactDaoImpl implements PersonneContactDao {

  @DependanceInjection
  private BizFactory factory;

  @Override
  public PersonneContactDto getPersonneContact(int idPersonneContact) {
    if (idPersonneContact == -1) {
      throw new FatalException();
    }
    if (idPersonneContact == Integer.MAX_VALUE) {
      return null;
    }
    PersonneContactDto personneContactDto = factory.getPersonneContactVide();
    personneContactDto.setIdPersonneContact(idPersonneContact);
    return personneContactDto;
  }

  @Override
  public List<PersonneContactDto> listerPersonnesContactByIdEntreprise(int idEntreprise) {
    List<PersonneContactDto> personnesContactDto = new ArrayList<PersonneContactDto>();
    if (idEntreprise == Integer.MAX_VALUE) {
      return personnesContactDto;
    }
    if (idEntreprise == -2) {
      throw new FatalException();
    }
    if (idEntreprise == 666) {
      return personnesContactDto;
    }
    PersonneContactDto personne = factory.getPersonneContactVide();
    personne.setEntreprise(idEntreprise);
    personnesContactDto.add(personne);
    return personnesContactDto;
  }

  @Override
  public PersonneContactDto insertPersonneContact(PersonneContactDto personneContact) {
    if (personneContact.getEntreprise() == -1) {
      throw new FatalException();
    }
    PersonneContactDto personne = factory.getPersonneContactVide();
    personne.setIdPersonneContact(personneContact.getIdPersonneContact());
    personne.setNom(personneContact.getNom());
    personne.setPrenom(personneContact.getPrenom());
    personne.setEmail(personneContact.getEmail());
    personne.setTel(personneContact.getTel());
    personne.setEntreprise(personneContact.getEntreprise());

    return personne;
  }

  @Override
  public boolean personneDeContactAppartientEntreprise(int idPersonneContact, int idEntreprise) {
    if (idPersonneContact == 2 && idEntreprise == 2) {
      return true;
    }
    return false;
  }

  @Override
  public List<PersonneContactDto> getResponsablesStage() {
    List<PersonneContactDto> responsables = new ArrayList<>();
    return responsables;
  }


  @Override
  public PersonneContactDto updatePersonneContact(PersonneContactDto personneContact) {
    // TODO Auto-generated method stub
    return null;
  }

}
