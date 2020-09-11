package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "IMAGE_CONTAINER")
public class ImageContainer {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_CONTAINER_SEQ_GEN")
    @SequenceGenerator(name = "IMAGE_CONTAINER_SEQ_GEN", sequenceName = "IMAGE_CONTAINER_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Lob
    @Column(name = "IMAGE")
    private byte[] image;

    public ImageContainer(byte[] image) {
        this.image = image;
    }

}
