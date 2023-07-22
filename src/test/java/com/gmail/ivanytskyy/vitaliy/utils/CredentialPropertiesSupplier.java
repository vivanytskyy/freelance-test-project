package com.gmail.ivanytskyy.vitaliy.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Objects;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 22/07/2023
 */
public class CredentialPropertiesSupplier {
    private static CredentialPropertiesSupplier instance = null;
    private static final String PATH_TO_PROPERTIES;
    static {
        PATH_TO_PROPERTIES = new File(
                "src" + File.separator
                        + "test" + File.separator
                        + "resources" + File.separator
                        + "credential.properties")
                .getAbsolutePath();
    }
    private final Properties properties;

    private CredentialPropertiesSupplier(){
        properties = new Properties();
        try {
            properties.load(new FileReader(PATH_TO_PROPERTIES));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static CredentialPropertiesSupplier getInstance(){
        if(instance == null){
            instance = new CredentialPropertiesSupplier();
        }
        return instance;
    }
    public String getProperty(String propertyName){
        return Objects.requireNonNull(this.properties.getProperty(propertyName),
                "Property " + propertyName + " isn't exist");
    }
    public void setProperty(String key, String value) throws IOException {
        properties.setProperty(key, value);
        properties.store(new FileWriter(PATH_TO_PROPERTIES), null);
    }
}