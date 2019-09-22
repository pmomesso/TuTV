using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiCrawlerTuTV.Model.ExternalApi {
    public class TheTvDbEpisode {
        public int id { get; set; }
        public int airedSeason { get; set; }
        public int airedSeasonID { get; set; }
        public int airedEpisodeNumber { get; set; }
        public string episodeName { get; set; }
        public string firstAired { get; set; }
        public List<string> guestStars { get; set; }
        public string director { get; set; }
        public List<string> directors { get; set; }
        public List<string> writers { get; set; }
        public string overview { get; set; }
        public string productionCode { get; set; }
        public string showUrl { get; set; }
        public int lastUpdated { get; set; }
        public string dvdDiscid { get; set; }
        public int? dvdSeason { get; set; }
        public double? dvdEpisodeNumber { get; set; }
        //public object dvdChapter { get; set; }
        public int? absoluteNumber { get; set; }
        public string filename { get; set; }
        public int seriesId { get; set; }
        public int lastUpdatedBy { get; set; }
        public int? airsAfterSeason { get; set; }
        public int? airsBeforeSeason { get; set; }
        public int? airsBeforeEpisode { get; set; }
        public int thumbAuthor { get; set; }
        public string thumbAdded { get; set; }
        public string thumbWidth { get; set; }
        public string thumbHeight { get; set; }
        public string imdbId { get; set; }
        public double siteRating { get; set; }
        public int siteRatingCount { get; set; }

        public Episode ToEpisode() {
            Episode ep = new Episode();

            ep.TvDbId =         this.id;
            ep.seasonNumber =   this.airedSeason;
            ep.episodeNumber =  this.airedEpisodeNumber;
            ep.name =           this.episodeName;
            ep.description =    this.overview;

            return ep;
        }
    }

    public class TheTvDbEpisodeList {
        public List<TheTvDbEpisode> data { get; set; }

        public List<Season> ToSeasonList() {
            List<Season> l = new List<Season>();

            foreach (TheTvDbEpisode TvDbE in this.data) {
                Episode ep = TvDbE.ToEpisode();
                bool foundSeason = false;
                Season se = null;
                foreach(Season season in l) {
                    if (season.seasonNumber == ep.seasonNumber) {
                        se = season;
                        break;
                    }
                }

                if (se == null) {
                    se = new Season {
                        seasonNumber = ep.seasonNumber
                    };
                    l.Add(se);
                }

                se.episodeList.Add(ep);
                ep.season = se;
            }

            return l;
        }
    }
}
