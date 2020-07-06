import React from 'react';
import { Switch, Route } from 'react-router-dom';

import Login from './containers/Login';
import Register from "./containers/Register";
import MailConfirm from "./containers/MailConfirm";
import DefaultContainer from './containers/DefaultContainer';
import Logout from './containers/Logout';

const RouterContainer = () => {
    return (
            <Switch>
                <Route exact path='/mailconfirm/:token' component={MailConfirm} />
                <Route exact path='/logout' component={Logout} />
                <Route path='/login' component={Login} />
                <Route exact path='/register' component={Register} />
                <Route component={DefaultContainer} />
            </Switch>

    );
}

export default RouterContainer;