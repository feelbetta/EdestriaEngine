package com.edestria.engine.guilds;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class Guild {

    private String created;
    private String name;
    private UUID leader;
    private Set<UUID> members = new HashSet<>();

    public Guild() {

    }

    public Guild(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.equals(this) || obj instanceof Guild && ((Guild) obj).getName().equals(this.getName());
    }
}
