import httpCommon from "@/router/http-common";
import {jsonAPI} from "@/router/http-common"
import axios from 'axios';

const API_URL = 'http://localhost:2001/api/';

class UserService {
    registerUser(data){
        return jsonAPI.post("/register", data)
    }

    // loginUser(data){
    //     return jsonAPI.post("/login", data)
    // }

    login(user) {
        return axios
            .post(API_URL + 'login', {
                user_name: user.name,
                password: user.password
            })
            .then(response => {
                console.log(response)
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    getRoles(){
        return httpCommon.get("/roles")
    }

}

export default new UserService()