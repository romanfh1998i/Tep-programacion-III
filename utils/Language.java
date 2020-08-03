package edu.pucmm.jdbc.utils;

import java.io.IOException;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author pc
 */
public class Language {

    public static Locale ENGLISH = Locale.ENGLISH;
    public static Locale SPANISH = new Locale("es");

    private static volatile Language instance = null;
    private ResourceBundle rbEnglish;
    private ResourceBundle rbSpanish;
   
    private Locale DEFAULT = ENGLISH;

    private Language() {
        loadLanguage();
    }

    public static Language getInstance() {
        Language result = instance;
        if (result == null) {
            synchronized (Language.class) {
                if (instance == null) {
                    instance = result = new Language();
                }
            }
        }
        return result;
    }

    private void loadLanguage() {
        try {
            rbEnglish = new PropertyResourceBundle(getClass().getResourceAsStream("/language.properties"));
            rbSpanish = new PropertyResourceBundle(getClass().getResourceAsStream("/language_es.properties"));
            
        } catch (IOException ignored) {
        }
    }

    private ResourceBundle getResourceBundle() {
        if (DEFAULT.getLanguage().equals(ENGLISH.getLanguage())) {
            return rbEnglish;
        } else if (DEFAULT.getLanguage().equals(SPANISH.getLanguage())) {
           
        }
        return rbSpanish;
    }

    public Locale getDEFAULT() {
        return DEFAULT;
    }

    public void setDEFAULT(Locale DEFAULT) {
        this.DEFAULT = DEFAULT;
    }

    public String getText(String key) {
        return getResourceBundle().getString(key);
    }
}
