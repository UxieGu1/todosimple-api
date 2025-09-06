import api from 'api';

export const TaskService = {
    findById : async (id) => {
        try {
            const response = await api.get(`/task/findById/${id}`);
            return response.data;
        }catch (error) {
            throw error;
        }
    },

    findAll : async () => {
        try{
            const response = await api.get('/task/findAll');
            return response.data;
        }catch (error) {
            throw error;
        }
    },

    create : async (task) => {
        try{
            const response = await api.create('/task/create', task);
            return response.data;
        }catch (error){
            throw error;
        }
    },

    update : async (task, id) => {
        try{
            const response = await api.put(`/task/update/${id}`, task);
            return response.data;
        }catch (error){
            throw error;
        }
    },

    delete : async (id) => {
        try{
            const response = await api.delete(`/task/delete/${id}`);
            return response.data;
        }catch (error){
            throw error;
        }
    }
};