package peerTubeMiner.api.mapper;

import java.util.ArrayList;
import java.util.List;

import peerTubeMiner.api.model.peerTube.CommentData;
import peerTubeMiner.api.model.videoMiner.VMComment;

public class PeerTubeCommentMapper {

    public static List<VMComment> toDomainList(List<CommentData> source) {
        List<VMComment> result = new ArrayList<>();
        if (source == null) return result;
        for (CommentData item : source) {
            result.add(toDomain(item));
        }
        return result;
    
    }

    public static VMComment toDomain(CommentData source) {
        if (source == null) return null;

        VMComment target = new VMComment();

        target.setId(source.getId() != null ? source.getId().toString() : null);
        target.setText(source.getText());
        target.setCreatedOn(source.getCreatedAt());

        return target;
    }
}