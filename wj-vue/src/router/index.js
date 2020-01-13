import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import AppIndex from '@/components/mainfunction/AppIndex'
import Login from '@/components/Login'
import TestVue from '@/components/home/TestVue'
import Home from '@/components/Home'
import AddLaw from '@/components/mainfunction/subfunction/AddLaw'
import Show from '@/components/mainfunction/subfunction/Show'
import Update from '@/components/mainfunction/subfunction/Update'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
  // 下面都是固定的写法
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'AppIndex',
          component: AppIndex,
          meta: {
            requireAuth: true
          },
          children: [
            {
              path: '/addlaw',
              name: 'AddLaw',
              component: AddLaw
            },
            {
              path: '/show',
              name: 'Show',
              component: Show
            },
            {
              path: '/update',
              name: 'Update',
              component: Update
            }
          ]
        },
        {
          path: '/test',
          name: 'Test',
          component: TestVue
        }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
