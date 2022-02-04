package com.ankush.karantraders.data.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="purchasetransaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer code;
    private String description;
    private Integer hsn;
    private Float quantity;
    private String unit;
    private Float mrp;
    private Float discount;
    private Float rate;
    private Float gst;
    private Float amount;

    @ManyToOne
    @JoinColumn(name="invoiceno")
    private PurchaseInvoice invoice;
}
