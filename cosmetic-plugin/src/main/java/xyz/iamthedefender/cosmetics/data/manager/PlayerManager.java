package xyz.iamthedefender.cosmetics.data.manager;

import lombok.Getter;
import xyz.iamthedefender.cosmetics.data.PlayerData;
import xyz.iamthedefender.cosmetics.data.PlayerOwnedData;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

@Getter
public class PlayerManager {

    // Zmiana na ConcurrentHashMap dla bezpieczeństwa wielowątkowego
    private final ConcurrentHashMap<UUID, PlayerData> playerDataHashMap;
    private final ConcurrentHashMap<UUID, PlayerOwnedData> playerOwnedDataHashMap;

    public PlayerManager() {
        playerDataHashMap = new ConcurrentHashMap<>();
        playerOwnedDataHashMap = new ConcurrentHashMap<>();
    }


    public void addPlayerData(PlayerData playerData) {
        playerDataHashMap.put(playerData.getUuid(), playerData);
    }

    public void addPlayerOwnedData(PlayerOwnedData playerOwnedData) {
        playerOwnedDataHashMap.put(playerOwnedData.getUuid(), playerOwnedData);
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerDataHashMap.computeIfAbsent(uuid, PlayerData::new);
    }

    public PlayerOwnedData getPlayerOwnedData(UUID uuid) {
        PlayerOwnedData playerOwnedData = playerOwnedDataHashMap.computeIfAbsent(uuid, PlayerOwnedData::new);
        playerOwnedData.load();
        return playerOwnedData;
    }
}
