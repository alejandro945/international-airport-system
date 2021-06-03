package model;

import java.io.Serializable;

public abstract class Person implements Serializable {
    private String name;
    private String lastName;
    private long id;

    public Person(String name, String lastName, long id) {
        this.name = name;
        this.lastName = lastName;
        this.id = id;
    }

    /**
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return long
     */
    public long getId() {
        return this.id;
    }

    /**
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

}
