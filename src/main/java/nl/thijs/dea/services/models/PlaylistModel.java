package nl.thijs.dea.services.models;

public class PlaylistModel {
    private int id;
    private String name;
    private boolean owner;
    private int[] tracks;
    private int length;

    public PlaylistModel(int id, String name, boolean owner, int[] tracks) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
        this.length = 37;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public int[] getTracks() {
        return tracks;
    }

    public void setTracks(int[] tracks) {
        this.tracks = tracks;
    }
}
