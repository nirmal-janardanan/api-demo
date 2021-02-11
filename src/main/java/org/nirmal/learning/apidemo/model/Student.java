package org.nirmal.learning.apidemo.model;

import lombok.*;

import java.time.LocalDate;

@Value
@With
public class Student {

    private final int rollNumber;
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
}
