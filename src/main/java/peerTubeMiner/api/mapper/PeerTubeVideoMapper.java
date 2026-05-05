package peerTubeMiner.api.mapper;

import java.util.ArrayList;
import java.util.List;

import peerTubeMiner.api.model.peerTube.VideoData;
import peerTubeMiner.api.model.videoMiner.VMVideo;

public class PeerTubeVideoMapper {

    public static VMVideo toDomain(VideoData source) {
        if (source == null) return null;

        VMVideo target = new VMVideo();
        target.setId(String.valueOf(source.getId()));
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setReleaseTime(bestTime(source.getPublishedAt(), source.getCreatedAt()));
        target.setAuthor(PeerTubeUserMapper.fromAccount(source.getAccount()));
        target.setComments(new ArrayList<>());
        target.setCaptions(new ArrayList<>());
        return target;
    }

    public static List<VMVideo> toDomainList(List<VideoData> source) {
        List<VMVideo> result = new ArrayList<>();
        if (source == null) return result;

        for (VideoData item : source) {
            result.add(toDomain(item));
        }
        return result;
    }

    private static String bestTime(String publishedAt, String createdAt) {
        return (publishedAt != null && !publishedAt.isBlank()) ? publishedAt : createdAt;
    }
}