package com.escolinha.model.classroom;

import org.hibernate.boot.jaxb.hbm.spi.SubEntityInfo;

//Matérias da Escola
public enum Subject {
    MATEMATICA("Matemática"),
    PORTUGUES("Portugês"),
    CIENCIAS("Ciências"),
    GEOGRAFIA("Geografia");

    private final String displayName;

    Subject(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
