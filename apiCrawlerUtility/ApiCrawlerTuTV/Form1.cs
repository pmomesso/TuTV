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

        private void CrawlMovieDb() {
            int seriesIdFromNum = int.Parse(seriesIdFrom.Text);
            int seriesIdToNum = int.Parse(seriesIdTo.Text);

            if (seriesIdToNum < seriesIdFromNum) {
                MessageBox.Show("Argumentos inválidos.");
                goto ThisIsTheEnd;
            }

            groupBox1.Enabled = false;
            groupBox1.Text = "Intentando obtener " + (seriesIdToNum - seriesIdFromNum + 1) + " series de TheTvDB...";

            //Creawleo la data de la API y la transformo en Serie
            TheMovieDbCrawler crawler = new TheMovieDbCrawler("c52eb986fe3d20e3eb11c982d5eab213");

            //Listado de géneros
            HashSet<Genre> gl = new HashSet<Genre>();
            //Listado de networks
            HashSet<Network> nl = new HashSet<Network>();
            //Listado de actores
            HashSet<Actor> al = new HashSet<Actor>();
            //Listado de series
            List<Series> l = crawler.GetSeriesListAsync(seriesIdFromNum, seriesIdToNum).Result;

            //Database manager
            DatabaseManager dbm = new DatabaseManager("localhost", 5432, "root", "root", "paw");

            //Construimos sets de cosas para insertar
            foreach(Series s in l) {
                foreach (ActorRole ar in s.actorList)
                    al.Add(ar.actor);

                foreach (Genre g in s.genresList)
                    gl.Add(g);

                nl.Add(s.network);
            }

            //Inserto géneros
            foreach (Genre g in gl)
                dbm.InsertOrUpdateGenre(g);

            //Inserto cadenas
            foreach (Network n in nl)
                dbm.InsertOrUpdateNetwork(n);

            //Inserto actores
            foreach (Actor a in al)
                dbm.InsertOrUpdateActor(a);

            int count = 1;
            //Inserto serie
            foreach (Series s in l) {
                groupBox1.Text = "Insertando serie " + count + " de " + l.Count + "...";
                dbm.InsertOrUpdateSeries(s);

                foreach (ActorRole ar in s.actorList)
                    dbm.InsertOrUpdateActorRole(ar);

                foreach (Season se in s.seasonList) {
                    dbm.InsertOrUpdateSeason(se);

                    foreach (Episode ep in se.episodeList)
                        dbm.InsertOrUpdateEpisode(ep);
                }

                count++;
            }

            MessageBox.Show(count + " series insertadas con éxito.");

        ThisIsTheEnd:
            groupBox1.ResetText();
            groupBox1.Enabled = true;
        }
        /*
        private void CrawlTvDb() {

            int seriesIdFromNum = int.Parse(seriesIdFrom.Text);
            int seriesIdToNum = int.Parse(seriesIdTo.Text);

            if(seriesIdToNum < seriesIdFromNum) {
                MessageBox.Show("Argumentos inválidos.");
                goto ThisIsTheEnd;
            }

            groupBox1.Enabled = false;
            groupBox1.Text = "Intentando obtener " + (seriesIdToNum - seriesIdFromNum + 1) + " series de TheTvDB...";

            //Creawleo la data de la API y la transformo en Serie
            TheTvDbCrawler crawler = new TheTvDbCrawler(
                "8AN5KC2OL40JPSCD", 
                "holanico119d1", 
                "8NQWA8WSGZX78J4Q", 
                "b2efd4a3");

            //Listado de géneros
            HashSet<Genre> gl =     new HashSet<Genre>();
            //Listado de networks
            HashSet<Network> nl =   new HashSet<Network>();
            //Listado de series
            List <Series> l =       crawler.GetSeriesListAsync(seriesIdFromNum, seriesIdToNum, gl, nl).Result;

            //Database manager
            DatabaseManager dbm = new DatabaseManager("localhost", 5432, "root", "root", "paw");

            groupBox1.Text = "Actualizando géneros...";
            //Inserto géneros
            foreach (Genre g in gl)
                g.id = dbm.InsertOrUpdateGenre(g);

            groupBox1.Text = "Actualizando canales...";
            //Inserto canales
            foreach (Network n in nl)
                n.id = dbm.InsertOrUpdateNetwork(n);

            int count = 1;

            //Inserto series
            foreach (Series s in l) {
                groupBox1.Text = "Insertando serie " + count + " de " + l.Count + "...";
                s.id = dbm.InsertOrUpdateSeries(s);

                foreach(ActorRole ar in s.actorList) {
                    ar.actor.id =   dbm.InsertOrUpdateActor(ar.actor);
                    ar.id =         dbm.InsertOrUpdateActorRole(ar);
                }

                foreach(Season se in s.seasonList) {
                    se.id = dbm.InsertOrUpdateSeason(se);

                    foreach(Episode ep in se.episodeList)
                        ep.id = dbm.InsertOrUpdateEpisode(ep);

                }

                count++;
            }

            MessageBox.Show(count + " series insertadas con éxito.");

            ThisIsTheEnd:
            groupBox1.ResetText();
            groupBox1.Enabled = true;
        }
        */

        private void Button1_Click_1(object sender, EventArgs e) {
            CrawlMovieDb();
            //CrawlTvDb();
        }
    }
}
