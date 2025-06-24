package com.krajust.criti_cloud_back.user;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.krajust.criti_cloud_back.user.UserMapper.toDTO;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO save(UserDTO userDTO) {
        return toDTO(userRepository.save(UserMapper.toEntity(userDTO)));
    }

    public Optional<UserDTO> findById(UUID id) {
        return userRepository.findById(id).map(UserMapper::toDTO);
    }

}
