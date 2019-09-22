using ApiCrawlerTuTV.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiCrawlerTuTV {
    public class ActorRole {
        public int id { get; set; }
        public int tvDbId { get; set; }
        public String role { get; set; }
        public Actor actor { get; set; }
        public Series series { get; set; }
        public string imageUrl { get; set; }
    }

}
