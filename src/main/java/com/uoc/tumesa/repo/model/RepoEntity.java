package com.uoc.tumesa.repo.model;

import com.mongodb.lang.Nullable;

/**
 * Interfaz para representar un objeto enlazado al repositorio.
 * */
public abstract class RepoEntity {

    @Nullable
    private String id; // Null si no existe en el repositorio


    public RepoEntity() {}

    public RepoEntity(@Nullable String id) {
        this.id = id;
    }


    @Nullable
    public String getId() {
        return id;
    }
    public void setId(@Nullable String id) {
        this.id = id;
    }
}