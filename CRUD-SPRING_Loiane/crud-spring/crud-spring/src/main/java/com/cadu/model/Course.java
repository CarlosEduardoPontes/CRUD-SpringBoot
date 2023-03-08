package com.cadu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Entity
@SQLDelete(sql = "UPDATE Course SET status = 'Inativo' WHERE id=?")
@Where(clause = "status = 'Ativo'")
public class Course {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long Id;

    @NotBlank
    @NotNull
    @Length(min=5, max = 200)
    @Column(length = 200, nullable = false)
    private String name;

    @NotNull
    @Length(max = 20)
//    @Pattern(regexp = "Back-end | Front-end")
    @Column(length = 20, nullable = false)
    private String category;

    @NotNull
    @Length(max = 20)
//    @Pattern(regexp = "Ativo | Inativo")
    @Column(length = 20, nullable = false)
    private String status = "Ativo";
}
