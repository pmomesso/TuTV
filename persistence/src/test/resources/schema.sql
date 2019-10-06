create table if not exists users
(
    id identity not null
        constraint users_pkey
            primary key,
    username varchar(50),
    password varchar(255),
    mail varchar(50) unique,
    confirmation_key varchar(60),
    isbanned boolean default false,
    isadmin boolean default false,
    avatar binary
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

create table if not exists seriesgenre
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

create table if not exists episodereview
(
    userid integer
        constraint commentepisode_users_id_fk
            references users,
    body varchar(256),
    episodeid integer,
    numlikes integer,
    id identity not null
        constraint commentepisode_pk
            primary key
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
    tvdbid integer,
    name varchar(255),
    description varchar(255),
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

create table if not exists seriesairing
(
    seriesid integer not null
        constraint seriesid
            references series,
    dayofweek integer,
    time time,
    country varchar(255)
);

create table if not exists actorroles
(
    id identity not null
        constraint actorroles_pkey
            primary key,
    seriesrole varchar(255),
    actorid integer
        constraint actor
            references actor,
    tvdbid integer,
    seriesid integer
        constraint actorroles_seriesid
            references series,
    created date,
    updated date
);

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
    numlikes integer default 0,
    id identity not null
        constraint seriesreview_pk
            primary key
);

create table if not exists season
(
    seasonid identity not null
        constraint season_pk
            primary key,
    seriesid integer not null
        constraint season_series_id_fk
            references series,
    seasonnumber integer not null
);

create table if not exists episode
(
    id identity not null
        constraint episode_pkey
            primary key,
    name varchar(255),
    overview varchar(255),
    seriesid integer
        constraint episode_series_id_fk
            references series,
    numepisode integer not null,
    tvdbid integer not null,
    seasonid integer not null
        constraint episode_season_seasonid_fk
            references season,
    aired date
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

create table if not exists seriesreviewcomments
(
    id identity not null
        constraint seriesreviewcomments_pk
            primary key,
    body varchar(512),
    userid integer
        constraint seriesreviewcomments_users_id_fk
            references users
            on delete cascade,
    postid integer
        constraint seriesreviewcomments_seriesreview_id_fk
            references seriesreview
            on delete cascade,
    numlikes integer default 0
);

create table if not exists episodereviewcomment
(
    id identity not null
        constraint episodereviewcomment_pk
            primary key,
    body varchar(512),
    numlikes integer default 0,
    episodereview integer
        constraint episodereviewcomment_episodereview_id_fk
            references episodereview
);

create table if not exists hasviewedepisode
(
    userid integer
        constraint hasviewedepisode_users_id_fk
            references users
            on delete cascade,
    episodeid integer
        constraint hasviewedepisode_episode_id_fk
            references episode
            on delete cascade,
    id identity not null
        constraint hasviewedepisode_pk
            primary key
);

create table if not exists haslikedseriesreview
(
    seriesreview integer
        constraint haslikedseriesreview_seriesreview_id_fk
            references seriesreview
            on delete cascade,
    userid integer
        constraint haslikedseriesreview_users_id_fk
            references users
            on delete cascade
);

create table if not exists haslikedseriesreviewcomment
(
    seriesreviewcomment integer
        constraint haslikedseriesreviewcomment_seriesreviewcomments_id_fk
            references seriesreviewcomments
            on delete cascade,
    userid integer
        constraint haslikedseriesreviewcomment_users_id_fk
            references users
            on delete cascade
);

create table if not exists userseriesrating
(
    userid integer
        constraint userseriesrating_users_id_fk
            references users
            on delete cascade,
    seriesid integer
        constraint userseriesrating_series_id_fk
            references series
            on delete cascade,
    rating double precision not null,
    constraint user_series_rating_unique
        unique (userid, seriesid)
);



