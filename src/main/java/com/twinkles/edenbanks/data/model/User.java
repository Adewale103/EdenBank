package com.twinkles.edenbanks.data.model;

import com.twinkles.edenbanks.data.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Set<RoleType> roles;
    private List<Account> accounts;

    public User(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        accounts = new ArrayList<>();
        roles = new HashSet<>();
        roles.add(RoleType.ROLE_USER);
    }

    public User(String firstName, String lastName, String email, String phoneNumber, RoleType roleType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        accounts = new ArrayList<>();
        roles = new HashSet<>();
        roles.add(roleType);
    }
}
