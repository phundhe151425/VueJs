import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:2001/';

class UserService {
    getPublicContent() {
        return axios.get(API_URL + 'login');
    }

    getUserBoard() {
        return axios.get(API_URL + 'blogs/**', { headers: authHeader() });
    }

    // getModeratorBoard() {
    //     return axios.get(API_URL + 'mod', { headers: authHeader() });
    // }
    //
    // getAdminBoard() {
    //     return axios.get(API_URL + 'admin', { headers: authHeader() });
    // }
}

export default new UserService();