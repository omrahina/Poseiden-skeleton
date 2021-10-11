package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "account", columnDefinition = "varchar(30)", nullable = false)
    @NotBlank(message = "Account is mandatory")
    private String account;

    @Column(name = "type", columnDefinition = "varchar(30)", nullable = false)
    @NotBlank(message = "Type is mandatory")
    private String type;

    @Column(name = "bidQuantity")
    private Double bidQuantity;

    @Column(name = "askQuantity")
    private Double askQuantity;

    @Column(name = "bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Column(name = "benchmark", columnDefinition = "varchar(125)")
    private String benchMark;

    @Column(name = "bidListDate")
    private LocalDate bidListDate;

    @Column(name = "commentary", columnDefinition = "varchar(125)")
    private String commentary;

    @Column(name = "security", columnDefinition = "varchar(125)")
    private String security;

    @Column(name = "status", columnDefinition = "varchar(10)")
    private String status;

    @Column(name = "trader", columnDefinition = "varchar(125)")
    private String trader;

    @Column(name = "book", columnDefinition = "varchar(125)")
    private String book;

    @Column(name = "creationName", columnDefinition = "varchar(125)")
    private String creationName;

    @Column(name = "creationDate")
    private LocalDate creationDate;

    @Column(name = "revisionName", columnDefinition = "varchar(125)")
    private String revisionName;

    @Column(name = "revisionDate")
    private LocalDate revisionDate;

    @Column(name = "dealName", columnDefinition = "varchar(125)")
    private String dealName;

    @Column(name = "dealType", columnDefinition = "varchar(125)")
    private String dealType;

    @Column(name = "sourceListId", columnDefinition = "varchar(125)")
    private String sourceListId;

    @Column(name = "side", columnDefinition = "varchar(125)")
    private String side;

    public BidList(String account, String type, double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
