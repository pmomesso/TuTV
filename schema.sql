--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10 (Ubuntu 10.10-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.10 (Ubuntu 10.10-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE ONLY public.notification DROP CONSTRAINT fksmt0pk89nrmafjy0gpk9obv6w;
ALTER TABLE ONLY public.episode DROP CONSTRAINT fkr5ifuxa82mfaxrhgahps7iu2m;
ALTER TABLE ONLY public.haslikedseriesreviewcomment DROP CONSTRAINT fkpg9qey3u6kx54agsi92llux55;
ALTER TABLE ONLY public.seriesreviewcomments DROP CONSTRAINT fkonk29iekiwb0ln7b2j6w54mfi;
ALTER TABLE ONLY public.follows DROP CONSTRAINT fknsql5bpj38nk5p89nvyiad4pd;
ALTER TABLE ONLY public.notification DROP CONSTRAINT fknk4ftb5am9ubmkv1661h15ds9;
ALTER TABLE ONLY public.follows DROP CONSTRAINT fkla0v85tmvblwypj4dyinm6vk9;
ALTER TABLE ONLY public.season DROP CONSTRAINT fkinfoupkdkuab2jvqx8i8dx05v;
ALTER TABLE ONLY public.serieslist DROP CONSTRAINT fki3v4jv0lix8mkgc5q2s4tu21x;
ALTER TABLE ONLY public.list DROP CONSTRAINT fkg72o5o0gdv8063u82p2bp5qrd;
ALTER TABLE ONLY public.ratings DROP CONSTRAINT fkeutid49wncs5bl4cscul2wwaq;
ALTER TABLE ONLY public.seriesreviewcomments DROP CONSTRAINT fkeeasovb4834h1qn69dwuk59gg;
ALTER TABLE ONLY public.seriesreview DROP CONSTRAINT fkea402qt2xo0favo577r2vkc4o;
ALTER TABLE ONLY public.hasgenre DROP CONSTRAINT fkdnot20b8yhvs0n4yh8e3ecd0b;
ALTER TABLE ONLY public.haslikedseriesreview DROP CONSTRAINT fkct1fjadu4c31quk7krogs0njv;
ALTER TABLE ONLY public.ratings DROP CONSTRAINT fkb3354ee2xxvdrbyq9f42jdayd;
ALTER TABLE ONLY public.haslikedseriesreview DROP CONSTRAINT fk8ortub3ayhgy8i7j2x2intm4b;
ALTER TABLE ONLY public.hasgenre DROP CONSTRAINT fk89ijx8nagl56xkqaqhv67ayvs;
ALTER TABLE ONLY public.seriesreview DROP CONSTRAINT fk7cx44mvnb9d28epbrdn363d4n;
ALTER TABLE ONLY public.hasviewedepisode DROP CONSTRAINT fk6dybi7rrgiptnry1crv8hdj2h;
ALTER TABLE ONLY public.serieslist DROP CONSTRAINT fk6clq7kkl9xmnvx8mn0xj8k78s;
ALTER TABLE ONLY public.hasviewedepisode DROP CONSTRAINT fk4foxmk3eqd02cpsos319qmfr3;
ALTER TABLE ONLY public.series DROP CONSTRAINT fk3orvdh23tltxo2hs88fgrmvl3;
ALTER TABLE ONLY public.haslikedseriesreviewcomment DROP CONSTRAINT fk2kipp0ifwuweuuv9p1hoepqas;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
ALTER TABLE ONLY public.seriesreviewcomments DROP CONSTRAINT seriesreviewcomments_pkey;
ALTER TABLE ONLY public.seriesreview DROP CONSTRAINT seriesreview_pkey;
ALTER TABLE ONLY public.serieslist DROP CONSTRAINT serieslist_pkey;
ALTER TABLE ONLY public.series DROP CONSTRAINT series_pkey;
ALTER TABLE ONLY public.season DROP CONSTRAINT season_pkey;
ALTER TABLE ONLY public.ratings DROP CONSTRAINT ratings_pkey;
ALTER TABLE ONLY public.notification DROP CONSTRAINT notification_pkey;
ALTER TABLE ONLY public.network DROP CONSTRAINT network_pkey;
ALTER TABLE ONLY public.list DROP CONSTRAINT list_pkey;
ALTER TABLE ONLY public.hasviewedepisode DROP CONSTRAINT hasviewedepisode_pkey;
ALTER TABLE ONLY public.haslikedseriesreviewcomment DROP CONSTRAINT haslikedseriesreviewcomment_pkey;
ALTER TABLE ONLY public.haslikedseriesreview DROP CONSTRAINT haslikedseriesreview_pkey;
ALTER TABLE ONLY public.hasgenre DROP CONSTRAINT hasgenre_pkey;
ALTER TABLE ONLY public.genres DROP CONSTRAINT genres_pkey;
ALTER TABLE ONLY public.follows DROP CONSTRAINT follows_pkey;
ALTER TABLE ONLY public.episode DROP CONSTRAINT episode_pkey;
DROP SEQUENCE public.users_id_seq;
DROP TABLE public.users;
DROP SEQUENCE public.seriesreviewcomments_id_seq;
DROP TABLE public.seriesreviewcomments;
DROP SEQUENCE public.seriesreview_id_seq;
DROP TABLE public.seriesreview;
DROP TABLE public.serieslist;
DROP SEQUENCE public.series_id_seq;
DROP TABLE public.series;
DROP SEQUENCE public.season_id_seq;
DROP TABLE public.season;
DROP SEQUENCE public.ratings_id_seq;
DROP TABLE public.ratings;
DROP SEQUENCE public.notification_id_seq;
DROP TABLE public.notification;
DROP SEQUENCE public.network_id_seq;
DROP TABLE public.network;
DROP SEQUENCE public.list_id_seq;
DROP TABLE public.list;
DROP TABLE public.hasviewedepisode;
DROP TABLE public.haslikedseriesreviewcomment;
DROP TABLE public.haslikedseriesreview;
DROP TABLE public.hasgenre;
DROP TABLE public.genres;
DROP SEQUENCE public.genre_id_seq;
DROP TABLE public.follows;
DROP SEQUENCE public.episode_id_seq;
DROP TABLE public.episode;
/*
DROP EXTENSION plpgsql;
DROP SCHEMA public;
--
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
*/

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: episode; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.episode (
    id bigint NOT NULL,
    aired date,
    name character varying(255) NOT NULL,
    numepisode integer NOT NULL,
    overview character varying(2048) NOT NULL,
    series_id bigint,
    tvdbid bigint,
    season_id bigint NOT NULL
);


--
-- Name: episode_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.episode_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: follows; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.follows (
    seriesid bigint NOT NULL,
    userid bigint NOT NULL
);


--
-- Name: genre_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.genre_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: genres; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.genres (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    i18key character varying(255)
);


--
-- Name: hasgenre; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.hasgenre (
    seriesid bigint NOT NULL,
    genreid bigint NOT NULL
);


--
-- Name: haslikedseriesreview; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.haslikedseriesreview (
    seriesreview bigint NOT NULL,
    userid bigint NOT NULL
);


--
-- Name: haslikedseriesreviewcomment; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.haslikedseriesreviewcomment (
    seriesreviewcomment bigint NOT NULL,
    userid bigint NOT NULL
);


--
-- Name: hasviewedepisode; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.hasviewedepisode (
    episodeid bigint NOT NULL,
    userid bigint NOT NULL
);


--
-- Name: list; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.list (
    id bigint NOT NULL,
    name character varying(50),
    listuser_id bigint NOT NULL
);


--
-- Name: list_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.list_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: network; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.network (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


--
-- Name: network_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.network_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: notification; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.notification (
    id bigint NOT NULL,
    message character varying(255),
    viewed boolean NOT NULL,
    resource_id bigint,
    user_id bigint
);


--
-- Name: notification_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.notification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: ratings; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ratings (
    id bigint NOT NULL,
    rating integer NOT NULL,
    series_id bigint NOT NULL,
    user_id bigint NOT NULL
);


--
-- Name: ratings_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ratings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: season; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.season (
    id bigint NOT NULL,
    name character varying(255),
    seasonnumber integer NOT NULL,
    series_id bigint NOT NULL
);


--
-- Name: season_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.season_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: series; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.series (
    id bigint NOT NULL,
    added date,
    bannerurl character varying(255) NOT NULL,
    firstaired date,
    followers integer DEFAULT 0,
    imdbid character varying(64),
    name character varying(255) NOT NULL,
    posterurl character varying(255) NOT NULL,
    runtime integer,
    description character varying(2048) NOT NULL,
    status character varying(16) NOT NULL,
    tvdbid integer,
    updated date,
    userrating double precision,
    network_id bigint NOT NULL
);


--
-- Name: series_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.series_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: serieslist; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.serieslist (
    listid bigint NOT NULL,
    seriesid bigint NOT NULL
);


--
-- Name: seriesreview; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.seriesreview (
    id bigint NOT NULL,
    body character varying(255) NOT NULL,
    isspam boolean DEFAULT false NOT NULL,
    numlikes integer DEFAULT 0,
    series_id bigint NOT NULL,
    user_id bigint NOT NULL
);


--
-- Name: seriesreview_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seriesreview_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seriesreviewcomments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.seriesreviewcomments (
    id bigint NOT NULL,
    body character varying(255) NOT NULL,
    numlikes integer DEFAULT 0,
    parent_id bigint NOT NULL,
    user_id bigint NOT NULL
);


--
-- Name: seriesreviewcomments_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seriesreviewcomments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    birthdate timestamp without time zone,
    confirmation_key character varying(60),
    isadmin boolean DEFAULT false NOT NULL,
    isbanned boolean DEFAULT false NOT NULL,
    mail character varying(255),
    password character varying(255),
    avatar bytea,
    username character varying(50)
);


--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: episode episode_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.episode
    ADD CONSTRAINT episode_pkey PRIMARY KEY (id);


--
-- Name: follows follows_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.follows
    ADD CONSTRAINT follows_pkey PRIMARY KEY (seriesid, userid);


--
-- Name: genres genres_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.genres
    ADD CONSTRAINT genres_pkey PRIMARY KEY (id);


--
-- Name: hasgenre hasgenre_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.hasgenre
    ADD CONSTRAINT hasgenre_pkey PRIMARY KEY (seriesid, genreid);


--
-- Name: haslikedseriesreview haslikedseriesreview_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.haslikedseriesreview
    ADD CONSTRAINT haslikedseriesreview_pkey PRIMARY KEY (seriesreview, userid);


--
-- Name: haslikedseriesreviewcomment haslikedseriesreviewcomment_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.haslikedseriesreviewcomment
    ADD CONSTRAINT haslikedseriesreviewcomment_pkey PRIMARY KEY (seriesreviewcomment, userid);


--
-- Name: hasviewedepisode hasviewedepisode_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.hasviewedepisode
    ADD CONSTRAINT hasviewedepisode_pkey PRIMARY KEY (episodeid, userid);


--
-- Name: list list_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.list
    ADD CONSTRAINT list_pkey PRIMARY KEY (id);


--
-- Name: network network_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.network
    ADD CONSTRAINT network_pkey PRIMARY KEY (id);


--
-- Name: notification notification_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (id);


--
-- Name: ratings ratings_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT ratings_pkey PRIMARY KEY (id);


--
-- Name: season season_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.season
    ADD CONSTRAINT season_pkey PRIMARY KEY (id);


--
-- Name: series series_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.series
    ADD CONSTRAINT series_pkey PRIMARY KEY (id);


--
-- Name: serieslist serieslist_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.serieslist
    ADD CONSTRAINT serieslist_pkey PRIMARY KEY (listid, seriesid);


--
-- Name: seriesreview seriesreview_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.seriesreview
    ADD CONSTRAINT seriesreview_pkey PRIMARY KEY (id);


--
-- Name: seriesreviewcomments seriesreviewcomments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.seriesreviewcomments
    ADD CONSTRAINT seriesreviewcomments_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: haslikedseriesreviewcomment fk2kipp0ifwuweuuv9p1hoepqas; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.haslikedseriesreviewcomment
    ADD CONSTRAINT fk2kipp0ifwuweuuv9p1hoepqas FOREIGN KEY (seriesreviewcomment) REFERENCES public.seriesreviewcomments(id);


--
-- Name: series fk3orvdh23tltxo2hs88fgrmvl3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.series
    ADD CONSTRAINT fk3orvdh23tltxo2hs88fgrmvl3 FOREIGN KEY (network_id) REFERENCES public.network(id);


--
-- Name: hasviewedepisode fk4foxmk3eqd02cpsos319qmfr3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.hasviewedepisode
    ADD CONSTRAINT fk4foxmk3eqd02cpsos319qmfr3 FOREIGN KEY (episodeid) REFERENCES public.episode(id);


--
-- Name: serieslist fk6clq7kkl9xmnvx8mn0xj8k78s; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.serieslist
    ADD CONSTRAINT fk6clq7kkl9xmnvx8mn0xj8k78s FOREIGN KEY (seriesid) REFERENCES public.series(id);


--
-- Name: hasviewedepisode fk6dybi7rrgiptnry1crv8hdj2h; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.hasviewedepisode
    ADD CONSTRAINT fk6dybi7rrgiptnry1crv8hdj2h FOREIGN KEY (userid) REFERENCES public.users(id);


--
-- Name: seriesreview fk7cx44mvnb9d28epbrdn363d4n; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.seriesreview
    ADD CONSTRAINT fk7cx44mvnb9d28epbrdn363d4n FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: hasgenre fk89ijx8nagl56xkqaqhv67ayvs; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.hasgenre
    ADD CONSTRAINT fk89ijx8nagl56xkqaqhv67ayvs FOREIGN KEY (genreid) REFERENCES public.genres(id);


--
-- Name: haslikedseriesreview fk8ortub3ayhgy8i7j2x2intm4b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.haslikedseriesreview
    ADD CONSTRAINT fk8ortub3ayhgy8i7j2x2intm4b FOREIGN KEY (userid) REFERENCES public.users(id);


--
-- Name: ratings fkb3354ee2xxvdrbyq9f42jdayd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT fkb3354ee2xxvdrbyq9f42jdayd FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: haslikedseriesreview fkct1fjadu4c31quk7krogs0njv; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.haslikedseriesreview
    ADD CONSTRAINT fkct1fjadu4c31quk7krogs0njv FOREIGN KEY (seriesreview) REFERENCES public.seriesreview(id);


--
-- Name: hasgenre fkdnot20b8yhvs0n4yh8e3ecd0b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.hasgenre
    ADD CONSTRAINT fkdnot20b8yhvs0n4yh8e3ecd0b FOREIGN KEY (seriesid) REFERENCES public.series(id);


--
-- Name: seriesreview fkea402qt2xo0favo577r2vkc4o; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.seriesreview
    ADD CONSTRAINT fkea402qt2xo0favo577r2vkc4o FOREIGN KEY (series_id) REFERENCES public.series(id);


--
-- Name: seriesreviewcomments fkeeasovb4834h1qn69dwuk59gg; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.seriesreviewcomments
    ADD CONSTRAINT fkeeasovb4834h1qn69dwuk59gg FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: ratings fkeutid49wncs5bl4cscul2wwaq; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT fkeutid49wncs5bl4cscul2wwaq FOREIGN KEY (series_id) REFERENCES public.series(id);


--
-- Name: list fkg72o5o0gdv8063u82p2bp5qrd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.list
    ADD CONSTRAINT fkg72o5o0gdv8063u82p2bp5qrd FOREIGN KEY (listuser_id) REFERENCES public.users(id);


--
-- Name: serieslist fki3v4jv0lix8mkgc5q2s4tu21x; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.serieslist
    ADD CONSTRAINT fki3v4jv0lix8mkgc5q2s4tu21x FOREIGN KEY (listid) REFERENCES public.list(id);


--
-- Name: season fkinfoupkdkuab2jvqx8i8dx05v; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.season
    ADD CONSTRAINT fkinfoupkdkuab2jvqx8i8dx05v FOREIGN KEY (series_id) REFERENCES public.series(id);


--
-- Name: follows fkla0v85tmvblwypj4dyinm6vk9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.follows
    ADD CONSTRAINT fkla0v85tmvblwypj4dyinm6vk9 FOREIGN KEY (userid) REFERENCES public.users(id);


--
-- Name: notification fknk4ftb5am9ubmkv1661h15ds9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fknk4ftb5am9ubmkv1661h15ds9 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: follows fknsql5bpj38nk5p89nvyiad4pd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.follows
    ADD CONSTRAINT fknsql5bpj38nk5p89nvyiad4pd FOREIGN KEY (seriesid) REFERENCES public.series(id);


--
-- Name: seriesreviewcomments fkonk29iekiwb0ln7b2j6w54mfi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.seriesreviewcomments
    ADD CONSTRAINT fkonk29iekiwb0ln7b2j6w54mfi FOREIGN KEY (parent_id) REFERENCES public.seriesreview(id);


--
-- Name: haslikedseriesreviewcomment fkpg9qey3u6kx54agsi92llux55; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.haslikedseriesreviewcomment
    ADD CONSTRAINT fkpg9qey3u6kx54agsi92llux55 FOREIGN KEY (userid) REFERENCES public.users(id);


--
-- Name: episode fkr5ifuxa82mfaxrhgahps7iu2m; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.episode
    ADD CONSTRAINT fkr5ifuxa82mfaxrhgahps7iu2m FOREIGN KEY (season_id) REFERENCES public.season(id);


--
-- Name: notification fksmt0pk89nrmafjy0gpk9obv6w; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fksmt0pk89nrmafjy0gpk9obv6w FOREIGN KEY (resource_id) REFERENCES public.series(id);


--
-- PostgreSQL database dump complete
--

