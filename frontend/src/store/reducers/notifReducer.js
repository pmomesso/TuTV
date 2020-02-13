const initState = {
    "loading": false
};

const notifReducer = (state = initState, action) => {
    if(action.type === "SET_LOADING") {
        return {
            ...state,
            loading:  action.payload
        }
    }

    return state;
}

export default notifReducer;