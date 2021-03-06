package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import exceptions.FatalException;


public class AppContext {

  private HashMap<Class<?>, Object> instances = new HashMap<Class<?>, Object>();

  private Properties properties;

  @DependanceInjection
  private MonLogger monLogger;

  @Retention(RetentionPolicy.RUNTIME)
  public @interface DependanceInjection {
  }

  /**
   * Constructeur.
   */
  public AppContext() {
    try {
      instances.put(Class.forName("util.AppContext"), this);
    } catch (ClassNotFoundException ex) {
      throw new FatalException("Impossible de charger AppContext");
    }
  }

  /**
   * Crée une nouvelle instance de l'objet en paramètre via l'injection dépendance.
   * 
   * @param objet à instancier
   */
  public void recurDepInj(Object objet) {
    if (properties == null) {
      monLogger.getMonLog().warning("Le fichier properties n'a pas encore été chargé");
      return;
    }
    Class<?> currentClass = objet.getClass();

    List<Field> fields = new ArrayList<Field>();
    while (currentClass != null) {
      fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
      currentClass = currentClass.getSuperclass();
    }

    for (Field field : fields) {
      if (field.isAnnotationPresent(DependanceInjection.class)) {

        Object ob;
        Class<?> cl;
        try {
          cl = Class.forName(properties.getProperty(field.getType().getCanonicalName()));

        } catch (ClassNotFoundException e1) {
          monLogger.getMonLog().severe("Classe inexistante " + Util.stackTraceToString(e1));
          throw new FatalException("Classe inexistante");
        }
        if (!instances.containsKey(cl)) {
          Constructor constructor;
          try {
            constructor = cl.getDeclaredConstructor();
          } catch (NoSuchMethodException | SecurityException ex) {
            monLogger.getMonLog().severe("Constructeur inexistant " + Util.stackTraceToString(ex));
            throw new FatalException("Constructeur inexistant");
          }
          constructor.setAccessible(true);
          try {
            ob = constructor.newInstance();
          } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
              | InvocationTargetException ex) {
            monLogger.getMonLog()
                .severe("Initialisation impossible " + Util.stackTraceToString(ex));
            throw new FatalException("Initialisation impossible");
          }
          instances.put(cl, ob);
          // appel récursif
          recurDepInj(ob);
        }

        field.setAccessible(true);
        try {
          field.set(objet, instances.get(cl));
        } catch (IllegalArgumentException | IllegalAccessException ex) {
          monLogger.getMonLog()
              .severe("récupération instance impossible " + Util.stackTraceToString(ex));
          throw new FatalException("récupération instance impossible");
        }
      }
    }
  }



  /**
   * Charge le fichier Properties passé en paramètre.
   * 
   * @param fichier Le fichier où se trouve les Properties
   */
  public void loadProps(String fichier) {
    if (fichier == null) {
      fichier = "dev.properties";
    } else {
    }
    properties = new Properties();

    try (FileInputStream file = new FileInputStream("conf/" + fichier)) {
      properties.load(file);
    } catch (FileNotFoundException ex) {
      throw new FatalException("Fichier properties introuvable");
    } catch (IOException ex) {
      throw new FatalException("Impossible de lire le fichier properties");
    }
  }


  /**
   * Cherche la clé associée à la chaîne passée en paramètre.
   * 
   * @param key la chaîne représentant la clé
   * @return la clé correspondante dans le fichier Properties
   */
  public String getValueProp(String key) {
    if (properties == null) {
      monLogger.getMonLog().warning("Le fichier properties n'a pas encore été chargé");
      return null;
    }
    Util.checkString(key);
    return properties.getProperty(key);
  }

  /**
   * Cherche la classe correspondante à la clé.
   * 
   * @param key la chaîne représentant la clé
   * @return la classe correspondante
   */
  public Class<?> getClassValueProp(String key) {
    if (properties == null) {
      monLogger.getMonLog().warning("Le fichier properties n'a pas encore été chargé");
      return null;
    }
    Util.checkString(key);
    try {
      return Class.forName(properties.getProperty(key));
    } catch (ClassNotFoundException ex) {
      monLogger.getMonLog().severe("Classe inexistante " + Util.stackTraceToString(ex));
      throw new FatalException("Classe inexistante");
    }
  }
}

