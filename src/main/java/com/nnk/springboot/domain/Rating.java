package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "moodysRating")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String moodysRating;

    @Column(name = "sandPRating")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String sandPRating;

    @Column(name = "fitchRating")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String fitchRating;

    @Column(name = "orderNumber")
    private Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
