package com.ankush.karantraders.data.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery_challan")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DeliveryChallan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "siteid")
    private Site site;

    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customer;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "nettotal")
    private Float nettotal;

    @Column(name = "gst")
    private Float gst;

    @Column(name = "transport")
    private Float transport;

    @Column(name = "other")
    private Float other;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "grand")
    private Float grand;

    @Column(name = "paid")
    private Float paid;

    @OneToMany(mappedBy = "challan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallanTransaction> transactions = new ArrayList<>();



}