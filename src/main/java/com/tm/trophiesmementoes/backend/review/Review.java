package com.tm.trophiesmementoes.backend.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "reviews")
@Getter @Setter
public class Review {

    @Id
    private String id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_initials")
    private String authorInitials;

    private Integer rating;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    private Boolean verified = false;

    @Column(name = "created_at")
    private Instant createdAt;
}
