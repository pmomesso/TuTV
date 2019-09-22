using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiCrawlerTuTV.Model {
    public class Actor {
        public int id { get; set; }
        public int tvDbId { get; set; }
        public String name { get; set; }
        public int age { get; set; }
        public double actorUserRating { get; set; }
        public DateTime created { get; set; }
        public DateTime updated { get; set; }

    }
}
