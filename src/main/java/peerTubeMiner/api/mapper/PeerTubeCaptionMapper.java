package peerTubeMiner.api.mapper;

import java.util.ArrayList;
import java.util.List;

import peerTubeMiner.api.model.peerTube.CaptionData;
import peerTubeMiner.api.model.videoMiner.VMCaption;

public class PeerTubeCaptionMapper {

    public static VMCaption toDomain(CaptionData source) {
        if (source == null) return null;

        VMCaption target = new VMCaption();

        target.setId(
            source.getCaptionPath() != null 
                ? source.getCaptionPath() 
                : source.getFileUrl()
        );

        target.setName(
            source.getLanguage() != null 
                ? source.getLanguage().getLabel() 
                : source.getCaptionPath()
        );

        target.setLanguage(
            source.getLanguage() != null 
                ? source.getLanguage().getId() 
                : null
        );

        return target;
    }

    public static List<VMCaption> toDomainList(List<CaptionData> source) {
        List<VMCaption> result = new ArrayList<>();
        if (source == null) return result;
        for (CaptionData item : source) {
            result.add(toDomain(item));
        }
        return result;
    }
}