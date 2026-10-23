package com.github.Luythen.GreenHouseServer.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.Luythen.GreenHouseServer.Dto.MeasurementDto;
import com.github.Luythen.GreenHouseServer.Dto.ResponseDto;
import com.github.Luythen.GreenHouseServer.Model.SoilMoistureModel;
import com.github.Luythen.GreenHouseServer.Service.SoilMoistureService;

@PreAuthorize("GH_Api")
@RestController 
@RequestMapping("/soil")
public class SoilMoistureController {

    private final SoilMoistureService soilMoistureService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public SoilMoistureController (SoilMoistureService soilMoistureService, SimpMessagingTemplate simpMessagingTemplate) {
        this.soilMoistureService = soilMoistureService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSoilMeasurements (@RequestBody List<MeasurementDto> measurementDto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String id = securityContext.getAuthentication().getPrincipal().toString();
        try {
            System.out.println(measurementDto.get(0).sensorId());
            List<SoilMoistureModel> sList = soilMoistureService.addNewSoilMoistureMeasurement(id, measurementDto);
            simpMessagingTemplate.convertAndSend("/topic/soil/" + id , sList);

            return ResponseEntity.ok(new ResponseDto(200, "Moist measurement have been registerd"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(400, e.getMessage()));
        }
    }

}
