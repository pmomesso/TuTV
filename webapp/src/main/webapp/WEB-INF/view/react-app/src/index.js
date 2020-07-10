import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import './css/resize_images.css';
import './css/tutv.css';
import './css/react-contextmenu.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import './i18n';
import * as serviceWorker from './serviceWorker';
import { createStore } from 'redux'
import rootReducer from './store/reducers/rootReducer'
import { Provider } from 'react-redux'
import Axios from 'axios';


const store = createStore(rootReducer);

//TODO remove this 2 lines
const APIURL = "http://localhost:8080";
Axios.defaults.baseURL = APIURL;

Axios.defaults.headers['Content-Type'] = 'application/json';

let token = localStorage.getItem("authToken");
let userJson = localStorage.getItem("authUserJson");

let user;

try {
    user = JSON.parse(userJson);

    if (token !== null && userJson != null) {
        store.dispatch({ type: "LOGIN", payload: { "token": token, "user": user, "updateLocalStorage": false } })
    }
} catch (e) {

}



ReactDOM.render(
    <Provider store = { store } >
        <App/>
    </Provider>,
    document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();