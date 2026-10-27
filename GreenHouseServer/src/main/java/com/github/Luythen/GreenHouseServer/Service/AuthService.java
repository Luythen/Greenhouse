package com.github.Luythen.GreenHouseServer.Service;

import com.github.Luythen.GreenHouseServer.Repository.UserRepository;

import io.jsonwebtoken.Claims;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.Luythen.GreenHouseServer.Dto.LoginResponseDto;
import com.github.Luythen.GreenHouseServer.Dto.AuthDto;
import com.github.Luythen.GreenHouseServer.Dto.RegisterDto;
import com.github.Luythen.GreenHouseServer.Exceptions.EmailAlreadyExistsException;
import com.github.Luythen.GreenHouseServer.Exceptions.InvalidFieldException;
import com.github.Luythen.GreenHouseServer.Exceptions.InvalidRefreshToken;
import com.github.Luythen.GreenHouseServer.Exceptions.PasswordNotMatchException;
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

    public AuthDto login (String email, String password) {
        UserModel userModel = (UserModel) userDetailServiceImpl.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, userModel.getPassword())) throw new PasswordNotMatchException("Lösenord matchar inte");

        String accessToken = jwtService.generateAccessToken(userModel);
        String refreshToken = jwtService.generateRefreshToken(userModel);

        return new AuthDto(refreshToken, new LoginResponseDto(accessToken, userModel.getFirstName(), userModel.getLastName(), userModel.getRole()));
    }

    public UserModel register (RegisterDto registerDto) {
        if (registerDto.email().isEmpty() || registerDto.firstName().isEmpty() || registerDto.lastName().isEmpty() || registerDto.password().isEmpty()) throw new InvalidFieldException("Tomt fält");
        
        UserModel userModel = new UserModel();

        userModel.setEmail(registerDto.email());
        userModel.setFirstName(registerDto.firstName());
        userModel.setLastName(registerDto.lastName());
        userModel.setPassword(passwordEncoder.encode(registerDto.password()));
        userModel.setPaid(false);
        userModel.setRole("User");
        
        try {
            return userRepository.insert(userModel);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException(registerDto.email());
        }
    }

    public LoginResponseDto refresh (String refreshToken) {
        try {
            if (jwtService.isTokenExpired(refreshToken, "refreshToken")) throw new InvalidRefreshToken("RefreshToken har gått ut");
            Claims claims = jwtService.getClaims(refreshToken, "refreshToken");
            UserModel userModel = (UserModel) userDetailServiceImpl.loadUserByUsername(claims.get(Claims.SUBJECT).toString());

            String accessToken = jwtService.generateAccessToken(userModel);
            return new LoginResponseDto(accessToken, userModel.getFirstName(), userModel.getLastName(), userModel.getRole());
        } catch (InvalidRefreshToken | UsernameNotFoundException e) {
            throw new InvalidRefreshToken("Ogiltig refreshtoken");
        }
    }

}
