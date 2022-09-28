import httpCommon from "@/commons/http-common";
import {jsonAPI} from "@/commons/http-common";

class UserService {
    registerUser(data){
        return jsonAPI.post("/register", data)
    }

    getRoles(){
        return httpCommon.get("/roles")
    }

}

export default new UserService()