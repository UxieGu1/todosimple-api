import api from './api';

export const userService = {
    findById : async (id) => {
        try {
            const response = await api.get(`/user/findById/${id}`);
            return response.data;
        } catch (error) {
            throw error;
        }
    },

    findAll : async () => {
        try{
            const response = await api.get('/user/findAll');
            return response.data;
        }catch (error){
            throw error;
        }
    },

    create : async (user) => {
        try{
            const response = await api.post('/user/create', user);
            return response.data
        }catch (error) {
            throw error;
        }
    },

    update : async (user, id) => {
        try{
            const response = await api.put(`/user/update/${id}`, user);
            return response.data;
        }catch (error){
            throw error;
        }
    },

    delete: async (id) => {
        try {
            await api.delete(`/user/delete/${id}`);
        } catch (error) {
            throw error;
        }
    }
};