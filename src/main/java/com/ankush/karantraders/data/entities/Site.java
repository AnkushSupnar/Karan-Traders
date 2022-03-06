package com.ankush.karantraders.data.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "site")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customer;

    @Column(name="sitename")
    private String sitename;

    @Column(name="addressline")
    private String addressline;

    @Column(name="village")
    private String village;

    @Column(name="taluka")
    private String taluka;

    @Column(name="district")
    private String district;

    @Column(name="contact")
    private String contact;

}