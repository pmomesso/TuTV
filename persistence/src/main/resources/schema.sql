create table if not exists users
(
	id serial not null
		constraint users_pkey
			primary key,
	username varchar(32),
	password varchar(32)
);

alter table users owner to root;

create table if not exists genres
(
	id serial not null
		constraint genres_pk
			primary key,
	genre varchar(16),
	constraint genres_id_genre_key
		unique (id, genre)
);

alter table genres owner to root;

create table if not exists "seriesGenre"
(
	genreid integer
		constraint genreid
			references genres
);

alter table "seriesGenre" owner to root;

create table if not exists actor
(
	id serial not null
		constraint actor_pk
			primary key,
	name varchar(32),
	created date,
	updated varchar(32),
	status varchar(32)
);

alter table actor owner to root;

create table if not exists "commentEpisode"
(
	userid integer
		constraint commentepisode_users_id_fk
			references users,
	body varchar(256),
	episodeid integer,
	parent integer,
	points integer,
	id serial not null
		constraint commentepisode_pk
			primary key
		constraint commentepisode_commentepisode_id_fk
			references "commentEpisode"
);

alter table "commentEpisode" owner to root;

create table if not exists network
(
	networkid serial not null
		constraint network_pk
			primary key,
	name varchar(32)
);

alter table network owner to root;

create table if not exists series
(
	id serial not null
		constraint series_pkey
			primary key,
	tvDbId integer,
	name varchar(32),
	description varchar(256),
	"userRating" double precision,
	status varchar(16),
	runtime integer,
	"networkId" integer
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

alter table series owner to root;

create table if not exists "seriesAiring"
(
	seriesid integer not null
		constraint seriesid
			references series,
	dayofweek integer,
	time time,
	country varchar(32)
);

alter table "seriesAiring" owner to root;

create table if not exists "actorRoles"
(
	seriesrole varchar(32),
	actorid integer
		constraint actor
			references actor,
	tvDbId integer,
	seriesid integer
		constraint seriesid
			references series,
	created date,
	updated date
);

alter table "actorRoles" owner to root;

create table if not exists "seriesReview"
(
	userid integer
		constraint seriesreview_users_id_fk
			references users,
	language varchar(32),
	body varchar(256),
	seriesid integer
		constraint seriesreview_series_id_fk
			references series,
	points integer,
	new_column integer
);

alter table "seriesReview" owner to root;

create table if not exists season
(
	seasonid integer not null
		constraint season_pk
			primary key,
	seriesid integer not null
		constraint season_series_id_fk
			references series,
	seasonNumber integer not null
);

alter table season owner to root;

create table if not exists episode
(
	name varchar(32),
	overview varchar(32),
	seriesid integer
		constraint episode_series_id_fk
			references series,
	numepisode integer not null,
	tvdbid integer not null,
	seasonid integer not null
		constraint episode_season_seasonid_fk
			references season
);

alter table episode owner to root;

create table if not exists follows
(
	userid integer not null
		constraint follows_users_id_fk
			references users,
	seriesid integer not null
		constraint follows_series_id_fk
			references series
);

alter table follows owner to root;

create table if not exists hasgenre
(
	seriesid integer
		constraint hasgenre_series_id_fk
			references series,
	genreid integer
		constraint hasgenre_genres_id_fk
			references genres
);

alter table hasgenre owner to root;