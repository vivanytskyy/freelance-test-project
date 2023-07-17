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
public class Comment {
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;
    private String message;
    private String username;
    private String commentDate;
}