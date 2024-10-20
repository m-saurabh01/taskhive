import axios from 'axios';


const API_URL = 'http://localhost:8080/auth'; 


export const register = (userData) => {
     axios.post(`${API_URL}/register`, userData).then((res)=>{
        console.log(res.data);
     }).catch(err=>{
        console.log(err);
     })
};


export const login = (credentials) => {
    return axios.post(`${API_URL}/login`, credentials).then((response) => {
        if (response.data.token) {
            localStorage.setItem('user', JSON.stringify(response.data)); // Save token to localStorage
        }
        return response.data;
    });
};

// Logout
export const logout = () => {
    localStorage.removeItem('user');
};


export const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem('user'));
};
