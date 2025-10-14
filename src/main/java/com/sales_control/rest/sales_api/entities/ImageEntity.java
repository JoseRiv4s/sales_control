package com.sales_control.rest.sales_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    // ✅ Relación inversa (opcional, no FK aquí)
    @OneToOne(mappedBy = "image")
    private ProductEntity product;
}
