package biz.entreprise;

public interface EntrepriseBiz extends EntrepriseDto {

  final int MAX_CARACTERES_DENOMINATION = 50;
  final int MAX_CARACTERES_ADRESSE = 50;
  final int MAX_CARACTERES_NUMERO = 10;
  final int MAX_CARACTERES_BOITE = 5;
  final int MAX_CARACTERES_CODE_POSTAL = 4;
  final int MAX_CARACTERES_VILLE = 30;
  final int MAX_CARACTERES_EMAIL = 50;
  final int MAX_CARACTERES_TEL = 15;
  final String REGEX_CODE_POSTAL = "[0-9]{4}";
  final String REGEX_EMAIL = ".+@.+\\..+";
  final String REGEX_TEL = "[0-9]+|\\+[0-9]+";
}
