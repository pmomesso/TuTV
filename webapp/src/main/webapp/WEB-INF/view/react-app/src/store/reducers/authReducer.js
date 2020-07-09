import Axios from "axios";

const initState = {
    "token": null,
    "user": null
};

const authReducer = (state = initState, action) => {
    if(action.type === "LOGIN") {
        if(action.payload.updateLocalStorage) {
            localStorage.setItem("authToken", action.payload.token);
            localStorage.setItem("authUserJson", JSON.stringify(action.payload.user));
        }
        
        Axios.defaults.headers.common['Authorization'] = action.payload.token;
        return {
            ...state,
            token:  action.payload.token,
            user:   action.payload.user
        }
    } else if(action.type === "LOGOUT") {
        localStorage.removeItem("authToken");
        localStorage.removeItem("authUserJson");

        delete Axios.defaults.headers.common['Authorization'];

        return {
            ...state,
            token: null,
            user: null
        };
    } else if(action.type === "UPDATE_USERNAME") {
        if(action.payload.updateLocalStorage) {
            localStorage.setItem("authUserJson", JSON.stringify(action.payload.user));
        }
        return {
            ...state,
            user: action.payload.user
        }
    }

    return state;
};

export default authReducer;