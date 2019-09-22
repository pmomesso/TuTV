using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiCrawlerTuTV.Model.ExternalApi {
    public class TheTvDbActor {
        public int id { get; set; }
        public int seriesId { get; set; }
        public string name { get; set; }
        public string role { get; set; }
        public int sortOrder { get; set; }
        public string image { get; set; }
        public int? imageAuthor { get; set; }
        public string imageAdded { get; set; }
        public string lastUpdated { get; set; }

        public ActorRole ToActorRole() {
            ActorRole a = new ActorRole();

            a.actor = new Actor {
                name =      this.name
            };

            a.tvDbId = this.id;
            a.role =        this.role;
            a.imageUrl =    this.image;

            return a;
        }
    }

    public class TheTvDbActorList {
        public List<TheTvDbActor> data { get; set; }

        public List<ActorRole> ToActorList() {
            List<ActorRole> l = new List<ActorRole>();

            foreach(TheTvDbActor TvDbA in this.data)
                l.Add(TvDbA.ToActorRole());

            return l;
        }
    }
}
