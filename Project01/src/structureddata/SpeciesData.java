package structureddata;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Structured Data containing a species' all the housings in the sanctuary.
 */
public class SpeciesData implements Comparable<SpeciesData> {
  private final String species;
  private final List<String> isolation;
  private final List<String> enclosure;

  /**
   * Constructor for SpeciesData class.
   *
   * @param species the name of the species
   */
  public SpeciesData(String species) {
    this.species = species;
    this.isolation = new ArrayList<>();
    this.enclosure = new ArrayList<>();
  }

  /**
   * Adds a new isolation.
   *
   * @param isolationName name of the isolation
   */
  public void addIsolationName(String isolationName) {
    isolation.add(isolationName);
  }

  /**
   * Adds a new enclosure.
   *
   * @param enclosureName name of the enclosure
   */
  public void addEnclosureName(String enclosureName) {
    enclosure.add(enclosureName);
  }

  /**
   * Gives the species name.
   *
   * @return species name
   */
  public String getSpecies() {
    return species;
  }

  /**
   * Gives the list of all the isolation names.
   *
   * @return list of isolation names
   */
  public List<String> getIsolation() {
    return isolation;
  }

  /**
   * Gives a list of all the enclosure names.
   *
   * @return list of enclosure names
   */
  public List<String> getEnclosure() {
    return enclosure;
  }

  /**
   * String representation of SpeciesData.
   *
   * @return String representation of SpeciesData
   */
  @Override
  public String toString() {
    return String.format("Species: %s, Isolations: %s, Enclosures: %s",
            species, isolation, enclosure);
  }

  @Override
  public int compareTo(SpeciesData o) {
    return this.species.compareTo(o.species);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof SpeciesData) {
      SpeciesData other = (SpeciesData) o;
      if (!species.equals(other.species) || isolation.size() != other.isolation.size()
              || enclosure.size() != other.enclosure.size()) {
        return false;
      }
      for (int i = 0; i < isolation.size(); i++) {
        if (!isolation.get(i).equals(other.isolation.get(i))) {
          return false;
        }
      }
      for (int i = 0; i < enclosure.size(); i++) {
        if (!enclosure.get(i).equals(other.enclosure.get(i))) {
          return false;
        }
      }
      return true;
    }
    return false;

  }

  @Override
  public int hashCode() {
    return Objects.hash(species, isolation, enclosure);
  }
}
