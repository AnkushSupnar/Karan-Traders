package com.ankush.karantraders.data.entities;
import lombok.*;
import javax.persistence.*;

@Table(name = "customer")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customername")
    private String customername;

    @Column(name = "addressline")
    private String addressline;

    @Column(name = "village")
    private String village;

    @Column(name = "taluka")
    private String taluka;

    @Column(name = "district")
    private String district;

    @Column(name = "contact")
    private String contact;

    @Column(name = "mobile")
    private String mobile;

}