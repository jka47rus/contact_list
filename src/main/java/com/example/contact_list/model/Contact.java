package com.example.contact_list.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
}
