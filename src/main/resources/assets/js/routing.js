const router = VueRouter.createRouter({
    history: VueRouter.createMemoryHistory(),
    routes: [
        { path: '/', component: homeComponent, meta: {
            private: false,
            breadcrumb: {
                show: false,
                title: 'Inicio',
                paths: [
                    { title: 'Inicio', active: true }
                ]
            }
        } },
        { path: '/login', component: loginComponent, meta: {
            private: false,
            breadcrumb: {
                show: false,
                title: 'Login',
                paths: [
                    { title: 'Inicio', path: '/', active: false },
                    { title: 'Login', active: true }
                ]
            }
        } },
        { path: '/registro', component: signupComponent, meta: {
            private: false,
            breadcrumb: {
                show: false,
                title: 'Registro',
                paths: [
                    { title: 'Inicio', path: '/', active: false },
                    { title: 'Registro', active: true }
                ]
            }
        } },
        { path: '/usuario', component: userProfileComponent, meta: {
            private: true,
            breadcrumb: {
                show: true,
                title: 'Perfil de usuario',
                paths: [
                    { title: 'Inicio', path: '/', active: false },
                    { title: 'Perfil de usuario', active: true }
                ]
            }
        } },
        { path: '/restaurantes', component: listComponent, meta: {
            private: false,
            breadcrumb: {
                show: true,
                title: 'Listado de restaurantes',
                paths: [
                    { title: 'Inicio', path: '/', active: false },
                    { title: 'Listado de restaurantes', active: true }
                ]
            }
        } },
        { path: '/restaurante/:name', component: detailComponent, meta: {
            private: false,
            breadcrumb: {
                show: true,
                title: 'Restaurante',
                paths: [
                    { title: 'Inicio', path: '/', active: false },
                    { title: 'Listado de restaurantes', path: '/restaurantes', active: false },
                    { title: 'Restaurante', active: true }
                ]
            }
        } }
    ],
});