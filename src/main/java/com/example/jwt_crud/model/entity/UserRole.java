package com.example.jwt_crud.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_users",
            joinColumns = @JoinColumn(name = "user_role_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    @JsonIgnore
    private List<User> users;

    public void addUser(User user) {
        if (users==null)
            users=new ArrayList<>();
        users.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        return name.equals(userRole.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
