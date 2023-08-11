package uz.pdp.userservice.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import uz.pdp.userservice.domain.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    private List<Role> roles;

    @ManyToMany
    private List<Permission> permissions;

    @Enumerated(EnumType.STRING)
    private UserState state;

    private String verificationCode;

    @CreationTimestamp
    private LocalDateTime verificationDate;
}
