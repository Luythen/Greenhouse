package com.github.Luythen.GreenHouseServer.Controller;

import com.github.Luythen.GreenHouseServer.Service.GreenHouseSerivce;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.Luythen.GreenHouseServer.Dto.AuthDto;
import com.github.Luythen.GreenHouseServer.Dto.LoginDto;
import com.github.Luythen.GreenHouseServer.Dto.RegisterDto;
import com.github.Luythen.GreenHouseServer.Dto.ResponseDto;
import com.github.Luythen.GreenHouseServer.Model.UserModel;
import com.github.Luythen.GreenHouseServer.Service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final GreenHouseSerivce greenHouseSerivce;
    private final AuthService authService;

    public AuthController (AuthService authService, GreenHouseSerivce greenHouseSerivce) {
        this.authService = authService;
        this.greenHouseSerivce = greenHouseSerivce;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDto loginDto, HttpServletResponse response, HttpServletRequest request) {
        try {
            AuthDto authDto = authService.login(loginDto.email(), loginDto.password());

            Cookie cookie = new Cookie("RefreshToken", authDto.refreshToken());
            cookie.setMaxAge(432000);
            cookie.setSecure(true);
            cookie.setPath("/auth/refresh");
            cookie.setHttpOnly(true);
            cookie.setDomain(request.getServerName());
            cookie.setAttribute("SameSite", "None");

            response.addCookie(cookie);

            return ResponseEntity.status(200).body(authDto.loginResponseDto());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(400, e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register (@RequestBody RegisterDto registerDto) {
        try {
            UserModel user = authService.register(registerDto);
            greenHouseSerivce.createNewGreenHouse(user.getId(), user.getFirstName());

            return ResponseEntity.ok().body(new ResponseDto(200, "Registeration successfull"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(400, e.getMessage()));
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh (@CookieValue("RefreshToken") String refreshToken) {
        try {
            return ResponseEntity.ok(authService.refresh(refreshToken));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(400, e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout (HttpServletResponse response, HttpServletRequest request) {
        Cookie cookie = new Cookie("RefreshToken", "LOL");
        cookie.setMaxAge(10);
        cookie.setSecure(true);
        cookie.setPath("/auth/refresh");
        cookie.setHttpOnly(true);
        cookie.setDomain(request.getServerName());
        cookie.setAttribute("SameSite", "None");

        response.addCookie(cookie);

        return ResponseEntity.ok(new ResponseDto(200,"Logout"));
    }
    
    

}
