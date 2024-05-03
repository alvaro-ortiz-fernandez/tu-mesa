axios.defaults.baseURL = appPath;
axios.defaults.headers.common['Authorization'] = '';
axios.defaults.headers.common['Accept'] = 'application/json';
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';

let app = {
    data() {
        return {
            user: undefined
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
        },
        removeAuthorization() {
            this.user = undefined;
            axios.defaults.headers.common['Authorization'] = '';
        }
    }
};

let router = VueRouter.createRouter({
    history: VueRouter.createMemoryHistory(),
    routes: [
        { path: '/', component: homeComponent, meta: { private: false } },
        { path: '/login', component: loginComponent, meta: { private: false } },
        { path: '/registro', component: signupComponent, meta: { private: false } },
        { path: '/usuario', component: userProfileComponent, meta: { private: true } },
        { path: '/administrador', component: adminProfileComponent, meta: { private: true } },
        { path: '/restaurantes', component: listComponent, meta: { private: false } },
        { path: '/restaurante', component: detailComponent, meta: { private: false } },
        { path: '/nueva-reserva', component: reservationComponent, meta: { private: true } }
    ],
});

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
