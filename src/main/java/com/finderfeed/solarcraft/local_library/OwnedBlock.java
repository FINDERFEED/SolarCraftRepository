package com.finderfeed.solarcraft.local_library;

import java.util.UUID;

public interface OwnedBlock {

    UUID getOwner();

    void setOwner(UUID owner);

}
