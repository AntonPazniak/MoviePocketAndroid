package com.example.moviepocketandroid.api.models;

import java.util.Date;


/**
 * Base class with property 'id'.
 * Used as a base class for all objects that requires this property.
 */


public class BaseEntity {
    private Long id;
    private Date created;
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}