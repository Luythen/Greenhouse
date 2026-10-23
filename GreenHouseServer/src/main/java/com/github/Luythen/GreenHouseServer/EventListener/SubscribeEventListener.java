package com.github.Luythen.GreenHouseServer.EventListener;

import java.util.List;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.github.Luythen.GreenHouseServer.Service.SoilMoistureService;

@Component 
public class SubscribeEventListener {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SoilMoistureService soilMoistureService;

    public SubscribeEventListener (SimpMessagingTemplate simpMessagingTemplate, SoilMoistureService soilMoistureService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.soilMoistureService = soilMoistureService;
    }

    @EventListener 
    public void handleSubscribeEvent (SessionSubscribeEvent event) {
        @SuppressWarnings("unchecked")
        Map<String, List<String>> nHeader = (Map<String, List<String>>) event.getMessage().getHeaders().get("nativeHeaders");
        String destination = nHeader.get("destination").getFirst();

        if (destination.startsWith("/topic/soil")) {
            String id = destination.replaceAll("/topic/soil/", "");
            simpMessagingTemplate.convertAndSend(destination, soilMoistureService.getLastestMoistMeasurements(id));
        }

    }

}
