package dungeon3;

import java.util.Map;

/**
 * A player representation in the dungeon model.
 * Player can pick the Treasures present at the current location.
 * Player can also move to the locations which are connected to the current location.
 * Visibility: Package - private
 */
interface Player {
  Map<Treasure, Integer> getCollectedTreasures();

  Location getLocation();

  Location movePlayer(Direction direction);

  void pickAllTreasures();
}
