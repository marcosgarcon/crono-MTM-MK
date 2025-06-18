    pri// src/main/java/com/mkcalculadora/Activity.java
package com.mkcalculadora;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Classe modelo para representar uma atividade na cronoanálise.
 */
public class Activity {
    private final SimpleStringProperty description;
vate final SimpleDoubleProperty standardTime; // Tempo padrão para a atividade

    public Activity(String description, double standardTime) {
        this.description = new SimpleStringProperty(description);
        this.standardTime = new SimpleDoubleProperty(standardTime);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public double getStandardTime() {
        return standardTime.get();
    }

    public void setStandardTime(double standardTime) {
        this.standardTime.set(standardTime);
    }

    public SimpleDoubleProperty standardTimeProperty() {
        return standardTime;
    }
}
