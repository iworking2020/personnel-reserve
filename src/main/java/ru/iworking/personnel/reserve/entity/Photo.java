package ru.iworking.personnel.reserve.entity;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "photo")
public class Photo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "image")
    private byte[] image;

    public Photo() { }

    public Photo(byte[] image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Arrays.equals(image, photo.image);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(image);
    }
}
