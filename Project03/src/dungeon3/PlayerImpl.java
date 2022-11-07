package dungeon3;

import java.util.Map;
import java.util.TreeMap;

/**
 * Representation of a player in the dungeon.
 * Implements method required for the player to carry out in a dungeon.
 * Visibility: Package - private
 */
class PlayerImpl implements Player {
  private final Map<Treasure, Integer> collectedTreasures;
  private Location currentLocation;

  protected PlayerImpl(Location currentLocation) {
    if (currentLocation == null) {
      throw new IllegalArgumentException("Start Point cannot be null.");
    } else if (!currentLocation.isCave()) {
      throw new IllegalArgumentException("Start Point cannot be a tunnel");
    }
    this.collectedTreasures = new TreeMap<>();
    this.currentLocation = currentLocation;
  }

  @Override
  public Map<Treasure, Integer> getCollectedTreasures() {
    return new TreeMap<>(collectedTreasures);
  }

  @Override
  public Location getLocation() {
    return currentLocation;
  }

  @Override
  public Location movePlayer(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    if (!currentLocation.getConnections().containsKey(direction)) {
      throw new IllegalArgumentException(
              String.format("Direction %s is not possible from here.", direction));
    }
    currentLocation = currentLocation.getConnections().get(direction);
    return currentLocation;
  }

  @Override
  public void pickAllTreasures() {
    Map<Treasure, Integer> locationTreasure = currentLocation.getTreasureMap();
    for (Treasure treasure : locationTreasure.keySet()) {
      int value = locationTreasure.get(treasure);
      collectedTreasures.put(treasure, collectedTreasures.getOrDefault(treasure, 0) + value);
      currentLocation.removeTreasure(treasure);
    }
  }
}
