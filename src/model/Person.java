package model;

import java.io.Serializable;

public abstract class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String lastName;
    private long id;

    public Person(String name, String lastName, long id) {
        this.name = name;
        this.lastName = lastName;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
