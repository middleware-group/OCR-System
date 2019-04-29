import Vue from 'vue'
import Router from 'vue-router'
import index from '../pages/index'
import upload from '../pages/upload'
import test from '../pages/test'

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            name: 'index',
            component: index
        },
        {
            path: '/upload',
            name: 'upload',
            component: () => import('../pages/upload')
        },
        {
            path: '/contact',
            name: 'contact',
            component: () => import('../pages/contact')
        },
        {
            path: '/test',
            name: 'test',
            component: test
        }
    ]
})
