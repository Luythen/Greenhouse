package com.github.Luythen.GreenHouseServer.Controller;

import com.github.Luythen.GreenHouseServer.Service.GreenHouseSerivce;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.Luythen.GreenHouseServer.Dto.GreenHouseDto;
import com.github.Luythen.GreenHouseServer.Model.UserModel;

import org.springframework.web.bind.annotation.GetMapping;

@PreAuthorize("GH_User")
@RestController
@RequestMapping("/greenhouse")
public class GreenHouseController {

    private final GreenHouseSerivce greenHouseSerivce;

    public GreenHouseController(GreenHouseSerivce greenHouseSerivce) {
        this.greenHouseSerivce = greenHouseSerivce;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> greenHouseInfo (@AuthenticationPrincipal UserModel userModel) {
        GreenHouseDto greenHouseDto = greenHouseSerivce.getGreenHouseDashboard(userModel.getId());
        return ResponseEntity.ok(greenHouseDto);
    }
    

}
