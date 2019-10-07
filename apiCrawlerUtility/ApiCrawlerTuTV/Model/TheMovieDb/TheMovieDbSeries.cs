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
        public DateTime first_air_date { get; set; }
        public List<Genre> genres { get; set; }
        public string homepage { get; set; }
        public int id { get; set; }
        public bool in_production { get; set; }
        public List<string> languages { get; set; }
        public string last_air_date { get; set; }
        public Episode last_episode_to_air { get; set; }
        public string name { get; set; }
        public object next_episode_to_air { get; set; }
        public List<Network> networks { get; set; }
        public int number_of_episodes { get; set; }
        public int number_of_seasons { get; set; }
        public List<string> origin_country { get; set; }
        public string original_language { get; set; }
        public string original_name { get; set; }
        public string overview { get; set; }
        public double popularity { get; set; }
        public string poster_path { get; set; }
        public List<ProductionCompany> production_companies { get; set; }
        public List<Season> seasons { get; set; }
        public string status { get; set; }
        public string type { get; set; }
        public double vote_average { get; set; }
        public int vote_count { get; set; }

        public int CompareTo(TheMovieDbSeries other) {
            return this.id.CompareTo(other.id);
        }

        public Series ToSeries(HashSet<Model.Genre> gl, HashSet<Model.Network> nl, HashSet<Model.Actor> al) {
            Model.Series s = new Model.Series();

            s.seriesName = this.original_name;
            s.firstAired = this.first_air_date;
            //s.actorList = 
            s.bannerUrl = this.backdrop_path;
            //s.genresList =
            //s.imbdId = this.
            s.networkList
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

    public class Genre : IComparable<Genre> {
        public int id { get; set; }
        public string name { get; set; }

        public int CompareTo(Genre other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class Network : IComparable<Network> {
        public string name { get; set; }
        public int id { get; set; }
        public string logo_path { get; set; }
        public string origin_country { get; set; }

        public int CompareTo(Network other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class ProductionCompany : IComparable<ProductionCompany> {
        public int id { get; set; }
        public string logo_path { get; set; }
        public string name { get; set; }
        public string origin_country { get; set; }

        public int CompareTo(ProductionCompany other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class Crew : IComparable<Crew> {
        public int id { get; set; }
        public string credit_id { get; set; }
        public string name { get; set; }
        public string department { get; set; }
        public string job { get; set; }
        public int gender { get; set; }
        public string profile_path { get; set; }

        public int CompareTo(Crew other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class GuestStar : IComparable<GuestStar> {
        public int id { get; set; }
        public string name { get; set; }
        public string credit_id { get; set; }
        public string character { get; set; }
        public int order { get; set; }
        public int gender { get; set; }
        public string profile_path { get; set; }

        public int CompareTo(GuestStar other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class Episode : IComparable<Episode> {
        public string air_date { get; set; }
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
        public List<Crew> crew { get; set; }
        public List<GuestStar> guest_stars { get; set; }

        public int CompareTo(Episode other) {
             return this.id.CompareTo(other.id);
        }
    }

    public class Season : IComparable<Season> {
        public string _id { get; set; }
        public int id { get; set; }
        public string air_date { get; set; }
        public List<Episode> episodes { get; set; }
        public int episode_count { get; set; }
        public string name { get; set; }
        public string overview { get; set; }
        public string poster_path { get; set; }
        public int season_number { get; set; }

        public int CompareTo(Season other) {
             return this.id.CompareTo(other.id);
        }
    }
}
