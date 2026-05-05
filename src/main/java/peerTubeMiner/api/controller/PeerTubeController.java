package peerTubeMiner.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import peerTubeMiner.api.model.videoMiner.VMChannel;
import peerTubeMiner.api.service.PeerTubeService;


@RestController
@RequestMapping("/peertube")
public class PeerTubeController {

    private final PeerTubeService service;

    public PeerTubeController(PeerTubeService service) {
        this.service = service;
    }

    /**
     * GET /{id}
     * Operación de solo lectura para pruebas. 
     * Muestra los datos transformados sin enviarlos a VideoMiner.
     */
    @GetMapping("/{id}")
    public VMChannel getChannel(
            @PathVariable String id,
            @RequestParam(defaultValue = "10") int maxVideos,
            @RequestParam(defaultValue = "2") int maxComments
    ) {
        VMChannel channel = service.getChannelFull(id, maxVideos, maxComments);
        
        // REGLA: Si el canal no existe, devolver 404.
        if (channel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Canal no encontrado en PeerTube");
        }
        
        return channel;
    }

    /**
     * POST /{id}
     * Operación principal: Obtiene los datos y los envía a VideoMiner.
     */
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED) // Devuelve 201 al crear el recurso con éxito
    public VMChannel postChannel(
            @PathVariable String id,
            @RequestParam(defaultValue = "10") int maxVideos,
            @RequestParam(defaultValue = "2") int maxComments
    ) {
        // 1. Obtener y transformar los datos
        VMChannel channel = service.getChannelFull(id, maxVideos, maxComments);

        // 2. Validar existencia (404 si no existe)
        if (channel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Canal no encontrado en PeerTube");
        }

        // 3. ENVIAR A VIDEOMINER 
        service.sendToVideoMiner(channel);

        return channel;
    }
}