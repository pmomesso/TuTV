import React, {Component} from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom'

import Login from './containers/Login';
import Register from "./containers/Register";
import DefaultContainer from './containers/DefaultContainer';

import 'react-activity/dist/react-activity.css';

//import './js/navigator';

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route exact path='/login' component={Login}/>
          <Route exact path='/register' component={Register}/>
          <Route component={DefaultContainer}/>
        </Switch>
      </BrowserRouter>
    );
  }
}

export default App;

/*
[
    {
        "id": 1,
        "name": "Once Upon A Time",
        "posterUrl": "/49qD372jeHUTmdNMGJkjCFZdv9y.jpg",
        "followers": 13,
        "bannerUrl": "/49qD372jeHUTmdNMGJkjCFZdv9y.jpg"
    },
    {
        "id": 2,
        "name": "Cleverman",
        "posterUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg",
        "followers": 0,
        "bannerUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg"
    },
{
        "id": 3,
        "name": "Cleverman",
        "posterUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg",
        "followers": 0,
        "bannerUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg"
    },
{
        "id": 4,
        "name": "Cleverman",
        "posterUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg",
        "followers": 0,
        "bannerUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg"
    }


]
*/