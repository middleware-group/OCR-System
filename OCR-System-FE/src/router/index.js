import Vue from 'vue'
import Router from 'vue-router'
import index from '../pages/index'
import first from '../pages/first'
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
            path: '/first',
            name: 'first',
            component: () => import('../pages/first')
        },
        {
            path: '/second',
            name: 'second',
            component: () => import('../pages/second')
        },
        {
            path: '/ocr',
            name: 'ocr',
            component: () => import('../pages/ocr')
        },
        {
            path: '/test',
            name: 'test',
            component: test
        }
    ]
})
