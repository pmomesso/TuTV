import React, { Component } from 'react';
import { Trans } from 'react-i18next';
import { NavLink } from 'react-router-dom';
import Axios from 'axios';
import { connect } from 'react-redux';

import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';

class Watchlist extends Component {

    state = {
        shows: null,
        loading: true
    }

    componentDidMount = () => {
        if(this.props.logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        Axios.get("/series/genres")
        .then(res => {
            this.setState({
                loading: false
            })
        });
    }

    render() {        
        if(this.state.loading)
        return (
            <section>
                    <div style={{width: "100%",height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
                        <Digital color="#727981" size={32} speed={1} animating={true} />
                    </div>
                </section>
            );
            
        let watchlist = this.state.shows.map((showEpisodePair) => {
            return <h1>prueba</h1>;
        });
        
        return (
            <div className="main-block h-100">
                    <div className="main-block-container h-100">
                        <div id="home" className="h-100">
                            <section id="new-shows" className="h-100">
                                {
                                    (!watchlist.length) ?
                                    <div className="container h-100">
                                        <div className="row justify-content-center h-100">
                                            <div className="col-lg-8 col-sm-12 align-self-center">
                                                <div className="text-center">
                                                    <h2>
                                                        <Trans i18nKey="watchlist.noshows" />
                                                    </h2>
                                                </div>
                                                <div className="text-center">
                                                    <img src={require('../img/noshows.png')} alt="" />
                                                </div>
                                                <div className="text-center m-4">
                                                    <h4>
                                                        <Trans i18nKey="watchlist.discover" />
                                                    </h4>
                                                </div>
                                                <div className="text-center m-4">
                                                    <NavLink to="/" className="tutv-button m-4">
                                                        <Trans i18nKey="watchlist.explore" />
                                                    </NavLink>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    :
                                    <h1>Series</h1>
                                }
                            </section>
                        </div>
                    </div>
                </div>
        )
    }
    
}

const mapStateToProps = (state) => {
    return {
        logged_user: state.auth.user
    }
}

export default connect(mapStateToProps)(Watchlist);