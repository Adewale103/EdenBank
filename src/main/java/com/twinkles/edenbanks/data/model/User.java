package com.twinkles.edenbanks.data.model;

import com.twinkles.edenbanks.data.model.enums.RoleType;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Set<RoleType> roles;
    private Account account;

    public User(String firstName, String lastName, String email,Account account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        roles = new HashSet<>();
        roles.add(RoleType.ROLE_USER);
        this.account = account;
    }

    public User(String firstName, String lastName, String email, String phoneNumber, RoleType roleType, Account account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        roles = new HashSet<>();
        roles.add(roleType);
        this.account = account;
    }
}
