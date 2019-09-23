using ApiCrawlerTuTV.Model;
using ApiCrawlerTuTV.Model.ExternalApi;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ApiCrawlerTuTV {
    public class ApiCrawler {
        private const string TvDbBaseURL = "https://api.thetvdb.com";
        private const string IMDbBaseURL = "http://www.omdbapi.com/?apikey=b2efd4a3&";
        private HttpClient TvDbClient, IMDbClient;
        private Authentication TvDbAuth;
        private string TvDbToken;
        private string IMDbApiKey;

        public ApiCrawler(string TvDbApiKey, string TvDbUserName, string TvDbUserKey, string IMDbApiKey) {
            this.IMDbApiKey = IMDbApiKey;
            IMDbClient = new HttpClient();

            TvDbClient = new HttpClient {
                BaseAddress = new Uri(TvDbBaseURL)
            };

            this.TvDbAuth = new Authentication {
                ApiKey      = TvDbApiKey,
                UserName    = TvDbUserName,
                UserKey     = TvDbUserKey
            };

            this.TvDbToken = GenerateToken();
            TvDbClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", TvDbToken);
        }

        private string GenerateToken() {
            HttpContent content = new StringContent(JsonConvert.SerializeObject(TvDbAuth), Encoding.UTF8, "application/json");

            using (HttpResponseMessage resp = TvDbClient.PostAsync("/login", content).Result) {
                if (!resp.IsSuccessStatusCode) {
                    if (resp.StatusCode == HttpStatusCode.Forbidden)
                        throw new Exception("Usuario o contraseña incorrectos");

                    throw new Exception("El servidor devolvió un código de error: " + resp.StatusCode);
                }

                string jsonString = resp.Content.ReadAsStringAsync().Result;
                return JsonConvert.DeserializeAnonymousType(jsonString,
                    new { token = "" }
                ).token;
            }
        }

        public async Task<List<Series>> GetSeriesListAsync(int startId, int endId, HashSet<Genre> gl, HashSet<Network> nl) {
            List<Series> l =    new List<Series>();
            int id =            startId;
            try {
                for (; id <= endId; id++) {

                    using (HttpResponseMessage resp = TvDbClient.GetAsync("/series/" + id).Result) {

                        if (!resp.IsSuccessStatusCode) {
                            Debug.WriteLine("No pude recuperar serie nº" + id + ". Boomer :(");
                            continue;
                        }

                        string jsonString = await resp.Content.ReadAsStringAsync();
                        TheTvDbSeries TvDbS = JsonConvert.DeserializeObject<TheTvDbSeries>(jsonString);
                        Series s = TvDbS.ToSeries(gl, nl);

                        if (String.IsNullOrWhiteSpace(s.seriesName))
                            continue;

                        s.actorList = await GetActorRolesBySeriesIdAsync(id);
                        foreach (ActorRole ar in s.actorList)
                            ar.series = s;

                        IMDbSeries IMDbS = await GetIMDbSeriesAsync(s.imbdId);
                        if(IMDbS != null)
                            s.posterUrl = IMDbS.Poster;

                        s.seasonList = await GetSeasonListSeriesIdAsync(id);
                        foreach (Season se in s.seasonList)
                            se.series = s;

                        l.Add(s);
                    }
                }

            } catch(Exception e) {
                MessageBox.Show(e.Message, "Excepción procesando la serie nº" + id + ".");
            }

            return l;
        }

        private async Task<IMDbSeries> GetIMDbSeriesAsync(string IMDbId) {
            using (HttpResponseMessage resp = IMDbClient.GetAsync(IMDbBaseURL + "&i=" + IMDbId).Result) {

                if (!resp.IsSuccessStatusCode) {
                    Debug.WriteLine("No pude recuperar serie nº" + IMDbId + ". Boomer :(");
                    return null;
                }

                string jsonString = await resp.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<IMDbSeries>(jsonString);
            }
        }

        private async Task<List<ActorRole>> GetActorRolesBySeriesIdAsync(int TvDbId) {
            using (HttpResponseMessage resp = TvDbClient.GetAsync("/series/" + TvDbId + "/actors").Result) {

                if (!resp.IsSuccessStatusCode) {
                    Debug.WriteLine("No pude recuperar actores de serie nº" + TvDbId + ". Boomer :(");
                    return new List<ActorRole>();
                }

                string jsonString = await resp.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<TheTvDbActorList>(jsonString).ToActorList();
            }
        }

        private async Task<List<Season>> GetSeasonListSeriesIdAsync(int TvDbId) {
            using (HttpResponseMessage resp = TvDbClient.GetAsync("/series/" + TvDbId + "/episodes").Result) {

                if (!resp.IsSuccessStatusCode) {
                    Debug.WriteLine("No pude recuperar episodios de serie nº" + TvDbId + ". Boomer :(");
                    return new List<Season>();
                }

                string jsonString = await resp.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<TheTvDbEpisodeList>(jsonString).ToSeasonList();
            }
        }
    }
}
