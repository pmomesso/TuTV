const initState = {
    "token": localStorage.getItem("authToken"),
    "user": null
};

const authReducer = (state = initState, action) => {
    if(action.type === "LOGIN") {
        localStorage.setItem("authToken", action.payload.token)
        return {
            ...state,
            token:  action.payload.token,
            user:   action.payload.user
        }
    }
    return state;
}

export default authReducer;