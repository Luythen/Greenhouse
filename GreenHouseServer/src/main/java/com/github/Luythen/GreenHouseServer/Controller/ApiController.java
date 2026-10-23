package com.github.Luythen.GreenHouseServer.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.Luythen.GreenHouseServer.Dto.ApiKeyDto;
import com.github.Luythen.GreenHouseServer.Dto.ResponseDto;
import com.github.Luythen.GreenHouseServer.Model.GreenHouseModel;
import com.github.Luythen.GreenHouseServer.Model.UserModel;
import com.github.Luythen.GreenHouseServer.Service.GreenHouseSerivce;
import com.github.Luythen.GreenHouseServer.Service.JWTService;

@RestController 
@PreAuthorize("GH_User")
@RequestMapping("/key")
public class ApiController {

    private final JWTService jwtService;
    private final GreenHouseSerivce greenHouseSerivce;

    public ApiController (JWTService jwtService, GreenHouseSerivce greenHouseSerivce) {
        this.jwtService = jwtService;
        this.greenHouseSerivce = greenHouseSerivce;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateApiKey (@AuthenticationPrincipal UserModel userModel) {
        try {
            GreenHouseModel greenHouseModel = greenHouseSerivce.findGreenHouseByOwnerId(userModel.getId());

            String apiKey = jwtService.generateApiKey(greenHouseModel.getId(), userModel.getId());
            
            return ResponseEntity.ok(new ApiKeyDto(apiKey));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(400, e.getMessage()));
        }
    }

}
