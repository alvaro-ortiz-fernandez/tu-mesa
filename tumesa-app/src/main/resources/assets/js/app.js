axios.defaults.baseURL = appPath;
axios.defaults.headers.common['Authorization'] = '';
axios.defaults.headers.common['Accept'] = 'application/json';
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';

let app = {
    data() {
        return {
            hello: "Hello world"
        }
    }
};

let router = VueRouter.createRouter({
    history: VueRouter.createMemoryHistory(),
    routes: [
        { path: '/login', component: loginComponent },
        { path: '/chat', component: {} }
    ],
});

addEventListener("DOMContentLoaded", (event) => {
    Vue.createApp(app)
       .use(router)
       .component('login', loginComponent)
       .mount('#app');
});
