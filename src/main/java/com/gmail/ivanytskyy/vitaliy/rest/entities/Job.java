package com.gmail.ivanytskyy.vitaliy.rest.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 17/07/2023
 */
@Data
public class Job {
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String user;
    private Integer noOfComments;
}