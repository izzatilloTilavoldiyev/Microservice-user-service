package uz.pdp.userservice.service.user;

import uz.pdp.userservice.domain.dto.UserRequestDTO;

public interface UserService {

    String save(UserRequestDTO dto);
}
