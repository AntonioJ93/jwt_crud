package com.example.jwt_crud.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    @NotBlank
    private String username;
    @Column(nullable = false)
    @NotBlank
    private String name;
    @Column(nullable = false)
    @NotBlank
    private String surname;
    @Column(nullable = false)
    @NotBlank
    private String password;
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "users",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @Column(nullable = false)
    private Set<UserRole> userRoles;
    @Column(unique = true,nullable = false)
    @Email
    private String email;
    private String phone;
    private String address;
    @Past
    private LocalDate birthDate;
    @Column(nullable = false,columnDefinition = "boolean default true")
    private Boolean enable;

    public void addUserRole(UserRole userRole){
        if(userRoles==null)
            userRoles=new HashSet<>();
        userRoles.add(userRole);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
