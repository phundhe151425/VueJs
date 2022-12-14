import Vue from "vue";
import Router from "vue-router"

Vue.use(Router)

export default new Router({
    mode:"history",
    routes:[
        {
            path: "/blogs",
            alias: "/blogs",
            name: "blogs",
            component: () => import("../components/Blogs")
        },
        {
            path:"/add",
            name:"add",
            component: () => import("../components/AddBlog")

        },
        {
            path:"/blogs/:blogId",
            name:"edit",
            component: () => import("../components/EditBlog")
        },
        {
            path:"/register",
            name:"register",
            component: () => import("../components/Register")
        },
        {
            path:"/login",
            name:"login",
            component: () => import("../components/Login")
        },
    ]
})