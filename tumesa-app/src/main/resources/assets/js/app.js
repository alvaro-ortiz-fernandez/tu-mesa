axios.defaults.baseURL = appPath;
axios.defaults.headers.common['Authorization'] = '';
axios.defaults.headers.common['Accept'] = 'application/json';
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';

let app = {
    data() {
        return {
            appPath: appPath
        }
    }
};

let router = VueRouter.createRouter({
    history: VueRouter.createMemoryHistory(),
    routes: [
        { path: '/', component: homeComponent },
        { path: '/login', component: loginComponent },
        { path: '/registro', component: signupComponent },
        { path: '/usuario', component: userProfileComponent },
        { path: '/administrador', component: adminProfileComponent },
        { path: '/restaurantes', component: listComponent },
        { path: '/restaurante', component: detailComponent },
        { path: '/nueva-reserva', component: reservationComponent }
    ],
});

addEventListener("DOMContentLoaded", (event) => {
    Vue.createApp(app)
       .use(router)
           .component('app-header', appHeaderComponent)
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
