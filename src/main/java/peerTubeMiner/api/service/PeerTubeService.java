package peerTubeMiner.api.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import peerTubeMiner.api.mapper.PeerTubeCaptionMapper;
import peerTubeMiner.api.mapper.PeerTubeChannelMapper;
import peerTubeMiner.api.mapper.PeerTubeCommentMapper;
import peerTubeMiner.api.mapper.PeerTubeVideoMapper;
import peerTubeMiner.api.model.peerTube.CommentsThreads;
import peerTubeMiner.api.model.peerTube.PeerTubeCaption;
import peerTubeMiner.api.model.peerTube.PeerTubeChannel;
import peerTubeMiner.api.model.peerTube.PeerTubeVideo;
import peerTubeMiner.api.model.peerTube.VideoData;
import peerTubeMiner.api.model.videoMiner.VMChannel;
import peerTubeMiner.api.model.videoMiner.VMVideo;

@Service
public class PeerTubeService {

    private final PeerTubeClient client;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${videominer.uri}")
    private String videoMinerBaseUrl;

    public PeerTubeService(PeerTubeClient client) {
        this.client = client;
    }

    public VMChannel getChannelFull(String channelId, int maxVideos, int maxComments) {
        // 1. Canal
        PeerTubeChannel ptChannel = client.getChannel(channelId);
        if (ptChannel == null) return null; // Para que el controller lance el 404

        VMChannel channel = PeerTubeChannelMapper.toDomain(ptChannel);

        // 2. Videos (wrapper → lista)
        PeerTubeVideo ptVideos = client.getVideos(channelId, maxVideos);
        List<VMVideo> videos = PeerTubeVideoMapper.toDomainList(ptVideos.getData());

        // 3. Enriquecer cada video
        for (int i = 0; i < videos.size(); i++) {
            VMVideo video = videos.get(i);
            VideoData original = ptVideos.getData().get(i);
            String videoId = String.valueOf(original.getId());

            // 🔹 COMMENTS
            CommentsThreads threads = client.getComments(videoId, maxComments);
            video.setComments(
                    PeerTubeCommentMapper.toDomainList(threads.getData())
            );

            // 🔹 CAPTIONS
            PeerTubeCaption captions = client.getCaptions(videoId);
            video.setCaptions(
                    PeerTubeCaptionMapper.toDomainList(captions.getData())
            );
        }

        // 4. Set videos al canal
        channel.setVideos(videos);

        return channel;
    }

    // Enviar a VideoMiner
    public void sendToVideoMiner(VMChannel channel) {
        try {
            restTemplate.postForObject(videoMinerBaseUrl + "/api/channels", channel, VMChannel.class);
            System.out.println("Canal de PeerTube enviado con éxito a VideoMiner");
        } catch (Exception e) {
            System.err.println("Error al conectar con VideoMiner: " + e.getMessage());
        }
    }

}