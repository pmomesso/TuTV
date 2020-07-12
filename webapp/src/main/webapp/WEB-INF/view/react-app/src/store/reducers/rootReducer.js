import authReducer from './authReducer';
import notifReducer from './notifReducer';
import searchReducer from './searchReducer';

import { combineReducers } from 'redux';

const rootReducer = combineReducers({
    auth:   authReducer,
    notif:  notifReducer,
    search: searchReducer
});

export default rootReducer;