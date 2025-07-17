package com.practica.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    private LocalDateTime timestamp;

    // Constructor scurt fără ID (pentru salvare rapidă)
    public ActionLog(String action, LocalDateTime timestamp) {
        this.action = action;
        this.timestamp = timestamp;
    }
}
