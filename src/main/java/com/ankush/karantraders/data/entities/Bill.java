package com.ankush.karantraders.data.entities;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billno")
    @NotFound(action = NotFoundAction.IGNORE)
    private Long billno;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "customeid")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bankid")
    private Bank bank;

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

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

}
