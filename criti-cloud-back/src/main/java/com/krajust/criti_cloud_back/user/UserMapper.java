package com.krajust.criti_cloud_back.user;

import com.krajust.criti_cloud_back.security.CustomUserDetails;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.id);
        user.setNickname(userDTO.nickname);
        user.setEmail(userDTO.email);
        return user;
    }

    public static UserDTO fromPrincipal(Authentication authentication) {
        final var customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return UserDTO.builder()
                .id(customUserDetails.getUser().id)
                .email(customUserDetails.getUser().email)
                .nickname(customUserDetails.getUser().nickname)
                .build();
    }
}
