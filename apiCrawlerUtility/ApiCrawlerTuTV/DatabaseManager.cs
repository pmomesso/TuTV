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
                cmd.CommandText = "SELECT id FROM actors WHERE name = @name";
                cmd.Parameters.AddWithValue("name", Name);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int? GetSeriesIdByTvDbId(int TvDbId) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT id FROM series WHERE tvdbid = @tvdbid";
                cmd.Parameters.AddWithValue("tvdbid", TvDbId);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int? GetEpisodeIdByTvDbId(int TvDbId) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT id FROM episode WHERE tvdbid = @tvdbid";
                cmd.Parameters.AddWithValue("tvdbid", TvDbId);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int? GetActorRoleIdByTvDbId(int TvDbId) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT id FROM actorRoles WHERE tvdbid = @tvdbid";
                cmd.Parameters.AddWithValue("tvdbid", TvDbId);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int? GetSeasonIdBySeriesIdAndSeasonNumber(int SeriesId, int SeasonNumber) {
            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;
                cmd.CommandText = "SELECT id FROM season WHERE seriesId = @seriesId AND seasonNumber = @seasonNumber";
                cmd.Parameters.AddWithValue("seriesId",     SeriesId);
                cmd.Parameters.AddWithValue("seasonNumber", SeasonNumber);
                return (int)cmd.ExecuteScalar();
            }
        }

        public int InsertOrUpdateActor(Actor a) {
            int? DatabaseActorId = GetActorIdByName(a.name);

            using (var cmd = new NpgsqlCommand()) {
                cmd.Connection = conn;

                if(DatabaseActorId == null) {
                    cmd.CommandText = "INSERT INTO actors (name, tvDbId) VALUES (@name, @tvdbid) RETURNING id";
                    cmd.Parameters.AddWithValue("tvdbid",   a.tvDbId);
                } else {
                    cmd.CommandText = "UPDATE actors SET name = @name WHERE id = @id RETURNING id";
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
                    cmd.CommandText = "INSERT INTO season (seriesId, seasonNumber) VALUES (@seriesId, @seasonNumber) RETURNING id";
                }
                else {
                    cmd.CommandText = "UPDATE actors SET seriesId = @seriesId, seasonNumber = @seasonNumber WHERE id = @id RETURNING id";
                    cmd.Parameters.AddWithValue("id", DatabaseSeasonId);
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
                    cmd.CommandText = "INSERT INTO season " +
                        "(seriesid, seasonid, numepisode, name, overview)" +
                        " VALUES " +
                        "(@seriesId, @seasonid, @numepisode, @name, @overview) " +
                        "RETURNING id";
                }
                else {
                    cmd.CommandText = "UPDATE actors SET " +
                        "seriesId = @seriesId, seasonid = @seasonid, numepisode = @numepisode, name = @name, overview = @overview " +
                        "WHERE id = @id RETURNING id";
                    cmd.Parameters.AddWithValue("id", DatabaseEpisodeId);
                }

                cmd.Parameters.AddWithValue("seriesId",     ep.season.series.id);
                cmd.Parameters.AddWithValue("seasonid",     ep.season.id);
                cmd.Parameters.AddWithValue("numepisode",   ep.episodeNumber);
                cmd.Parameters.AddWithValue("name",         ep.name);
                cmd.Parameters.AddWithValue("overview",     ep.description);
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
                        "seriesRole = @seriesRole, seriesId = @seriesId, actorId = @actorId" +
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

                if (DatabaseSeriesId == null) {
                    cmd.CommandText = "INSERT INTO series " +
                        "(tvDbId, name, description, status, runtime, networkId, firstAired, id_imdb, posterUrl, bannerUrl) " +
                        "VALUES " +
                        "(@tvdbid, @name, @description, @status, @runtime, @networkId, @firstAired, @id_imdb, @posterUrl, @bannerUrl) " +
                        "RETURNING id";
                    cmd.Parameters.AddWithValue("tvdbid", s.tvDbId);
                } else {
                    cmd.CommandText = "UPDATE series SET " +
                        "name = @name, description = @description, status = @status, runtime = @runtime, networkId = @networkId, " +
                        "firstAired = @firstAired, id_imdb = @id_imdb, posterUrl = @posterUrl, bannerUrl = @bannerUrl) " +
                        "WHERE id = @id " +
                        "RETURNING id";
                    cmd.Parameters.AddWithValue("id", DatabaseSeriesId);
                }

                cmd.Parameters.AddWithValue("name",         s.seriesName);
                cmd.Parameters.AddWithValue("description",  s.seriesDescription);
                cmd.Parameters.AddWithValue("status",       s.status);
                cmd.Parameters.AddWithValue("runtime",      s.runningTime);
                cmd.Parameters.AddWithValue("networkId",    s.network);
                cmd.Parameters.AddWithValue("firstAired",   s.firstAired);
                cmd.Parameters.AddWithValue("id_imdb",      s.imbdId);
                cmd.Parameters.AddWithValue("posterUrl",    s.posterUrl);
                cmd.Parameters.AddWithValue("bannerUrl",    s.bannerUrl);
                return (int)cmd.ExecuteScalar();
            }
        }
    }
}
