using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiCrawlerTuTV.Model {
    public class Episode {
        public int id { get; set; }
        public String name { get; set; }
        public String description { get; set; }
        public int episodeNumber { get; set; }
        public int seasonNumber { get; set; }
        public Season season { get; set; }
        public int TvDbId { get; set; }
        //public Rating userRating { get; set; }
    }
}
