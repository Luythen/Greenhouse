package com.github.Luythen.GreenHouseServer.Service;

import com.github.Luythen.GreenHouseServer.Repository.UserRepository;

import io.jsonwebtoken.Claims;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.Luythen.GreenHouseServer.Dto.LoginResponseDto;
import com.github.Luythen.GreenHouseServer.Dto.AuthDto;
import com.github.Luythen.GreenHouseServer.Dto.RegisterDto;
import com.github.Luythen.GreenHouseServer.Model.UserModel;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailServiceImpl userDetailServiceImpl;
    private final JWTService jwtService;

    public AuthService (UserDetailServiceImpl userDetailServiceImpl, JWTService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userDetailServiceImpl = userDetailServiceImpl;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthDto login (String email, String password) throws Exception {
        UserModel userModel = (UserModel) userDetailServiceImpl.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, userModel.getPassword())) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

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
            userModel.setPassword(passwordEncoder.encode(registerDto.password()));
            userModel.setPaid(false);
            userModel.setRole("User");

            userRepository.insert(userModel);

            return userModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public LoginResponseDto refresh (String refreshToken) throws Exception {
        if (jwtService.isTokenExpired(refreshToken, "refreshToken")) throw new Exception("Expierd");

        Claims claims = jwtService.getClaims(refreshToken, "refreshToken");
        try {
            UserModel userModel = (UserModel) userDetailServiceImpl.loadUserByUsername(claims.get(Claims.SUBJECT).toString());

            String accessToken = jwtService.generateAccessToken(userModel);
            return new LoginResponseDto(accessToken, userModel.getFirstName(), userModel.getLastName(), userModel.getRole());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
