package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String name;

    @Column(name = "description")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String description;

    @Column(name = "json")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String json;

    @Column(name = "template")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String template;

    @Column(name = "sqlStr")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String sqlStr;

    @Column(name = "sqlPart")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String sqlPart;

    public RuleName(String name, String description, String json, String template, String sql, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sql;
        this.sqlPart = sqlPart;
    }
}
