using ApiCrawlerTuTV.Model;
using ApiCrawlerTuTV.Model.TheMovieDb;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Diagnostics;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Windows.Forms;

namespace ApiCrawlerTuTV {
    public class TheMovieDbCrawler {
        private const string MovieDbURL = "https://api.themoviedb.org/3";
        private HttpClient MovieDbClient;
        private string Token;

        public TheMovieDbCrawler(string api_key) {
            this.MovieDbClient = new HttpClient();
            this.MovieDbClient.BaseAddress = new Uri(MovieDbURL);
            this.Token = api_key;
        }

        public void asd() {
            var query = HttpUtility.ParseQueryString(string.Empty);
        }

        private NameValueCollection GetParamBuilder() {
            var query = HttpUtility.ParseQueryString(string.Empty);
            query.Add("api_key", Token);

            return query;
        }

        public async Task<List<Series>> GetSeriesListAsync(int startId, int endId, HashSet<Model.Genre> gl, HashSet<Model.Network> nl, HashSet<Actor> al) {
            List<Series> l = new List<Series>();
            int id = startId;
            try {
                for (; id <= endId; id++) {

                    var queryBuilder = GetParamBuilder();
                    var fullEndpoint = MovieDbURL + "/tv/" + id + "?" + queryBuilder.ToString();

                    using (HttpResponseMessage resp = MovieDbClient.GetAsync(fullEndpoint).Result) {

                        if (!resp.IsSuccessStatusCode) {
                            Debug.WriteLine("No pude recuperar serie nº" + id + ". Boomer :(");
                            continue;
                        }

                        string jsonString = await resp.Content.ReadAsStringAsync();
                        TheMovieDbSeries MovieDbS = JsonConvert.DeserializeObject<TheMovieDbSeries>(jsonString);
                        Series s = MovieDbS.ToSeries(gl, nl);

                        l.Add(s);
                    }
                }

            }
            catch (Exception e) {
                MessageBox.Show(e.Message, "Excepción procesando la serie nº" + id + ".");
            }

            return l;
        }
    }
}
