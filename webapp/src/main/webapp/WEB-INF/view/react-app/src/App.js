import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom'

import Login from './containers/Login';
import Register from "./containers/Register";
import MailConfirm from "./containers/MailConfirm";
import DefaultContainer from './containers/DefaultContainer';

import 'react-activity/dist/react-activity.css';

import Logout from './containers/Logout';

const App = () => {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path='/mailconfirm/:token' component={MailConfirm} />
        <Route exact path='/logout' component={Logout} />
        <Route path='/login' component={Login} />
        <Route exact path='/register' component={Register} />
        <Route component={DefaultContainer} />
      </Switch>
    </BrowserRouter>
  );
}

export default App;