package dev.fresult.railwayManagement.common.enums;

public enum RoleName {
  ADMIN(1),
  STATION_STAFF(2),
  PASSENGER(3);

  private final int id;

  RoleName(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
