import axios from 'axios';
import { getCurrentUser } from '../services/authService';


const api = axios.create({
    baseURL: 'http://localhost:8080', 
    headers: {
        'Content-Type': 'application/json',
    },
});



api.interceptors.request.use(
    (config) => {
        const user = getCurrentUser(); 
        if (user && user.token) {
            config.headers.Authorization = `Bearer ${user.token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);


export const callApi = async (endpoint, method = 'GET', data = null,navigate) => {
    try {
        const response = await api({
            url: endpoint,
            method: method, 
            data: data, 
        });
        return response.data; 
    } catch (error) {
        if (error.response && error.response.status === 403) {
            navigate('/login');
        }
        throw error; 
    }
};

export default api;

