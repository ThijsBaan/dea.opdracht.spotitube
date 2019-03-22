GO
USE MASTER 
GO

DROP DATABASE IF EXISTS dea_spotitube
GO
CREATE DATABASE dea_spotitube
GO

USE dea_spotitube
GO

CREATE TYPE dt_username
FROM VARCHAR(255)
CREATE TYPE dt_trackid
FROM int
CREATE TYPE dt_playlistid
FROM int
GO

CREATE TABLE [Login](
Username			dt_username		NOT NULL,
[Password]			VARCHAR(255)	NOT NULL
CONSTRAINT pk_login PRIMARY KEY (Username)
)
GO

CREATE TABLE [Token] (
Username			dt_username		NOT NULL,
Token				VARCHAR(15)		NOT NULL,
CONSTRAINT pk_token PRIMARY KEY (Username, Token),
CONSTRAINT fk_token FOREIGN KEY (Username) REFERENCES [Login] (Username),
CONSTRAINT ak_token_us UNIQUE(Username),
CONSTRAINT ak_token_tk UNIQUE(Token)
)
GO

CREATE TABLE [Tracks](
ID					dt_trackid		IDENTITY,
Title				VARCHAR(255)	NOT NULL,
Performer			VARCHAR(255)	NOT NULL,
Duration			INT				NOT NULL,
Album				VARCHAR(255)	NULL,
Playcount			INT				DEFAULT(0) NOT NULL,
PublicationDate		DATE			NULL,
[Description]		VARCHAR(255)	NULL,
CONSTRAINT pk_tracks PRIMARY KEY (ID)
)
GO

CREATE TABLE [Playlist](
ID					dt_playlistid	IDENTITY,
[Name]				VARCHAR(255)	NOT NULL,
[Owner]				dt_username		NOT NULL,
CONSTRAINT pk_playlist PRIMARY KEY (ID)
)
GO

CREATE TABLE [followingPlaylist](
Playlist		dt_playlistid	NOT NULL,
Follower		dt_username		NOT NULL
CONSTRAINT pk_followingplaylist PRIMARY KEY (Playlist, Follower),
CONSTRAINT fk_fp_p FOREIGN KEY (Playlist) REFERENCES [Playlist] (ID),
CONSTRAINT fk_fp_f FOREIGN KEY (Follower) REFERENCES [Login] (Username)
)
GO

CREATE TABLE [tracksInPlaylist](
Playlist		dt_playlistid	NOT NULL,
Track			dt_trackid		NOT NULL,
OfflineAvailable	BIT			NOT NULL
CONSTRAINT pk_tip PRIMARY KEY (Playlist, Track),
CONSTRAINT fk_tip_p FOREIGN KEY (Playlist) REFERENCES [Playlist] (ID),
CONSTRAINT fk_tip_t FOREIGN KEY (Track) REFERENCES [Tracks] (ID)
)
GO

INSERT INTO [Login] (Username,[Password])
VALUES('thijs', 'baan'),
	  ('piet', 'zwart')
GO

INSERT INTO [Tracks] ([Title], [Performer], [Duration], [Album], [PublicationDate], [Description])
VALUES	('Ocean and a rock', 'Lisa Hannigan', 337, 'Sea sew', NULL, NULL),
		('So Long, Marianne', 'Leonard Cohen', 546, 'Songs of Leonad Cohen', NULL, NULL)
INSERT INTO [Tracks] ([Title], [Performer], [Duration], [Album], [Playcount], [PublicationDate], [Description])
VALUES	('One', 'Metallica', 423, NULL, 37, '1-11-2001', 'Long Version')
GO

INSERT INTO [Playlist] ([Name], [Owner])
VALUES	('Heavy Metal', 'thijs'),
		('Pop', 'zwartepiet')
GO

INSERT INTO [followingPlaylist] ([Playlist], [Follower])
VALUES  (2, 'thijs')
GO

INSERT INTO [tracksInPlaylist] ([Playlist], [Track], [OfflineAvailable])
VALUES	(1, 1, 1),
		(1, 2, 0),
		(2, 1, 0),
		(2, 2, 1),
		(2, 3, 0)
GO

