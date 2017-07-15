package com.edestria.engine.eplayers;

import com.edestria.engine.ranks.Rank;
import com.edestria.engine.utils.time.Time;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class EPlayer {

    private UUID uuid;
    private Rank rank;

    private String lastLogin;

    private String guild;

    private Set<UUID> friends;

    public EPlayer(UUID uuid) {
        this.uuid = uuid;
        this.rank = Rank.DEFAULT;
        this.lastLogin = Time.formatDate(new Date());
    }

    public EPlayer() {
        //this(UUID.fromString("f8170b78-b8ca-49ea-98f8-a9220d59924d"));
    }
}
