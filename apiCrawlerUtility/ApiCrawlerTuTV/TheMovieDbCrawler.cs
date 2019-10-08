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

        public async Task<List<Series>> GetSeriesListAsync(int startId, int endId) {
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
                        Series s = MovieDbS.ToSeries();

                        if (String.IsNullOrEmpty(s.bannerUrl))
                            continue;

                        if (String.IsNullOrEmpty(s.posterUrl))
                            continue;

                        if (s.network == null)
                            continue;

                        foreach (Season se in s.seasonList) {
                            se.episodeList = await GetEpisodesBySeriesAndSeasonAsync(s, se);
                            if (se.episodeList.Count <= 0)
                                continue;
                        }

                        s.actorList = await GetMainCastBySeries(s);
                        if (s.actorList.Count <= 0)
                            continue;

                        foreach (ActorRole ar in s.actorList)
                            ar.series = s;

                        l.Add(s);
                    }
                }

            }
            catch (Exception e) {
                MessageBox.Show(e.Message, "Excepción procesando la serie nº" + id + ".");
            }

            return l;
        }

        public async Task<List<Episode>> GetEpisodesBySeriesAndSeasonAsync(Series series, Season season) {
            List<Episode> l = new List<Episode>();
            try {

                var queryBuilder = GetParamBuilder();
                var fullEndpoint = MovieDbURL + "/tv/" + series.id + "/season/" + season.seasonNumber + "?" + queryBuilder.ToString();

                using (HttpResponseMessage resp = MovieDbClient.GetAsync(fullEndpoint).Result) {

                    if (!resp.IsSuccessStatusCode) {
                        Debug.WriteLine("No pude recuperar episodios de serie nº" + series.id + " temporada nº" + season.seasonNumber + ". Boomer :(");
                        return new List<Episode>();
                    }

                    string jsonString = await resp.Content.ReadAsStringAsync();
                    TheMovieDbSeason MovieDbS = JsonConvert.DeserializeObject<TheMovieDbSeason>(jsonString);

                    foreach (TheMovieDbEpisode tmdbe in MovieDbS.episodes) {
                        Episode ep = tmdbe.ToEpisode();
                        ep.season = season;
                        l.Add(ep);
                    }
                }

            }
            catch (Exception e) {
                MessageBox.Show(e.Message, "Excepción procesando los episodios de la serie nº" + series.id + " temporada nº" + season.seasonNumber + ".");
            }

            return l;
        }

        public async Task<List<ActorRole>> GetMainCastBySeries(Series series) {
            List<ActorRole> l = new List<ActorRole>();
            try {

                var queryBuilder = GetParamBuilder();
                var fullEndpoint = MovieDbURL + "/tv/" + series.id + "/credits?" + queryBuilder.ToString();

                using (HttpResponseMessage resp = MovieDbClient.GetAsync(fullEndpoint).Result) {

                    if (!resp.IsSuccessStatusCode) {
                        Debug.WriteLine("No pude recuperar elenco de serie nº" + series.id + ". Boomer :(");
                        return new List<ActorRole>();
                    }

                    string jsonString = await resp.Content.ReadAsStringAsync();
                    TheMovieDbCredits MovieDbC = JsonConvert.DeserializeObject<TheMovieDbCredits>(jsonString);

                    foreach (TheMovieDbCast tmdbc in MovieDbC.cast)
                        l.Add(tmdbc.ToActorRole());

                }

            }
            catch (Exception e) {
                MessageBox.Show(e.Message, "Excepción procesando elenco de la serie nº" + series.id + ".");
            }

            return l;
        }
    }
}
