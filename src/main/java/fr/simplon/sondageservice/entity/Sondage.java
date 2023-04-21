package fr.simplon.sondageservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "sondage")
public class Sondage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(min=3, max=120)
    @Column(name = "description", length = 120)
    private String description;

    @Column(name = "question", length = 120)
    private String question;

    @Column(name = "date_creation")
    private LocalDate dateCreation = LocalDate.now();

    @Column(name = "date_cloture")
    private LocalDate dateCloture;

    @NotBlank
    @Size(max=64)
    @Column(name = "createur", nullable = false)
    private String createur;

    public Sondage(String description, String question, LocalDate dateCloture, String createur) {
        this.description = description;
        this.question = question;
        this.dateCloture = dateCloture;
        this.createur = createur;
    }

    public Sondage() {
    }

    // Getters et setters pour chaque propriété

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && !description.isBlank()) {
            this.description = description.substring(0, Math.min(description.length(), 120));
        } else {
            throw new IllegalArgumentException("La description ne peut pas être vide.");
        }
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question.substring(0, Math.min(question.length(), 120));
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDate dateCloture) {
        if (dateCloture.isAfter(dateCreation)) {
            this.dateCloture = dateCloture;
        } else {
            throw new IllegalArgumentException("La date de clôture doit être postérieure à la date de création.");
        }
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        if (createur != null) {
            createur = createur.trim();
        }

        if (createur != null && !createur.isBlank()) {
            this.createur = createur;
        } else {
            throw new IllegalArgumentException("Le nom du créateur ne peut pas être vide et doit avoir une longueur maximale de 64 caractères.");
        }
    }
}
