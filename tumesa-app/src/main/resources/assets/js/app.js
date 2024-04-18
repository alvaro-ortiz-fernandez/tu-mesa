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
        { path: '/', component: loginComponent },
        { path: '/login', component: loginComponent },
        { path: '/signup', component: signupComponent }
    ],
});

addEventListener("DOMContentLoaded", (event) => {
    Vue.createApp(app)
       .use(router)
       .component('app-header', appHeaderComponent)
       .component('app-footer', appFooterComponent)
       .component('login', loginComponent)
       .component('signup', signupComponent)
       .mount('#app');
});
