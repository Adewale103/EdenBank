package com.twinkles.edenbanks.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Bank {
    private String id;
    private String name;
    private List<User> users;

    public Bank(String id, String name) {
        this.id = id;
        this.name = name;
        users = new ArrayList<>();
    }
}
