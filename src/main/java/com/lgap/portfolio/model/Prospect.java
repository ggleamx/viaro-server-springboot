package com.lgap.portfolio.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Prospect {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String toString() {
        return "Prospect: " + this.firstName + " " + this.lastName;
    }
}
