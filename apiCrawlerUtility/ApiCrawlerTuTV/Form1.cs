using ApiCrawlerTuTV.Model;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ApiCrawlerTuTV {
    public partial class Form1 : Form {
        public Form1() {
            InitializeComponent();
        }

        private void Button1_Click(object sender, EventArgs e) {
            //Creawleo la data de la API y la transformo en Serie
            ApiCrawler crawler = new ApiCrawler(
                "8AN5KC2OL40JPSCD", 
                "holanico119d1", 
                "8NQWA8WSGZX78J4Q", 
                "b2efd4a3");
            List <Series> l = crawler.GetSeriesListAsync(305250, 305390).Result;

            //Listado de géneros
            HashSet<string> gl = new HashSet<string>();
            foreach (Series s in l)
                foreach (Genre g in s.genresList)
                    gl.Add(g.name);

            //Listado de networks
            HashSet<string> nl = new HashSet<string>();
            foreach (Series s in l)
                nl.Add(s.network);

            DatabaseManager dbm = new DatabaseManager("localhost", 5432, "root", "root", "paw");
            foreach(Series s in l) {
                s.id = dbm.InsertOrUpdateSeries(s);

                foreach(ActorRole ar in s.actorList) {
                    ar.actor.id =   dbm.InsertOrUpdateActor(ar.actor);
                    ar.id =         dbm.InsertOrUpdateActorRole(ar);
                }

                foreach(Season se in s.seasonList) {
                    se.id = dbm.InsertOrUpdateSeason(se);

                    foreach(Episode ep in se.episodeList) {
                        ep.seriesId = s.id;
                        ep.seasonId = se.id;

                        ep.id = dbm.InsertOrUpdateEpisode(ep);
                    }
                }
            }
        }
    }
}
