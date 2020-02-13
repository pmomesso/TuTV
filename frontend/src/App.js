import React, {Component} from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom'

import Login from './containers/Login';
import Register from "./containers/Register";
import MailConfirm from "./containers/MailConfirm";
import DefaultContainer from './containers/DefaultContainer';

import 'react-activity/dist/react-activity.css';

import { connect } from 'react-redux';
import Logout from './containers/Logout';
//import Axios from 'axios';

class App extends Component {
  componentDidMount() {
    let token = localStorage.getItem("authToken");
    let userJson = localStorage.getItem("authUserJson");

    let user;

    try {
      user = JSON.parse(userJson)
    } catch (e) {
        return;
    }
  
    if(token !== null && userJson != null) {
      this.props.loginUser(token, user);
    }
  }

  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route exact path='/mailconfirm/:token' component={MailConfirm}/>
          <Route exact path='/logout' component={Logout}/>
          <Route path='/login' component={Login}/>
          <Route exact path='/register' component={Register}/>
          <Route component={DefaultContainer}/>
        </Switch>
      </BrowserRouter>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
      loginUser: (token, user) => { dispatch({ type: "LOGIN", payload: { "token": token, "user": user, "updateLocalStorage": false } }) }
  }
}

export default connect(null, mapDispatchToProps)(App);