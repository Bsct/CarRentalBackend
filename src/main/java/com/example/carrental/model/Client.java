package com.example.carrental.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String address;

    @OneToOne(mappedBy = "client", cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY, optional = true)
    private ApplicationUser user;

}
