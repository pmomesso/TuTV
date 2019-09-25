create table if not exists users
(
	id identity not null
		constraint users_pkey
			primary key,
	username varchar(32),
	password varchar(32)
);

create table if not exists genres
(
	id identity not null
		constraint genres_pk
			primary key,
	genre varchar(255),
	constraint genres_id_genre_key
		unique (id, genre)
);

create table if not exists seriesGenre
(
	genreid integer
		constraint genreid
			references genres
);

create table if not exists actor
(
	id identity not null
		constraint actor_pk
			primary key,
	name varchar(255),
	created date,
	updated varchar(32),
	status varchar(32)
);

create table if not exists commentEpisode
(
	userid integer
		constraint commentepisode_users_id_fk
			references users,
	body varchar(256),
	episodeid integer,
	parent integer,
	points integer,
	id identity not null
		constraint commentepisode_pk
			primary key
		constraint commentepisode_commentepisode_id_fk
			references commentEpisode
);

create table if not exists network
(
	networkid identity not null
		constraint network_pk
			primary key,
	name varchar(255)
);

create table if not exists series
(
	id identity not null
		constraint series_pkey
			primary key,
	tvDbId integer,
	name varchar(255),
	description varchar(512),
	userRating double precision,
	status varchar(16),
	runtime integer,
	networkId integer
		constraint series_network_networkid_fk
			references network,
	firstaired date,
	id_imdb varchar(64),
	added date,
	updated date,
	posterurl varchar(256),
	followers integer default 0 not null,
	bannerurl varchar(256)
);

create table if not exists seriesAiring
(
	seriesid integer not null
		constraint seriesid
			references series,
	dayofweek integer,
	time time,
	country varchar(255)
);

create table if not exists actorRoles
(
    id identity not null
	    primary key,
	seriesrole varchar(255),
	actorid integer
		constraint actor
			references actor,
	tvDbId integer,
	seriesid integer
		constraint seriesid_constraint
			references series,
	created date,
	updated date
);

create table if not exists seriesReview
(
	userid integer
		constraint seriesreview_users_id_fk
			references users,
	language varchar(255),
	body varchar(256),
	seriesid integer
		constraint seriesreview_series_id_fk
			references series,
	points integer,
	new_column integer
);

create table if not exists season
(
	seasonid identity not null
		constraint season_pk
			primary key,
	seriesid integer not null
		constraint season_series_id_fk
			references series,
	seasonNumber integer not null
);

create table if not exists episode
(
    id identity not null
		primary key,
	name varchar(255),
	overview varchar(512),
	seriesid integer
		constraint episode_series_id_fk
			references series,
	numepisode integer not null,
	tvdbid integer not null,
	seasonid integer not null
		constraint episode_season_seasonid_fk
			references season
);

create table if not exists follows
(
	userid integer not null
		constraint follows_users_id_fk
			references users,
	seriesid integer not null
		constraint follows_series_id_fk
			references series
);

create table if not exists hasgenre
(
	seriesid integer
		constraint hasgenre_series_id_fk
			references series,
	genreid integer
		constraint hasgenre_genres_id_fk
			references genres
);
