package com.ankush.karantraders.data.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="purchaseinvoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="invoiceno")
    private String invoiceno;

    @Column(name="date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="party")
    private PurchaseParty party;

    @Column(name="nettotal")
    private Double nettotal;

    @Column(name="dicount")
    private Double discount;

    @Column(name="sgst")
    private Double sgst;

    @Column(name="cgst")
    private Double cgst;

    @Column(name="shiping")
    private Double shiping;

    @Column(name="packaging")
    private Double packaging;

    @Column(name="other")
    private Double other;

    @Column(name="grand")
    private Double grand;

    @ManyToOne
    @JoinColumn(name="bank")
    private Bank bank;

    @Column(name="paid")
    private Double paid;

    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PurchaseTransaction>transaction = new ArrayList<>();
}
