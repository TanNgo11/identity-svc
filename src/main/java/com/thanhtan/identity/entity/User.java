package com.thanhtan.identity.entity;

import com.thanhtan.identity.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    String username;
    String password;
    @Enumerated(EnumType.STRING)
    Status status;

    @ManyToMany
    Set<Role> roles = new HashSet<>();


}