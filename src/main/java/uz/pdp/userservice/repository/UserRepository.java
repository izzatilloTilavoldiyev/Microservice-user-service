package uz.pdp.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.userservice.domain.entity.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsUserByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
