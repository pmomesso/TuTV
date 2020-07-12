const initState = {
    searchName: ""
};

const searchReducer = (state = initState, action) => {
    if(action.type === "UPDATE_SEARCH") {
        return {
            ...state,
            searchName: action.payload.searchName
        }
    }

    return state;
};

export default searchReducer;