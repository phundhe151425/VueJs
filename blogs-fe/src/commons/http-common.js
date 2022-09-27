import axios from "axios";

export default axios.create({
    baseURL: 'http://localhost:2001/api/',
    headers:{
        enctype:"multipart/form-data",
        "Content-type": false,
        processData: false,
    }
})