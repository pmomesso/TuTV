using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiCrawlerTuTV.Model {
    public class Series : IComparable<Series> {
        public int id { get; set; }
        public String seriesName { get; set; }
        public int tvDbId { get; set; }
        public String seriesDescription { get; set; }
        public Network network { get; set; }
        public String rating { get; set; }
        public double userRating { get; set; }
        public int runningTime { get; set; }
        public string status { get; set; }
        public string imbdId { get; set; }
        public DateTime? firstAired { get; set; }
        public DateTime Added { get; set; }
        public DateTime updated { get; set; }
        public List<ActorRole> actorList { get; set; }
        public List<Genre> genresList { get; set; }
        public List<Season> seasonList { get; set; }
        public List<Network> networkList { get; set; }
        public string bannerUrl { get; set; }
        public string posterUrl { get; set; }

        public int CompareTo(Series other) {
            return this.id.CompareTo(other.id);
        }
    }
}
