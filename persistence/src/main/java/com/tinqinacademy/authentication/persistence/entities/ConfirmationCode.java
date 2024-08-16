package com.tinqinacademy.authentication.persistence.entities;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "confirmation_codes")
public class ConfirmationCode {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "code", nullable = false,unique = true, length = 12)
    private String code;

    @Column(name = "email", nullable = false, unique = true, length = 64)
    private String email;
}
