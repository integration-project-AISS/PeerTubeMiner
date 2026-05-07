package peerTubeMiner.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import peerTubeMiner.api.model.peerTube.CommentsThreads;
import peerTubeMiner.api.model.peerTube.PeerTubeCaption;
import peerTubeMiner.api.model.peerTube.PeerTubeChannel;
import peerTubeMiner.api.model.peerTube.PeerTubeVideo;

@Component
public class PeerTubeClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${peertubeminer.baseuri}")
    private String baseUri; // https://peertube2.cpy.re/api/v1

    public PeerTubeChannel getChannel(String handle) {
        String url = baseUri + "/video-channels/" + handle;
        try {
            return restTemplate.getForObject(url, PeerTubeChannel.class);
        } catch (HttpClientErrorException.NotFound e) {
            // Si PeerTube dice que no existe, devolvemos null
            return null;
        } catch (Exception e) {
            // Otros errores (red, 500 de PeerTube, etc.)
            System.err.println("Error consultando PeerTube: " + e.getMessage());
            return null;
        }
    }

    public PeerTubeVideo getVideos(String handle, int count) {
        String url = baseUri + "/video-channels/"
                + handle + "/videos?count=" + count;

        return restTemplate.getForObject(url, PeerTubeVideo.class);
    }

    public CommentsThreads getComments(String videoId, int count) {
        String url = baseUri + "/videos/"
                + videoId + "/comment-threads?count=" + count;

        return restTemplate.getForObject(url, CommentsThreads.class);
    }

    public PeerTubeCaption getCaptions(String videoId) {
        String url = baseUri + "/videos/"
                + videoId + "/captions";

        return restTemplate.getForObject(url, PeerTubeCaption.class);
    }

}