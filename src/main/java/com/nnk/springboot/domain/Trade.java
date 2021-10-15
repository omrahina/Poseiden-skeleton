package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Entity
@Table(name = "trade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields

    @Id
    @Column(name = "TradeId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tradeId;

    @Column(name = "account")
    @NotBlank(message = "Account is mandatory")
    @Length(max = 30, message = "must not exceed 30 characters")
    private String account;

    @Column(name = "type")
    @NotBlank(message = "Type is mandatory")
    @Length(max = 30, message = "must not exceed 30 characters")
    private String type;

    @Column(name = "buyQuantity")
    private Double buyQuantity;

    @Column(name = "sellQuantity")
    private Double sellQuantity;

    @Column(name = "sellPrice")
    private Double sellPrice;

    @Column(name = "tradeDate")
    private LocalDate tradeDate;

    @Column(name = "security")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String security;

    @Column(name = "status")
    @Length(max = 10, message = "must not exceed 10 characters")
    private String status;

    @Column(name = "trader")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String trader;

    @Column(name = "benchmark")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String benchMark;

    @Column(name = "book")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String book;

    @Column(name = "creationName")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String creationName;

    @Column(name = "creationDate")
    private LocalDate creationDate;

    @Column(name = "revisionName")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String revisionName;

    @Column(name = "revisionDate")
    private LocalDate revisionDate;

    @Column(name = "dealName")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String dealName;

    @Column(name = "dealType")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String dealType;

    @Column(name = "sourceListId")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String sourceListId;

    @Column(name = "side")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String side;

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }
}
