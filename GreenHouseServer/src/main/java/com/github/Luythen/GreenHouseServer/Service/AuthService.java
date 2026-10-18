package com.github.Luythen.GreenHouseServer.Service;

import com.github.Luythen.GreenHouseServer.Repository.UserRepository;
import org.springframework.stereotype.Service;

import com.github.Luythen.GreenHouseServer.Dto.LoginResponseDto;
import com.github.Luythen.GreenHouseServer.Dto.AuthDto;
import com.github.Luythen.GreenHouseServer.Dto.RegisterDto;
import com.github.Luythen.GreenHouseServer.Model.UserModel;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserDetailServiceImpl userDetailServiceImpl;
    private final JWTService jwtService;

    public AuthService (UserDetailServiceImpl userDetailServiceImpl, JWTService jwtService, UserRepository userRepository) {
        this.userDetailServiceImpl = userDetailServiceImpl;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public AuthDto login (String email, String password) throws Exception {
        UserModel userModel = (UserModel) userDetailServiceImpl.loadUserByUsername(email);

        String accessToken = jwtService.generateAccessToken(userModel);
        String refreshToken = jwtService.generateRefreshToken(userModel);

        return new AuthDto(refreshToken, new LoginResponseDto(accessToken, userModel.getFirstName(), userModel.getLastName(), userModel.getRole()));
    }

    public UserModel register (RegisterDto registerDto) throws Exception {
        if (registerDto.email().isEmpty() || registerDto.firstName().isEmpty() || registerDto.lastName().isEmpty() || registerDto.password().isEmpty()) throw new Exception("A field is empty");
        try {
            UserModel userModel = new UserModel();

            userModel.setEmail(registerDto.email());
            userModel.setFirstName(registerDto.firstName());
            userModel.setLastName(registerDto.lastName());
            userModel.setPassword(registerDto.password());
            userModel.setPaid(false);
            userModel.setRole("User");

            userRepository.insert(userModel);

            return userModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
