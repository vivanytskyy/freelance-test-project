package com.gmail.ivanytskyy.vitaliy.rest.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 17/07/2023
 */
@Data
@Builder
public class Job {
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;
    private String title;
    private String description;
    private Double price;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String user;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Integer noOfComments;
}