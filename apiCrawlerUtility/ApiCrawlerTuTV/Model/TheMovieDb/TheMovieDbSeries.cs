using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiCrawlerTuTV.Model.TheMovieDb {
    class TheMovieDbSeries : IComparable<TheMovieDbSeries> {
        public string backdrop_path { get; set; }
        public List<CreatedBy> created_by { get; set; }
        public List<int> episode_run_time { get; set; }
        public DateTime? first_air_date { get; set; }
        public List<TheMovieDbGenre> genres { get; set; }
        public string homepage { get; set; }
        public int id { get; set; }
        public bool in_production { get; set; }
        public List<string> languages { get; set; }
        public string last_air_date { get; set; }
        public TheMovieDbEpisode last_episode_to_air { get; set; }
        public string name { get; set; }
        public object next_episode_to_air { get; set; }
        public List<TheMovieDbNetwork> networks { get; set; }
        public int number_of_episodes { get; set; }
        public int number_of_seasons { get; set; }
        public List<string> origin_country { get; set; }
        public string original_language { get; set; }
        public string original_name { get; set; }
        public string overview { get; set; }
        public double popularity { get; set; }
        public string poster_path { get; set; }
        public List<TheMovieDbProductionCompany> production_companies { get; set; }
        public List<TheMovieDbSeason> seasons { get; set; }
        public string status { get; set; }
        public string type { get; set; }
        public double vote_average { get; set; }
        public int vote_count { get; set; }

        public int CompareTo(TheMovieDbSeries other) {
            return this.id.CompareTo(other.id);
        }

        public Series ToSeries() {
            Model.Series s = new Model.Series();

            s.seriesName = this.original_name;
            s.firstAired = this.first_air_date;
            //s.actorList = /series/id/credits
            s.bannerUrl = this.backdrop_path;

            s.genresList = new List<Genre>();
            foreach (TheMovieDbGenre mdbg in this.genres)
                s.genresList.Add(mdbg.ToGenre());

            s.id = this.id;

            //TODO mas que una?
            if (this.networks.Count > 0)
                s.network = this.networks[0].ToNetwork();

            s.posterUrl = this.poster_path;

            //TODO promedio?
            if(this.episode_run_time.Count > 0)
                s.runningTime = this.episode_run_time[0];

            s.seasonList = new List<Season>();
            foreach (TheMovieDbSeason mdbs in this.seasons) {
                Season se = mdbs.ToSeason();
                se.series = s;
                s.seasonList.Add(se);
            }

            s.seriesDescription = this.overview;

            s.seriesName = this.name;

            s.status = this.status;

            s.tvDbId = this.id;

            return s;
        }
    }

    public class CreatedBy : IComparable<CreatedBy> {
        public int id { get; set; }
        public string credit_id { get; set; }
        public string name { get; set; }
        public int gender { get; set; }
        public object profile_path { get; set; }

        public int CompareTo(CreatedBy other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class TheMovieDbGenre : IComparable<TheMovieDbGenre> {
        public int id { get; set; }
        public string name { get; set; }

        public int CompareTo(TheMovieDbGenre other) {
             return this.id.CompareTo(other.id);
        }
        public Model.Genre ToGenre() {
            return new Model.Genre {
                id = this.id,
                name = this.name
            };
        }
    }

    public class TheMovieDbNetwork : IComparable<TheMovieDbNetwork> {
        public string name { get; set; }
        public int id { get; set; }
        public string logo_path { get; set; }
        public string origin_country { get; set; }

        public Model.Network ToNetwork() {
            return new Model.Network {
                id = this.id,
                name = this.name
            };
        }

        public int CompareTo(TheMovieDbNetwork other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class TheMovieDbProductionCompany : IComparable<TheMovieDbProductionCompany> {
        public int id { get; set; }
        public string logo_path { get; set; }
        public string name { get; set; }
        public string origin_country { get; set; }

        public int CompareTo(TheMovieDbProductionCompany other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class TheMovieDbCrew : IComparable<TheMovieDbCrew> {
        public int id { get; set; }
        public string credit_id { get; set; }
        public string name { get; set; }
        public string department { get; set; }
        public string job { get; set; }
        public int gender { get; set; }
        public string profile_path { get; set; }

        public int CompareTo(TheMovieDbCrew other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class TheMovieDbCast : IComparable<TheMovieDbCast> {
        public string character { get; set; }
        public string credit_id { get; set; }
        public int id { get; set; }
        public string name { get; set; }
        public int gender { get; set; }
        public string profile_path { get; set; }
        public int order { get; set; }

        public ActorRole ToActorRole() {
            return new ActorRole {
                actor = new Actor {
                    name = this.name,
                    id = this.id,
                    tvDbId = this.id
                },
                imageUrl = this.profile_path,
                role = this.character
            };
        }

        public int CompareTo(TheMovieDbCast other) {
            return this.id.CompareTo(other.id);
        }
    }

    public class TheMovieDbEpisode : IComparable<TheMovieDbEpisode> {
        public DateTime? air_date { get; set; }
        public int episode_number { get; set; }
        public int id { get; set; }
        public string name { get; set; }
        public string overview { get; set; }
        public string production_code { get; set; }
        public int season_number { get; set; }
        public int show_id { get; set; }
        public string still_path { get; set; }
        public double vote_average { get; set; }
        public int vote_count { get; set; }
        public List<TheMovieDbCrew> crew { get; set; }
        public List<TheMovieDbCast> guest_stars { get; set; }

        public Model.Episode ToEpisode() {
            Episode e = new Episode();
            e.description = this.overview;
            e.episodeNumber = this.episode_number;
            e.id = this.id;
            e.name = this.name;
            e.seasonNumber = this.season_number;
            e.TvDbId = this.id;
            e.aired = this.air_date;
            return e;
        }

        public int CompareTo(TheMovieDbEpisode other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class TheMovieDbSeason : IComparable<TheMovieDbSeason> {
        public string _id { get; set; }
        public int id { get; set; }
        public string air_date { get; set; }
        public List<TheMovieDbEpisode> episodes { get; set; }
        public int episode_count { get; set; }
        public string name { get; set; }
        public string overview { get; set; }
        public string poster_path { get; set; }
        public int season_number { get; set; }

        public Model.Season ToSeason() {
            return new Model.Season {
                id = this.id,
                name = this.name,
                seasonNumber = this.season_number
            };
        }

        public int CompareTo(TheMovieDbSeason other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class TheMovieDbCredits : IComparable<TheMovieDbCredits> {
        public List<TheMovieDbCast> cast { get; set; }
        //public List<Crew> crew { get; set; }
        public int id { get; set; }

        public int CompareTo(TheMovieDbCredits other) {
            return this.id.CompareTo(other.id);
        }
    }
}
