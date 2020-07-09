import i18n from "i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import { initReactI18next } from "react-i18next";

i18n
  .use(LanguageDetector)
  .use(initReactI18next)
  .init({
    // we init with resources
    resources: {
      en: {
        translations: {
            "index.users":                  "Users",
            "index.upcoming":               "Upcoming",
            "index.watchlist":              "Watchlist",
            "index.explore":                "Explore",
            "index.signin":                 "Log in",
            "index.signout":                "Log out",
            "index.followers":              "{{count}} follower",
            "index.followers_plural":       "{{count}} followers",
            "index.newShows":               "Recently added",
            "index.profile":                "Profile",

            "search.search":                "Search",
            "search.searchResults":         "Search Results",
            "search.noResults":             "No results found for your search",
            "search.advancedSearch":        "Advanced search",
            "search.allNetworks":           "All networks",
            "search.allGenres":             "All genres",

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
            "series.no_rating":             "No rating yet",
            "series.spoil":                 "Avoid spoilers here",
            "series.enterComment":          "Enter your comment here",
            "series.markSpoiler":           "Mark as spoiler",
            "series.post":                  "Comment",
            "series.discussion":            "Discussion",
            "series.enterReply":            "Enter your reply here",
            "series.noPosts":               "There are no comments for this series yet",
            "series.hasSpoiler":            "Contains spoilers",
            "series.show":                  "Show",
            "series.deleteConfirmTitle":    "Confirmation required",
            "series.deleteConfirmDialog":   "Are you really sure you wish to delete this post? This action can't be undone.",
            "series.yes":                   "Yes",
            "series.no":                    "No",
            "series.ban":                   "Ban user",
            "series.unban":                 "Unban user",
            "series.addToList":             "Add to a list",
            "series.newList":               "New list",
            "series.newListName":           "Enter a name for the new list",
            "series.viewPrevious":          "You have previous unseen episodes. Do you want to mark them all as seen?",

            "login.title":                  "Log In",
            "login.username":               "Mail address: ",
            "login.password":               "Password: ",
            "login.rememberme":             "Remember me",
            "login.submit":                 "Log in",
            "login.noaccount":              " Don't have an account?",
            "login.createaccount":          " Register",
            "login.continue":               "Continue without login",
            "login.ErrorNotConfirmed":      "Your account must be verified before login. Check your inbox",
            "login.ErrorExpired":           "Your session expired. You must reauthenticate",
            "login.ErrorInvalidCredentials":"Invalid credencials",
            "login.ErrorOther":             "Internal Error. Try again later",
            "login.StartExploring":         "Start exploring!",
            "login.invalidCredentials":     "Incorrect username or password",

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
            "register.invalidEmail":        "Invalid email address",
            "register.passwordTooLong":     "Must be 20 characters or less",
            "register.mailExists":          "The mail already exists.",
            "register.usernameExists":      "The username already exists.",
            "register.successModalTitle":   "Almost ready!",
            "register.successModalBody":    "Your information was registered successfully. To complete the process, activate your account through the link sent to your mail address.",

            "mailconfirm.title":            "Welcome to TuTv, {{name}}",
            "mailconfirm.success":          "Your account has been successfully activated. You can now fully enjoy the site.",
            "mailconfirm.loading":          "Activating your account...",
            "mailconfirm.error":            "Error activating your account. The link followed is invalid or has been already used.",

            "profile.followed":             "Shows",
            "profile.recently":             "Recently watched",
            "profile.all":                  "All shows",
            "profile.userNoShows":          "{{user}} does not follow any series yet",
            "profile.avatarMaxSize":        "The maximum file size is",
            "profile.upload":               "Upload profile picture",
            "profile.edit":                 "Edit",
            "profile.information":          "Information",
            "profile.stats":                "Stats",
            "profile.noStats":              "Not enough information to calculate your stats",
            "profile.lists":                "Lists",
            "profile.lists.noSeries":       "This list is empty",
            "profile.addToList":            "Add to list",
            "profile.addList":              "Add list",
            "profile.modifyList":           "Modify list",
            "profile.noLists":              "You have no lists yet",
            "profile.noShows":              "You have no followed shows yet",
            "profile.listName":             "Name",
            "profile.done":                 "Done",
            "profile.close":                "Close",
            "profile.sureRemove":           "Are you sure you want to remove {0}?",
            "profile.favoriteGenres":       "Favorite Genres",
            "profile.save":                 "Save",
            "profile.usernameExists":       "The username already exists.",
            "profile.wrongFileType":        "Selected file extension is not supported.",

            "upcoming.title":               "Upcoming",
            "upcoming.noshows":             "There are no upcoming shows!",

            "watchlist.watchNext":          "Watch next",
            "watchlist.season":             "S",
            "watchlist.noshows":            "You have no shows to watch!",
            "watchlist.Noshows":            "No shows",
            "watchlist.discover":           "Discover shows to watch",
            "watchlist.explore":            "Explore",

            "users.title":                  "Administrator control page",
            "users.user":                   "User",
            "users.status":                 "Status",
            "users.email":                  "Mail",
            "users.action":                 "Action",
            "users.member":                 "Member",
            "users.banned":                 "Banned",
            "users.active":                 "Active",
            "users.results":                "{0} - {1} of {2}",
            "users.previous":               "Previous",
            "users.next":                   "Next",
            "users.banConfirmDialog":       "Are you sure you wish to ban this user?",
        }
      },
      es: {
        translations: {
            "index.users":                  "Usuarios",
            "index.upcoming":               "Próximamente",
            "index.watchlist":              "Series Pendientes",
            "index.explore":                "Explorar",
            "index.signin":                 "Iniciar sesión",
            "index.signout":                "Cerrar sesión",
            "index.followers":              "{{count}} seguidor",
            "index.followers_plural":       "{{count}} seguidores",
            "index.newShows":               "Recientemente agregadas",
            "index.profile":                "Perfil",

            "search.search":                "Buscar",
            "search.searchResults":         "Resultados de Búsqueda",
            "search.noResults":             "No se encontraron resultados para su búsqueda",
            "search.advancedSearch":        "Búsqueda avanzada",
            "search.allNetworks":           "Todas las cadenas",
            "search.allGenres":             "Todos los géneros",

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
            "series.no_rating":             "Sin calificaciones aún",
            "series.spoil":                 "No spoilear aquí",
            "series.enterComment":          "Escriba el comentario aquí",
            "series.markSpoiler":           "Marcar como spoiler",
            "series.post":                  "Comentar",
            "series.discussion":            "Discusión",
            "series.enterReply":            "Escriba su respuesta aquí",
            "series.noPosts":               "No hay comentarios para esta serie todavía",
            "series.hasSpoiler":            "Contiene spoilers",
            "series.show":                  "Mostrar",
            "series.deleteConfirmTitle":    "Confirmación requerida",
            "series.deleteConfirmDialog":   "¿Está seguro de que desea eliminar esta publicación? Esta acción no se puede deshacer.",
            "series.yes":                   "Sí",
            "series.no":                    "No",
            "series.ban":                   "Bloquear usuario",
            "series.unban":                 "Desbloquear usuario",
            "series.addToList":             "Añadir a una lista",
            "series.newList":               "Nueva lista",
            "series.newListName":           "Ingrese un nombre para la nueva lista",
            "series.viewPrevious":          "Tiene episodios anteriores no vistos. ¿Desea marcarlos todos como vistos?",

            "login.title":                  "Ingresar",
            "login.username":               "Correo electrónico:",
            "login.password":               "Contraseña:",
            "login.rememberme":             "Recordarme",
            "login.submit":                 "Ingresar",
            "login.noaccount":              "¿No tienes una cuenta?",
            "login.createaccount":          "Registrate",
            "login.continue":               "Continuar sin iniciar sesión",
            "login.ErrorNotConfirmed":      "Debe confirmar su cuenta antes de iniciar sesićn. Revise su casilla de correo electrónico",
            "login.ErrorExpired":           "Su sesión expiró. Vuelva a iniciar sesión",
            "login.ErrorInvalidCredentials":"Credenciales incorrectas",
            "login.ErrorOther":             "Vuelva a intentar en un momento. Hubo un error interno",
            "login.StartExploring":         "¡Comenzar a explorar!",
            "login.invalidCredentials":     "Usuario o contraseña incorrectos",

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
            "register.invalidEmail":        "Email inválido",
            "register.passwordTooLong":     "Debe contener 20 o menos caracteres",
            "register.mailExists":          "El mail ingresado ya existe.",
            "register.usernameExists":      "El nombre de usuario ya existe.",
            "register.successModalTitle":   "¡Ya casi!",
            "register.successModalBody":    "Su registro se ha completado exitosamente. Para finalizar, active su cuenta mediante el enlace enviado por correo a la casilla que especificó.",

            "mailconfirm.title":            "Bienvenido a TuTv, {{name}}",
            "mailconfirm.success":          "Su cuenta ha sido activada con éxito. Ya puedes disfrutar de todos los beneficios.",
            "mailconfirm.loading":          "Activando su cuenta...",
            "mailconfirm.error":            "Error activando su cuenta. El enlace es inválido o ya ha sido utilizado con anterioridad.",

            "profile.followed":             "Series",
            "profile.recently":             "Visto recientemente",
            "profile.all":                  "Todas las series",
            "profile.userNoShows":          "{{user}} no sigue ninguna serie todavía",
            "profile.avatarMaxSize":        "El tamaño máximo permitido es",
            "profile.upload":               "Subir foto de perfil",
            "profile.edit":                 "Editar",
            "profile.information":          "Información",
            "profile.stats":                "Estadísticas",
            "profile.noStats":              "Sin suficiente información para calcular sus estadísticas",
            "profile.lists":                "Listas",
            "profile.lists.noSeries":       "Esta lista está vacía",
            "profile.addToList":            "Agregar a lista",
            "profile.addList":              "Agregar lista",
            "profile.modifyList":           "Modificar lista",
            "profile.noLists":              "No tiene listas todavía",
            "profile.noShows":              "No tiene series seguidas todavía",
            "profile.listName":             "Nombre",
            "profile.done":                 "Listo",
            "profile.close":                "Cerrar",
            "profile.sureRemove":           "¿Seguro que quiere borrar la lista {0}?",
            "profile.favoriteGenres":       "Géneros favoritos",
            "profile.save":                 "Guardar",
            "profile.usernameExists":       "El nombre de usuario ya existe.",
            "profile.wrongFileType":        "El archivo seleccionado no es válido.",

            "upcoming.title":               "Próximamente",
            "upcoming.noshows":             "¡No hay próximos episodios!",

            "watchlist.watchNext":          "Continue viendo",
            "watchlist.season":             "T",
            "watchlist.noshows":            "¡No tiene episodios para ver!",
            "watchlist.Noshows":            "Sin episodios",
            "watchlist.discover":           "Encuentre series para ver",
            "watchlist.explore":            "Explorar",

            "users.title":                  "Página de control del administrador",
            "users.user":                   "Usuario",
            "users.status":                 "Estado",
            "users.email":                  "Mail",
            "users.action":                 "Acción",
            "users.member":                 "Miembro",
            "users.banned":                 "Bloqueado",
            "users.active":                 "Activo",
            "users.results":                "{0} - {1} de {2}",
            "users.previous":               "Anterior",
            "users.next":                   "Siguiente",
            "users.banConfirmDialog":       "¿Está seguro de que desea bloquear a este usuario?",
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
