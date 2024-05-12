axios.defaults.baseURL = appPath;
axios.defaults.headers.common['Authorization'] = localStorage.getItem("tumesa-authorization");
axios.defaults.headers.common['Accept'] = 'application/json';
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';

let app = {
    data() {
        return {
            user: localStorage.getItem("tumesa-user")
        }
    },
    created() {
        let _this = this;
        this.$router.beforeEach((to, from, next) => {
            // Redirigimos las rutas privadas al login
            if (to.meta.private && !(_this.user)) {
                NotificationService.showWarningToast("Debe iniciar sesión para acceder a esa sección");
                next({ path: '/login' });
            } else {
                next();
            }
        })
    },
    methods: {
        addAuthorization(user, authorization) {
            this.user = user;
            axios.defaults.headers.common['Authorization'] = authorization;

            localStorage.setItem("tumesa-user", user);
            localStorage.setItem("tumesa-authorization", authorization);
        },
        removeAuthorization() {
            this.user = undefined;
            axios.defaults.headers.common['Authorization'] = '';

            localStorage.removeItem("tumesa-user");
            localStorage.removeItem("tumesa-authorization");
        }
    }
};

addEventListener("DOMContentLoaded", (event) => {
    const vueApp = Vue.createApp(app);

    vueApp
       .use(router)
           .component('app-header', appHeaderComponent)
           .component('app-breadcrumb', appBreadcrumbComponent)
           .component('app-footer', appFooterComponent)
           .component('login', loginComponent)
           .component('signup', signupComponent)
           .component('user-profile', userProfileComponent)
           .component('admin-profile', adminProfileComponent)
           .component('home', homeComponent)
           .component('list', listComponent)
           .component('detail', detailComponent)
           .component('reservation', reservationComponent);

    vueApp.config.globalProperties.$filters = {
        timestampToDate(timestamp) {
            if (!timestamp)
                return ''

            // Multiplicamos por 1000 para convertir segundos a milisegundos
            var fecha = new Date(timestamp * 1000);

            // Obtener los componentes de la fecha
            var dia = fecha.getDate();
            var mes = fecha.getMonth() + 1; // Los meses en JS van de 0 a 11
            var año = fecha.getFullYear();

            // Obtenemos el nombre del mes
            var nombresMeses = ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"];
            var nombreMes = nombresMeses[mes - 1];

            // Lo convertimos a cadena
           return dia + " de " + nombreMes + " de " + año;
        }
    }

    vueApp
       .mount('#app');
});
