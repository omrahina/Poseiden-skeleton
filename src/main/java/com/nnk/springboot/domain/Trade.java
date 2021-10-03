package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @Column(name = "account", columnDefinition = "varchar(30)", nullable = false)
    @NotBlank(message = "Account is mandatory")
    private String account;

    @Column(name = "type", columnDefinition = "varchar(30)", nullable = false)
    @NotBlank(message = "Type is mandatory")
    private String type;

    @Column(name = "buyQuantity")
    private Double buyQuantity;

    @Column(name = "sellQuantity")
    private Double sellQuantity;

    @Column(name = "sellPrice")
    private Double sellPrice;

    @Column(name = "tradeDate")
    private LocalDate tradeDate;

    @Column(name = "security", columnDefinition = "varchar(125)")
    private String security;

    @Column(name = "status", columnDefinition = "varchar(10)")
    private String status;

    @Column(name = "trader", columnDefinition = "varchar(125)")
    private String trader;

    @Column(name = "benchmark", columnDefinition = "varchar(125)")
    private String benchMark;

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

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }
}
