package com.br.game_library.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "game")
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "steam_app_id")
    private Long steamAppId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 100)
    private String genre;

    @Column(length = 50)
    private String platform;

    @Column(name = "release_year")
    private Integer releaseYear;

    private Double rating;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    private Boolean favorite;
}
