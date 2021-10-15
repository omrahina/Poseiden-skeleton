package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "bidlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields

    @Id
    @Column(name = "BidListId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bidListId;

    @Column(name = "account")
    @NotBlank(message = "Account is mandatory")
    @Length(max = 30,  message = "must not exceed 30 characters")
    private String account;

    @Column(name = "type")
    @NotBlank(message = "Type is mandatory")
    @Length(max = 30,  message = "must not exceed 30 characters")
    private String type;

    @Column(name = "bidQuantity")
    private Double bidQuantity;

    @Column(name = "askQuantity")
    private Double askQuantity;

    @Column(name = "bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Column(name = "benchmark")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String benchMark;

    @Column(name = "bidListDate")
    private LocalDate bidListDate;

    @Column(name = "commentary")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String commentary;

    @Column(name = "security")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String security;

    @Column(name = "status")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String status;

    @Column(name = "trader")
    @Length(max = 125, message = "must not exceed 125 characters")
    private String trader;

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

    public BidList(String account, String type, double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
