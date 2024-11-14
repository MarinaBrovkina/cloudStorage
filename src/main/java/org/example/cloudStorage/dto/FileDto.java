package org.example.cloudStorage.dto;

import lombok.Data;

@Data
public class FileDto {
    private Long id;
    private String name;
    private Long size;
    private String path;

    public FileDto(Long id, String name, Long size, String path) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.path = path;
    }
}
