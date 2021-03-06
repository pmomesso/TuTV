using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ApiCrawlerTuTV.Model;
using Npgsql;

namespace ApiCrawlerTuTV {
    public class DatabaseManager {
        private NpgsqlConnection conn;

        public DatabaseManager(string host, int port, string user, string password, string database) {
            string connstring = String.Format("Server={0};Port={1};" +
                    "User Id={2};Password={3};Database={4};",
                    host, port, user, password, database);
            // Making connection with Npgsql provider
            conn = new NpgsqlConnection(connstring);
            conn.Open();
        }

        #region exists
        public bool DoesNetworkExist(Network n) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT EXISTS(SELECT * FROM network WHERE networkId = @id)";
                cmd.Parameters.AddWithValue("id", n.id);
                return (bool)cmd.ExecuteScalar();
            }
        }
        public bool DoesGenreExist(Genre g) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT EXISTS(SELECT * FROM genres WHERE id = @id)";
                cmd.Parameters.AddWithValue("id", g.id);
                return (bool)cmd.ExecuteScalar();
            }
        }
        public bool DoesSeriesExist(Series s) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT EXISTS(SELECT * FROM series WHERE id = @id)";
                cmd.Parameters.AddWithValue("id", s.id);
                return (bool)cmd.ExecuteScalar();
            }
        }
        public bool DoesEpisodeExist(Episode ep) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT EXISTS(SELECT * FROM episode WHERE id = @id)";
                cmd.Parameters.AddWithValue("id", ep.id);
                return (bool)cmd.ExecuteScalar();
            }
        }
        public bool DoesSeasonExist(Season se) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT EXISTS(SELECT * FROM season WHERE seasonid = @id)";
                cmd.Parameters.AddWithValue("id", se.id);
                return (bool)cmd.ExecuteScalar();
            }
        }
        public bool DoesActorExist(Actor a) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT EXISTS(SELECT * FROM actor WHERE id = @id)";
                cmd.Parameters.AddWithValue("id", a.id);
                return (bool)cmd.ExecuteScalar();
            }
        }
        public bool DoesActorRoleExist(ActorRole ar) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT EXISTS(SELECT * FROM actorRoles WHERE seriesId = @seriesId AND actorId = @actorId)";
                cmd.Parameters.AddWithValue("seriesId", ar.series.id);
                cmd.Parameters.AddWithValue("actorId", ar.actor.id);
                return (bool)cmd.ExecuteScalar();
            }
        }
        #endregion


        public int InsertOrUpdateNetwork(Network n) {
            if (DoesNetworkExist(n))
                return n.id;

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                cmd.CommandText = "INSERT INTO network (networkId, name) VALUES (@id, @name) RETURNING networkId";

                cmd.Parameters.AddWithValue("id", n.id);
                cmd.Parameters.AddWithValue("name", n.name);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateGenre(Genre g) {
            if (DoesGenreExist(g))
                return g.id;


            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                cmd.CommandText = "INSERT INTO genres (id, genre) VALUES (@id, @genre) RETURNING id";

                cmd.Parameters.AddWithValue("id", g.id);
                cmd.Parameters.AddWithValue("genre", g.name);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateActor(Actor a) {
            if (DoesActorExist(a))
                return a.id;

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                cmd.CommandText = "INSERT INTO actor (id, name) VALUES (@id, @name) RETURNING id";

                cmd.Parameters.AddWithValue("id", a.id);
                cmd.Parameters.AddWithValue("name", a.name);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateSeason(Season se) {
            if (DoesSeasonExist(se))
                return se.id;

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                cmd.CommandText = "INSERT INTO season (seasonid, seriesId, seasonNumber) VALUES (@id, @seriesId, @seasonNumber) RETURNING seasonid";

                cmd.Parameters.AddWithValue("id", se.id);
                cmd.Parameters.AddWithValue("seriesId",     se.series.id);
                cmd.Parameters.AddWithValue("seasonNumber", se.seasonNumber);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateEpisode(Episode ep) {
            if (DoesEpisodeExist(ep))
                return ep.id;

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                cmd.CommandText = "INSERT INTO episode " +
                    "(id, aired, seriesid, seasonid, numepisode, name, overview, tvdbid)" +
                    " VALUES " +
                    "(@id, @aired, @seriesId, @seasonid, @numepisode, @name, @overview, @tvdbid) " +
                    "RETURNING id";

                cmd.Parameters.AddWithValue("id", ep.id);
                cmd.Parameters.AddWithValue("tvdbid", ep.TvDbId);

                cmd.Parameters.AddWithValue("seriesId",     ep.season.series.id);
                cmd.Parameters.AddWithValue("seasonid",     ep.season.id);
                cmd.Parameters.AddWithValue("numepisode",   ep.episodeNumber);
                
                if(ep.name != null)
                    cmd.Parameters.AddWithValue("name",         ep.name);
                else
                    cmd.Parameters.AddWithValue("name",         DBNull.Value);

                if (ep.aired != null)
                    cmd.Parameters.AddWithValue("aired", ep.aired);
                else
                    cmd.Parameters.AddWithValue("aired", DBNull.Value);

                if (ep.description != null)
                    cmd.Parameters.AddWithValue("overview",     ep.description);
                else
                    cmd.Parameters.AddWithValue("overview",     DBNull.Value);

                return (int)cmd.ExecuteScalar();
            }
        }

        public int? InsertOrUpdateActorRole(ActorRole ar) {
            if (DoesActorRoleExist(ar))
                return ar.id;

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                cmd.CommandText = "INSERT INTO actorRoles " +
                    "(seriesRole, seriesId, actorId) " +
                    "VALUES " +
                    "(@seriesRole, @seriesId, @actorId) " +
                    "RETURNING id";

                cmd.Parameters.AddWithValue("seriesRole",   ar.role);
                cmd.Parameters.AddWithValue("seriesId",     ar.series.id);
                cmd.Parameters.AddWithValue("actorId",      ar.actor.id);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateSeries(Series s) {
            if (DoesSeriesExist(s))
                return s.id;

            int DatabaseSeriesId;

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                cmd.CommandText = "INSERT INTO series " +
                    "(id, tvdbid, name, description, status, runtime, networkid, firstAired, id_imdb, posterurl, bannerurl) " +
                    "VALUES " +
                    "(@id, @tvdbid, @name, @description, @status, @runtime, @networkid, @firstaired, @id_imdb, @posterurl, @bannerurl) " +
                    "RETURNING id";

                cmd.Parameters.AddWithValue("id", s.id);
                cmd.Parameters.AddWithValue("tvdbid", s.tvDbId);
                cmd.Parameters.AddWithValue("name",         s.seriesName);

                if(s.seriesDescription != null)
                    cmd.Parameters.AddWithValue("description",  s.seriesDescription);
                else
                    cmd.Parameters.AddWithValue("description",  DBNull.Value);

                cmd.Parameters.AddWithValue("status",       s.status);
                cmd.Parameters.AddWithValue("runtime",      s.runningTime);
                cmd.Parameters.AddWithValue("networkid",    s.network.id);

                if (s.imbdId != null)
                    cmd.Parameters.AddWithValue("id_imdb", s.imbdId);
                else
                    cmd.Parameters.AddWithValue("id_imdb", DBNull.Value);

                if (s.firstAired != null)
                    cmd.Parameters.AddWithValue("firstaired", s.firstAired);
                else
                    cmd.Parameters.AddWithValue("firstaired", DBNull.Value);

                if (s.posterUrl != null)
                    cmd.Parameters.AddWithValue("posterurl", s.posterUrl);
                else
                    cmd.Parameters.AddWithValue("posterurl",DBNull.Value);

                if (s.bannerUrl != null)
                    cmd.Parameters.AddWithValue("bannerurl", s.bannerUrl);
                else
                    cmd.Parameters.AddWithValue("bannerurl", DBNull.Value);
                DatabaseSeriesId = (int)cmd.ExecuteScalar();
            }

            foreach(Genre g in s.genresList) {
                using(var cmd = new NpgsqlCommand()) {
                    cmd.Connection = conn;
                    cmd.CommandText = "INSERT INTO hasgenre " +
                        "(genreid, seriesid) " +
                        "VALUES " +
                        "(@genreid, @seriesid)";
                    cmd.Parameters.AddWithValue("genreid", g.id);
                    cmd.Parameters.AddWithValue("seriesid", DatabaseSeriesId);

                    cmd.ExecuteNonQuery();
                }
            }

            return (int)DatabaseSeriesId;
        }
    }
}
