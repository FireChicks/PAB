package com.kbd.PAB.VO;

import jakarta.persistence.*;

@Entity(name = "comEstimate")
@Table
public class ComEstimateVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comEstimateID", columnDefinition = "INT")
    private String comEstimateID;



}
