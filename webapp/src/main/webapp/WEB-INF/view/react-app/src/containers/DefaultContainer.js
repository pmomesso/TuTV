import React from 'react';
import { Route } from 'react-router-dom';

import Navbar from '../components/Navbar'
import Explore from './Explore';
import SeriesPage from './SeriesPage';

import 'bootstrap/dist/css/bootstrap.min.css';
import UsersPage from "./UsersPage";
import ProfilePage from './ProfilePage';
import Watchlist from './Watchlist';
import Upcoming from './Upcoming';
import Search from './Search';
import ErrorPage from './ErrorPage';

const DefaultContainer = () => {
    return (
        <div className="App home no-touch white reduced-right h-100" id="container">
          <div className="body-inner h-100">
            <Navbar/>

            <div className="page-center page-column ">
              <div className="page-center-inner">
                  <Route exact path='/' component={Explore}/>
                  <Route path='/series/:series_id' component={SeriesPage}/>
                  <Route path='/profiles/:profile_id' component={ProfilePage}/>
                  <Route path='/users' render={() => (<UsersPage source={"/users"}/>)}/>
                  <Route path='/watchlist' component={Watchlist}/>
                  <Route path='/upcoming' component={Upcoming}/>
                  <Route path='/search' component={Search}/>
                  <Route path='*' exact={true} render={() => (<ErrorPage status={"error.404status"} body={"error.404body"}/>)} />
              </div>
            </div>  

          </div>
        </div>
    );
        
};
export default DefaultContainer;