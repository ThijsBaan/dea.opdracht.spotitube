package nl.thijs.dea.datasources.dao;

import nl.thijs.dea.datasources.DatabaseConnection;
import nl.thijs.dea.services.models.PlaylistModel;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayListDAO{
    private Connection connection;
    private int totalPlaylistLength;

    @Inject
    public PlayListDAO(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
    }

    public int getTotalPlaylistLength() {
        return totalPlaylistLength;
    }

    public List<PlaylistModel> loadPlaylists(String token) {
        try {

            PreparedStatement playlistSt = connection.prepareStatement("SELECT ID, " +
                    "                    Name, " +
                    "                    CASE WHEN (SELECT [Username] FROM Token WHERE Token = ?) = [Owner]  " +
                    "                    THEN 1 ELSE 0 END AS [Owner], " +
                    "                    (SELECT SUM(Duration) FROM Tracks t where t.ID in (SELECT Track FROM tracksInPlaylist WHERE Playlist = p.ID)) as [Duration]  " +
                    "                    FROM Playlist p ");
            playlistSt.setString(1, token);

            ResultSet r = playlistSt.executeQuery();

            int[] emptyArray = new int[0];

            var playlistList = new ArrayList<PlaylistModel>();

            while (r.next()) {
                playlistList.add(new PlaylistModel(r.getInt("ID"), r.getString(
                        "Name"),
                        r.getBoolean("Owner"), emptyArray));
                totalPlaylistLength += r.getInt("duration");
            }

            return playlistList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addPlaylist(String token, String playlistNaam) {
        try {
            PreparedStatement addPlaylistSt = connection.prepareStatement("INSERT INTO Playlist ([Name], [Owner]) " +
                    "VALUES(?, (SELECT Username FROM Token WHERE Token = ?))");
            addPlaylistSt.setString(1, playlistNaam);
            addPlaylistSt.setString(2, token);

            addPlaylistSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlaylist(String token, int playlistId) {
        try {
            PreparedStatement deletePlaylistSt = connection.prepareStatement("DELETE FROM playlist " +
                    "WHERE id = ? AND [Owner] = (SELECT Username FROM Token WHERE Token = ?)");
            deletePlaylistSt.setInt(1, playlistId);
            deletePlaylistSt.setString(2, token);

            deletePlaylistSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editPlaylist(String token, int id, String newName) {
        try {
            PreparedStatement editPlaylistSt = connection.prepareStatement("UPDATE Playlist " +
                    "SET [Name] = ? " +
                    "WHERE id = ? AND [Owner] = (SELECT Username FROM Token WHERE Token = ?)");
            editPlaylistSt.setString(1, newName);
            editPlaylistSt.setInt(2, id);
            editPlaylistSt.setString(3, token);

            editPlaylistSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
