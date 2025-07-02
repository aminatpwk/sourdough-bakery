package org.example.sourdough.repository;

import org.example.sourdough.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
