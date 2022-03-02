package com.ankush.karantraders.data.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "unit")
    private String unit;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "gst")
    private Float gst;

    @Column(name = "amount")
    private Float amount;

    @ManyToOne
    @JoinColumn(name = "itemstockid")
    private ItemStock itemStock;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "billno")
    private Bill bill;

}
