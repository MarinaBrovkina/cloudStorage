package org.example.cloudStorage.repositories;


import org.example.cloudStorage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   User findUserByLogin(String login);

}
