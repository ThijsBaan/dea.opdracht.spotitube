package nl.thijs.dea.dummy;

public class DummyPlaylists {
    private int id;
    private String name;
    private boolean owner;
    private String tracks;
    private int length;

    public DummyPlaylists(int id, String name, boolean owner, String tracks) {
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

    public String getTracks() {
        return tracks;
    }

    public void setTracks(String tracks) {
        this.tracks = tracks;
    }
}