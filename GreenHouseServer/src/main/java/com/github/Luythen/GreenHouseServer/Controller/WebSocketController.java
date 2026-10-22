package com.github.Luythen.GreenHouseServer.Controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.github.Luythen.GreenHouseServer.Model.SoilMoistureModel;
import com.github.Luythen.GreenHouseServer.Service.SoilMoistureService;

@Controller 
public class WebSocketController {

    private final SoilMoistureService soilMoistureService;

    public WebSocketController (SoilMoistureService soilMoistureService) {
        this.soilMoistureService = soilMoistureService;
    }

    @SubscribeMapping("/topic/soil/{id}")
    public List<SoilMoistureModel> soil (@DestinationVariable String id) {
        try {
            return soilMoistureService.getLastestMoistMeasurements(id);
        } catch (Exception e) {
            return null;
        }
    }

}
