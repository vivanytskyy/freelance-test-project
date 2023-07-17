package com.gmail.ivanytskyy.vitaliy.rest.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 17/07/2023
 */
@Data
public class Image {
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;
    private String name;
    private List<String> image;
}