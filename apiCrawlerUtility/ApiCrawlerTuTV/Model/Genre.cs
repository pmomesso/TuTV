using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiCrawlerTuTV.Model {
    public class Genre : IComparable<Genre> {
        public int id { get; set; }
        public string name { get; set; }

        public int CompareTo(Genre other) {
            return this.id.CompareTo(other.id);
        }

        public override bool Equals(object obj) {
            return this.id.Equals(((Genre)obj).id);
        }

        public override int GetHashCode() {
            return this.id.GetHashCode();
        }
    }
}
