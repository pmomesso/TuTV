import React from 'react';
import { BrowserRouter } from 'react-router-dom'

import 'react-activity/dist/react-activity.css';

import ReactNotification from 'react-notifications-component'
import 'react-notifications-component/dist/theme.css'

import RouterContainer from './RouterContainer';

const App = () => {
  return (
    <div className="app-container h-100">
      <ReactNotification />
      <BrowserRouter>
        <RouterContainer />
      </BrowserRouter>      
    </div>
  );
}

export default App;