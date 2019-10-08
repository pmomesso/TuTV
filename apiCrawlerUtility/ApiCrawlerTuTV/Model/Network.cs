using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiCrawlerTuTV.Model {
    public class Network : IComparable<Network> {
        public int id { get; set; }
        public string name { get; set; }

        public int CompareTo(Network other) {
            return this.id.CompareTo(other.id);
        }

        public override bool Equals(object obj) {
            return this.id.Equals(((Network)obj).id);
        }

        public override int GetHashCode() {
            return this.id.GetHashCode();
        }
    }
}
