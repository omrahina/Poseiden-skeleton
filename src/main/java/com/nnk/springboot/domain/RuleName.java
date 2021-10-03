package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleName {
    // TODO: Map columns in data table RULENAME with corresponding java fields

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(125)")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(125)")
    private String description;

    @Column(name = "json", columnDefinition = "varchar(125)")
    private String json;

    @Column(name = "template", columnDefinition = "varchar(512)")
    private String template;

    @Column(name = "sqlStr", columnDefinition = "varchar(125)")
    private String sqlStr;

    @Column(name = "sqlPart", columnDefinition = "varchar(125)")
    private String sqlPart;
}
