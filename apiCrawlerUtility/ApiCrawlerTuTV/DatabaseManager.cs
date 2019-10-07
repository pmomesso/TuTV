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

        public int? GetActorIdByName(string Name) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT id FROM actor WHERE name = @name";
                cmd.Parameters.AddWithValue("name", Name);
                return (int?)cmd.ExecuteScalar();
            }
        }

        public int? GetGenreIdByName(string Name) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT id FROM genres WHERE genre = @genre";
                cmd.Parameters.AddWithValue("genre", Name);
                return (int?)cmd.ExecuteScalar();
            }
        }

        public int? GetNetworkIdByName(string Name) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT networkId FROM network WHERE name = @name";
                cmd.Parameters.AddWithValue("name", Name);
                return (int?)cmd.ExecuteScalar();
            }
        }

        public int? GetSeriesIdByTvDbId(int TvDbId) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT id FROM series WHERE tvdbid = @tvdbid";
                cmd.Parameters.AddWithValue("tvdbid", TvDbId);
                return (int?)cmd.ExecuteScalar();
            }
        }

        public int? GetEpisodeIdByTvDbId(int TvDbId) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT id FROM episode WHERE tvdbid = @tvdbid";
                cmd.Parameters.AddWithValue("tvdbid", TvDbId);
                return (int?)cmd.ExecuteScalar();
            }
        }

        public int? GetActorRoleIdByTvDbId(int TvDbId) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT id FROM actorRoles WHERE tvdbid = @tvdbid";
                cmd.Parameters.AddWithValue("tvdbid", TvDbId);
                return (int?)cmd.ExecuteScalar();
            }
        }

        public int? GetSeasonIdBySeriesIdAndSeasonNumber(int SeriesId, int SeasonNumber) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT seasonid FROM season WHERE seriesId = @seriesId AND seasonNumber = @seasonNumber";
                cmd.Parameters.AddWithValue("seriesId",     SeriesId);
                cmd.Parameters.AddWithValue("seasonNumber", SeasonNumber);
                return (int?)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateNetwork(Network n) {
            int? DatabaseNetworkId = GetNetworkIdByName(n.Name);

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                if (DatabaseNetworkId == null) {
                    cmd.CommandText = "INSERT INTO network (name) VALUES (@name) RETURNING networkId";
                }
                else {
                    cmd.CommandText = "UPDATE network SET name = @name WHERE networkId = @networkId RETURNING networkId";
                    cmd.Parameters.AddWithValue("networkId", DatabaseNetworkId);
                }

                cmd.Parameters.AddWithValue("name", n.Name);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateGenre(Genre g) {
            int? DatabaseGenreId = GetGenreIdByName(g.name);

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                if (DatabaseGenreId == null) {
                    cmd.CommandText = "INSERT INTO genres (genre) VALUES (@genre) RETURNING id";
                }
                else {
                    cmd.CommandText = "UPDATE genres SET genre = @genre WHERE id = @id RETURNING id";
                    cmd.Parameters.AddWithValue("id", DatabaseGenreId);
                }

                cmd.Parameters.AddWithValue("genre", g.name);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateActor(Actor a) {
            int? DatabaseActorId = GetActorIdByName(a.name);

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                if(DatabaseActorId == null) {
                    cmd.CommandText = "INSERT INTO actor (name) VALUES (@name) RETURNING id";
                } else {
                    cmd.CommandText = "UPDATE actor SET name = @name WHERE id = @id RETURNING id";
                    cmd.Parameters.AddWithValue("id", DatabaseActorId);
                }

                cmd.Parameters.AddWithValue("name", a.name);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateSeason(Season se) {
            int? DatabaseSeasonId = GetSeasonIdBySeriesIdAndSeasonNumber(se.series.id, se.seasonNumber);

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                if (DatabaseSeasonId == null) {
                    cmd.CommandText = "INSERT INTO season (seriesId, seasonNumber) VALUES (@seriesId, @seasonNumber) RETURNING seasonid";
                }
                else {
                    cmd.CommandText = "UPDATE season SET seriesId = @seriesId, seasonNumber = @seasonNumber WHERE seasonid = @seasonid RETURNING seasonid";
                    cmd.Parameters.AddWithValue("seasonid", DatabaseSeasonId);
                }

                cmd.Parameters.AddWithValue("seriesId",     se.series.id);
                cmd.Parameters.AddWithValue("seasonNumber", se.seasonNumber);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateEpisode(Episode ep) {
            int? DatabaseEpisodeId = GetEpisodeIdByTvDbId(ep.TvDbId);

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                if (DatabaseEpisodeId == null) {
                    cmd.CommandText = "INSERT INTO episode " +
                        "(seriesid, seasonid, numepisode, name, overview, tvdbid)" +
                        " VALUES " +
                        "(@seriesId, @seasonid, @numepisode, @name, @overview, @tvdbid) " +
                        "RETURNING id";
                    cmd.Parameters.AddWithValue("tvdbid", ep.TvDbId);
                }
                else {
                    cmd.CommandText = "UPDATE episode SET " +
                        "seriesId = @seriesId, seasonid = @seasonid, numepisode = @numepisode, name = @name, overview = @overview " +
                        "WHERE id = @id RETURNING id";
                    cmd.Parameters.AddWithValue("id", DatabaseEpisodeId);
                }

                cmd.Parameters.AddWithValue("seriesId",     ep.season.series.id);
                cmd.Parameters.AddWithValue("seasonid",     ep.season.id);
                cmd.Parameters.AddWithValue("numepisode",   ep.episodeNumber);
                
                if(ep.name != null)
                    cmd.Parameters.AddWithValue("name",         ep.name);
                else
                    cmd.Parameters.AddWithValue("name",         DBNull.Value);

                if (ep.description != null)
                    cmd.Parameters.AddWithValue("overview",     ep.description);
                else
                    cmd.Parameters.AddWithValue("overview",     DBNull.Value);

                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateActorRole(ActorRole ar) {
            int? DatabaseActorRoleId = GetActorRoleIdByTvDbId(ar.tvDbId);

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                if (DatabaseActorRoleId == null) {
                    cmd.CommandText = "INSERT INTO actorRoles " +
                        "(seriesRole, tvDbId, seriesId, actorId) " +
                        "VALUES " +
                        "(@seriesRole, @tvDbId, @seriesId, @actorId) " +
                        "RETURNING id";
                    cmd.Parameters.AddWithValue("tvDbId", ar.tvDbId);
                } else {
                    cmd.CommandText = "UPDATE actorRoles SET " +
                        "seriesRole = @seriesRole, seriesId = @seriesId, actorId = @actorId " +
                        "WHERE id = @id " +
                        "RETURNING id";
                    cmd.Parameters.AddWithValue("id", DatabaseActorRoleId);
                }

                cmd.Parameters.AddWithValue("seriesRole",   ar.role);
                cmd.Parameters.AddWithValue("seriesId",     ar.series.id);
                cmd.Parameters.AddWithValue("actorId",      ar.actor.id);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateSeries(Series s) {
            int? DatabaseSeriesId = GetSeriesIdByTvDbId(s.tvDbId);

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                //Inserto serie
                if (DatabaseSeriesId == null) {
                    cmd.CommandText = "INSERT INTO series " +
                        "(tvdbid, name, description, status, runtime, networkid, firstAired, id_imdb, posterurl, bannerurl) " +
                        "VALUES " +
                        "(@tvdbid, @name, @description, @status, @runtime, @networkid, @firstaired, @id_imdb, @posterurl, @bannerurl) " +
                        "RETURNING id";
                    cmd.Parameters.AddWithValue("tvdbid", s.tvDbId);
                } else {
                    cmd.CommandText = "UPDATE series SET " +
                        "name = @name, description = @description, status = @status, runtime = @runtime, networkid = @networkid, " +
                        "firstaired = @firstaired, id_imdb = @id_imdb, posterurl = @posterurl, bannerurl = @bannerurl " +
                        "WHERE id = @id " +
                        "RETURNING id";
                    cmd.Parameters.AddWithValue("id", DatabaseSeriesId);
                }
                cmd.Parameters.AddWithValue("name",         s.seriesName);

                if(s.seriesDescription != null)
                    cmd.Parameters.AddWithValue("description",  s.seriesDescription);
                else
                    cmd.Parameters.AddWithValue("description",  DBNull.Value);

                cmd.Parameters.AddWithValue("status",       s.status);
                cmd.Parameters.AddWithValue("runtime",      s.runningTime);
                cmd.Parameters.AddWithValue("networkid",    s.network.id);
                cmd.Parameters.AddWithValue("id_imdb",      s.imbdId);

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

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "DELETE FROM hasgenre WHERE seriesId = @seriesId";
                cmd.Parameters.AddWithValue("seriesId", DatabaseSeriesId);

                cmd.ExecuteNonQuery();
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
