package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "moodysRating", columnDefinition = "varchar(125)")
    private String moodysRating;

    @Column(name = "sandPRating", columnDefinition = "varchar(125)")
    private String sandPRating;

    @Column(name = "fitchRating", columnDefinition = "varchar(125)")
    private String fitchRating;

    @Column(name = "orderNumber")
    private Integer orderNumber;
}
