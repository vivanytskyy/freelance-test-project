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
public class User {
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;
    private String username;
    private String name;
    private String lastname;
}