import React from 'react';
import { Route } from 'react-router-dom';

import Navbar from '../components/Navbar'
import Explore from './Explore';
import SeriesPage from './SeriesPage';

import 'bootstrap/dist/css/bootstrap.min.css';
import '../css/tvst.css';
import ProfilePage from './ProfilePage';

const DefaultContainer = () => {

    return (
        <div className="App home no-touch white reduced-right" id="container">
          <div className="body-inner">
            <Navbar/>

            <div className="page-center page-column ">
              <div className="page-center-inner">
                <Route exact path='/' component={Explore}/>
                <Route path='/series/:series_id' component={SeriesPage}/>
                <Route path='/profiles/:profile_id' component={ProfilePage}/>
              </div>
            </div>  

          </div>
        </div>
    );
        
}
export default DefaultContainer;