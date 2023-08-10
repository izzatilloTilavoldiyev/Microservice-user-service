package uz.pdp.userservice.domain.entity.user;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.userservice.domain.entity.BaseEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission extends BaseEntity {
    private String permission;
}
