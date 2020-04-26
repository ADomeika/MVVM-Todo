package dev.domeika.todo.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TodoLocation {
    @ColumnInfo
    @PrimaryKey
    @NonNull
    public String locationId;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private double latitude;

    @ColumnInfo
    private double longitude;

    public TodoLocation() {}

    public TodoLocation(String id, String name, double latitude, double longitude) {
        this.locationId = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return locationId;
    }

    public void setId(String id) {
        this.locationId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
