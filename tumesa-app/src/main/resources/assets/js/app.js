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
    Vue.createApp(app)
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
           .component('reservation', reservationComponent)
       .mount('#app');
});
