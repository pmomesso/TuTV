create table if not exists users
(
    id serial not null
        constraint users_pkey
            primary key,
    username varchar(32),
    password varchar(32),
    mail varchar(32)
);

alter table users owner to root;

create table if not exists genres
(
    id serial not null
        constraint genres_pk
            primary key,
    genre varchar(255),
    constraint genres_id_genre_key
        unique (id, genre)
);

alter table genres owner to root;

create table if not exists seriesgenre
(
    genreid integer
        constraint genreid
            references genres
);

alter table seriesgenre owner to root;

create table if not exists actor
(
    id serial not null
        constraint actor_pk
            primary key,
    name varchar(255),
    created date,
    updated varchar(32),
    status varchar(32)
);

alter table actor owner to root;

create table if not exists commentepisode
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
            references commentepisode
);

alter table commentepisode owner to root;

create table if not exists network
(
    networkid serial not null
        constraint network_pk
            primary key,
    name varchar(255)
);

alter table network owner to root;

create table if not exists series
(
    id serial not null
        constraint series_pkey
            primary key,
    tvdbid integer,
    name varchar(255),
    description text,
    userrating double precision,
    status varchar(16),
    runtime integer,
    networkid integer
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

create table if not exists seriesairing
(
    seriesid integer not null
        constraint seriesid
            references series,
    dayofweek integer,
    time time,
    country varchar(255)
);

alter table seriesairing owner to root;

create table if not exists actorroles
(
    id serial not null
        constraint actorroles_pkey
            primary key,
    seriesrole varchar(255),
    actorid integer
        constraint actor
            references actor,
    tvdbid integer,
    seriesid integer
        constraint seriesid
            references series,
    created date,
    updated date
);

alter table actorroles owner to root;

create table if not exists seriesreview
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
    id serial not null
        constraint seriesreview_pk
            primary key
);

alter table seriesreview owner to root;

create table if not exists season
(
    seasonid serial not null
        constraint season_pk
            primary key,
    seriesid integer not null
        constraint season_series_id_fk
            references series,
    seasonnumber integer not null
);

alter table season owner to root;

create table if not exists episode
(
    id serial not null
        constraint episode_pkey
            primary key,
    name varchar(255),
    overview text,
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

create table if not exists seriesreviewcomments
(
    id serial not null
        constraint seriesreviewcomments_pk
            primary key,
    body varchar(512),
    userid integer
        constraint seriesreviewcomments_users_id_fk
            references users,
    postid integer
        constraint seriesreviewcomments_seriesreview_id_fk
            references seriesreview,
    points integer default 0
);

alter table seriesreviewcomments owner to root;

