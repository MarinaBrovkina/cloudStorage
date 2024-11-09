package org.example.cloudStorage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long size;
    private String path;
    @ManyToOne
    private User user;

    public File(String name, Long size, String path, User user) {
        this.name = name;
        this.size = size;
        this.path = path;
        this.user = user;
    }

    public File() {
    }
}
