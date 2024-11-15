package org.example.cloudStorage.repository;

import org.example.cloudStorage.entity.File;
import org.example.cloudStorage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByUser(User user);
}