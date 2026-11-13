package com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects;

/**
 * First and last name of a person.
 */
public record PersonName(String firstName, String lastName) {
    public PersonName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("organizations.error.profile.firstName.notBlank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("organizations.error.profile.lastName.notBlank");
        }
    }

    public String getFullName() {
        return "%s %s".formatted(firstName, lastName);
    }
}
