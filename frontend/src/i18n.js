import i18n from "i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import { initReactI18next } from "react-i18next";

i18n
  .use(LanguageDetector)
  .use(initReactI18next)
  .init({
    // we init with resources
    resources: {
      es: {
        translations: {
            "index.explore":                "Explorar",
            "index.signin":                 "Iniciar sesión",
            "index.followers":              "{{count}} seguidor",
            "index.followers_plural":       "{{count}} seguidores",
            "index.newShows":               "Recientemente agregadas",
            "index.profile":                "Perfil",

            "search.search":                "Buscar",
            "search.advancedSearch":        "Búsqueda avanzada",

            "genres.drama":                 "Drama",
            "genres.fantasy":               "Fantasía",
            "genres.actionadventure":       "Acción y aventura",
            "genres.animation":             "Animación",
            "genres.comedy":                "Comedia",
            "genres.talk":                  "Entrevistas",
            "genres.reality":               "Telerealidad",
            "genres.crime":                 "Policiales",
            "genres.documentary":           "Documentales",
            "genres.family":                "Familiares",
            "genres.horror":                "Terror",
            "genres.kids":                  "Infantiles",
            "genres.mystery":               "Misterio",
            "genres.news":                  "Noticias",
            "genres.romance":               "Romance",
            "genres.soap":                  "Novelas",
            "genres.warpolitics":           "Guerra y política",
            "genres.scififantasy":          "Ciencia ficción y fantasía",

            "series.follow":                "Seguir",
            "series.unfollow":              "Dejar de seguir",
            "series.seasons":               "{{count}} temporada",
            "series.seasons_plural":        "{{count}} temporadas",
            "series.season_number":         "Temporada {{count}}",

            "login.continue":               "Continuar sin iniciar sesión",
            "login.noaccount":              "¿No tienes una cuenta?",
            "login.createaccount":          "Registrate",
            "login.title":                  "Iniciar sesión",
            "login.username":               "Correo electrónico: ",
            "login.password":               "Contraseña: ",
            "login.rememberme":             " Recordarme",
            "login.submit":                 "Iniciar sesión",

            "register.title":               "Registrarse",
            "register.username":            "Nombre de usuario:",
            "register.password":            "Contraseña:",
            "register.repeatPassword":      "Repetir contraseña:",
            "register.mail":                "Correo electrónico: ",
            "register.submit":              "Registrar",
            "register.haveaccount":         "¿Ya tienes una cuenta?",
            "register.login":               "Ingresa",
            "register.continue":            "Continuar sin registrarse",
            "register.unmatchedPassword":   "Las contraseñas no coinciden.",
            "register.mailExists":          "El mail ingresado ya existe.",

            "profile.usernameExists":       "El nombre de usuario ya existe.",
            "profile.followed":             "Series",
            "profile.lists":                "Listas",
            "profile.stats":                "Estadísticas",
            "profile.information":          "Información",
            "profile.recently":             "Visto recientemente",
            "profile.all":                  "Todas las series",
            "profile.userNoShows":          "{{user}} no sigue ninguna serie todavía",
            "profile.save":                 "Guardar",

            "upcoming.title":               "Próximamente",
            "upcoming.noshows":             "¡No hay próximos episodios!",

            "watchlist.watchNext":          "Continue viendo",
            "watchlist.season":             "T",
            "watchlist.noshows":            "¡No tiene episodios para ver!",
            "watchlist.Noshows":            "Sin episodios",
            "watchlist.discover":           "Encuentre series para ver",
            "watchlist.explore":            "Explorar",
        }
      },
      en: {
        translations: {
            "index.explore":                "Explore",
            "index.signin":                 "Log in",
            "index.followers":              "{{count}} follower",
            "index.followers_plural":       "{{count}} followers",
            "index.newShows":               "Recently added",
            "index.profile":                "Profile",

            "search.search":                "Search",
            "search.advancedSearch":        "Advanced search",

            "genres.drama":                 "Drama",
            "genres.fantasy":               "Fantasy",
            "genres.actionadventure":       "Action & Adventure",
            "genres.animation":             "Animation",
            "genres.comedy":                "Comedy",
            "genres.talk":                  "Talk",
            "genres.reality":               "Reality",
            "genres.crime":                 "Crime",
            "genres.documentary":           "Documentary",
            "genres.family":                "Family",
            "genres.horror":                "Horror",
            "genres.kids":                  "Kids",
            "genres.mystery":               "Mystery",
            "genres.news":                  "News",
            "genres.romance":               "Romance",
            "genres.soap":                  "Soap",
            "genres.warpolitics":           "War & Politics",
            "genres.scififantasy":          "Sci-fi & Fantasy",

            "series.follow":                "Follow",
            "series.unfollow":              "Unfollow",
            "series.seasons":               "{{count}} season",
            "series.seasons_plural":        "{{count}} seasons",
            "series.season_number":         "Season {{count}}",

            "login.continue":               "Continue without login",
            "login.noaccount":              "Don't have an account?",
            "login.createaccount":          "Register",
            "login.title":                  "Log in",
            "login.username":               "Mail address: ",
            "login.password":               "Password: ",
            "login.rememberme":             " Remember me",
            "login.submit":                 "Log in",

            "register.title":               "Register",
            "register.username":            "Username:",
            "register.password":            "Password:",
            "register.repeatPassword":      "Repeat password:",
            "register.mail":                "Mail address:",
            "register.submit":              "Register",
            "register.haveaccount":         "Already have an account?",
            "register.login":               "Login",
            "register.continue":            "Continue without register",
            "register.unmatchedPassword":   "Passwords do not match.",
            "register.mailExists":          "The mail already exists.",

            "profile.usernameExists":       "The username already exists.",
            "profile.followed":             "Shows",
            "profile.lists":                "Lists",
            "profile.stats":                "Statistics",
            "profile.information":          "Information",
            "profile.recently":             "Recently watched",
            "profile.all":                  "All shows",
            "profile.userNoShows":          "{{user}} does not follow any series yet",
            "profile.save":                 "Save",

            "upcoming.title":               "Upcoming",
            "upcoming.noshows":             "There are no upcoming shows!",

            "watchlist.watchNext":          "Watch next",
            "watchlist.season":             "S",
            "watchlist.noshows":            "You have no shows to watch!",
            "watchlist.Noshows":            "No shows",
            "watchlist.discover":           "Discover shows to watch",
            "watchlist.explore":            "Explore",
        }
      }
    },
    fallbackLng: "es",
    debug: true,

    // have a common namespace used around the full app
    ns: ["translations"],
    defaultNS: "translations",

    keySeparator: false, // we use content as keys

    interpolation: {
      escapeValue: false
    }
  });

export default i18n;