package com.airtime.airtime.repository;
import com.airtime.airtime.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // This allows for interaction with the users model in the database,
    // and allows the user entity to be injected into the services and controllers.
    Optional<User> findByUsername(String username);
}
