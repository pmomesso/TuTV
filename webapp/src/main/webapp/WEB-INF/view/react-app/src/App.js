import React from 'react';
import { BrowserRouter } from 'react-router-dom'

import 'react-activity/dist/react-activity.css';

import 'react-confirm-alert/src/react-confirm-alert.css';

import ReactNotification from 'react-notifications-component'
import 'react-notifications-component/dist/theme.css'

import RouterContainer from './RouterContainer';

const App = () => {
  return (
    <div className="app-container h-100">
      <ReactNotification />
      <BrowserRouter basename="/paw-2019b-1">
        <RouterContainer />
      </BrowserRouter>      
    </div>
  );
}

export default App;