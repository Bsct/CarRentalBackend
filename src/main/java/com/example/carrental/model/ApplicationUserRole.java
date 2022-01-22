package com.example.carrental.model;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserRole {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<ApplicationUser> users;

}
