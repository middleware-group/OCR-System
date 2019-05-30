import Vue from 'vue'
import Router from 'vue-router'
import index from '../pages/index'
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
            path: '/contact',
            name: 'contact',
            component: () => import('../pages/contact')
        },
        {
            path: '/ocr',
            name: 'ocr',
            component: () => import('../pages/ocr')
        },
        {
            path: '/translate',
            name: 'translate',
            component: () => import('../pages/translate')
        },
        {
            path: '/test',
            name: 'test',
            component: test
        }
    ]
})
