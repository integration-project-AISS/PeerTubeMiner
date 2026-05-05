package peerTubeMiner.api.mapper;

import java.util.List;

import peerTubeMiner.api.model.peerTube.Account;
import peerTubeMiner.api.model.peerTube.Avatar;
import peerTubeMiner.api.model.peerTube.OwnerAccount;
import peerTubeMiner.api.model.videoMiner.VMUser;

public class PeerTubeUserMapper {

    public static VMUser fromAccount(Account source) {
        if (source == null) return null;

        VMUser target = new VMUser();
        target.setName(bestName(source.getDisplayName(), source.getName()));
        target.setUser_link(source.getUrl());
        target.setPicture_link(firstAvatarUrl(source.getAvatars()));
        return target;
    }

    public static VMUser fromOwnerAccount(OwnerAccount source) {
        if (source == null) return null;

        VMUser target = new VMUser();
        target.setName(bestName(source.getDisplayName(), source.getName()));
        target.setUser_link(source.getUrl());
        target.setPicture_link(firstAvatarUrl(source.getAvatars()));
        return target;
    }

    private static String bestName(String displayName, String name) {
        return (displayName != null && !displayName.isBlank()) ? displayName : name;
    }

    private static String firstAvatarUrl(List<Avatar> avatars) {
        if (avatars == null || avatars.isEmpty()) return null;
        return avatars.get(0).getFileUrl();
    }
}