using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiCrawlerTuTV.Model {
    public class Season : IComparable<Season> {
        public int id { get; set; }
        public Series series { get; set; }
        public String name { get; set; }
        public List<Episode> episodeList { get; set; }
        public int seasonNumber { get; set; }

        public int CompareTo(Season other) {
            return this.id.CompareTo(other.id);
        }
    }
}
