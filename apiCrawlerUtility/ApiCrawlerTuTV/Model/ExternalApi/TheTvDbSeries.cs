using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiCrawlerTuTV.Model.ExternalApi {
    public class TheTvDbSeriesData {
        public int id { get; set; }
        public string seriesName { get; set; }
        public List<string> aliases { get; set; }
        public string banner { get; set; }
        public string seriesId { get; set; }
        public string status { get; set; }
        public DateTime? firstAired { get; set; }
        public string network { get; set; }
        public string networkId { get; set; }
        public string runtime { get; set; }
        public List<string> genre { get; set; }
        public string overview { get; set; }
        public int lastUpdated { get; set; }
        public string airsDayOfWeek { get; set; }
        public string airsTime { get; set; }
        public string rating { get; set; }
        public string imdbId { get; set; }
        public string zap2itId { get; set; }
        public string added { get; set; }
        public int addedBy { get; set; }
        public double siteRating { get; set; }
        public int siteRatingCount { get; set; }
        public string slug { get; set; }
    }

    public class TheTvDbSeries {
        public TheTvDbSeriesData data { get; set; }
        public Series ToSeries() {
            TheTvDbSeriesData d = this.data;
            Series s = new Series();
            s.tvDbId =              d.id;
            s.seriesName =          d.seriesName;
            s.seriesDescription =   d.overview;
            s.network =             d.network;
            s.rating =              d.rating;
            s.status =              d.status;
            s.imbdId =              d.imdbId;
            s.firstAired =          d.firstAired;
            s.bannerUrl =           d.banner;
            s.runningTime =         int.Parse(d.runtime);

            s.genresList =          new List<Genre>();

            foreach (string genre in d.genre)
                s.genresList.Add(new Genre { name = genre });

            return s;
        }
    }

}
