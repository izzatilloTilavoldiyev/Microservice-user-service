package uz.pdp.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.userservice.domain.entity.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

//    @Query("select count(u)>0 from users u where u.email = :email and not u.deleted")
    boolean existsUserByEmail(String email);

    @Query("from users u where u.email = :email and not u.deleted")
    Optional<User> findUserByEmail(String email);

    @Query("from users u where u.id = :id and not u.deleted")
    Optional<User> findUserById(UUID id);
}
