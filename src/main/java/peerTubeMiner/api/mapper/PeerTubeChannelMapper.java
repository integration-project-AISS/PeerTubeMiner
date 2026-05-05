package peerTubeMiner.api.mapper;

import java.util.ArrayList;

import peerTubeMiner.api.model.peerTube.PeerTubeChannel;
import peerTubeMiner.api.model.videoMiner.VMChannel;

public class PeerTubeChannelMapper {

    public static VMChannel toDomain(PeerTubeChannel source) {
        if (source == null) return null;

        VMChannel target = new VMChannel();
        target.setId(String.valueOf(source.getId()));
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setCreatedTime(source.getCreatedAt());
        target.setVideos(new ArrayList<>());
        
        return target;
    }
}