package com.finderfeed.solarforge.for_future_library;

import java.util.UUID;

public interface OwnedBlock {

    UUID getOwner();

    void setOwner(UUID owner);

}
