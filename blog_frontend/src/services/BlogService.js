

import httpCommon from "@/router/http-common";

class BlogService{
    getAll(params){
        return httpCommon.get("/blogs", {params});
    }

    getBlogById(id) {
        return httpCommon.get(`/blogs/${id}`);
    }

    create(data) {
        let fromData = new FormData(data);
        console.log(fromData);
        return httpCommon.post("/blogs", fromData);
    }

    update(id, data){
        this.getBlogById(id);
        let formData = new FormData(data);
        return httpCommon.put(`/blogs/${id}`, formData);
    }

    delete(id) {
        return httpCommon.delete(`/blogs/${id}`);
    }

    search(key) {
        return httpCommon.get(`/blogs/search?key=${key}`);
    }

    getCates(){
        return httpCommon.get("/cates")
    }

    getAuthors(){
        return httpCommon.get("/authors")
    }


}

export default new BlogService()