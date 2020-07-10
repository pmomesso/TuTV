import React, { Component } from 'react';
import { Trans, withTranslation } from 'react-i18next';
import { NavLink } from 'react-router-dom';
import Axios from 'axios';
import { connect } from 'react-redux';
import { compose } from 'recompose';
import { store } from 'react-notifications-component';
import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';
import TvSeriesPosterWatchlist from "../components/TvSeriesPosterWatchlist";

class Watchlist extends Component {

    state = {
        shows: null,
        loading: true
    };

    componentDidMount = () => {
        if(this.props.logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        Axios.get("/users/" + this.props.logged_user.id + "/watchlist")
        .then(res => {
            this.setState({
                shows: res.data,
                loading: false
            })
        });
    };

    onEpisodeWatchedClickedHandler = (event, series_id, season_index, episode_index, series_name, message) => {
        event.preventDefault();
        const { t, logged_user } = this.props;

        Axios.put("/series/" + series_id + "/seasons/" + season_index + "/episodes/" + episode_index + "/viewed", JSON.stringify({"viewedByUser": true}))
            .then(() => {
                Axios.get("/users/" + logged_user.id + "/watchlist")
                    .then(res => {
                        this.setState({
                            shows: res.data
                        });
                        store.addNotification({
                            title:  t("lists.success"),
                            message:  series_name + " " + t("watchlist.season") + message + t("series.watched"),
                            type: "success",
                            insert: "top",
                            container: "top-right",
                            animationIn: ["animated", "fadeIn"],
                            animationOut: ["animated", "fadeOut"],
                            dismiss: {
                                duration: 4000,
                                onScreen: true
                            }
                        });
                    });
            })
            .catch((err) => {
                /* TODO SI CADUCO LA SESION? */
                //alert("Error: " + err.response.status);
            });
    };

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
            return(
                <TvSeriesPosterWatchlist key={ showEpisodePair.series.id } series={ showEpisodePair.series } episode={ showEpisodePair.episode } onEpisodeWatchedClickedHandler={this.onEpisodeWatchedClickedHandler} />
            );
        });
        
        return (
            <div className="main-block h-100">
                    <div className="main-block-container h-100">
                        <div id="home" className="h-100">
                            {
                                (!watchlist.length) ?
                                    <section id="new-shows" className="h-100">
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
                                    </section>
                                    :
                                    <section id="new-shows" className="h-100">
                                        <h1><Trans i18nKey="watchlist.watchNext" /></h1>
                                        <ul className="to-watch-list posters-list list-unstyled list-inline single-row">
                                            { watchlist }
                                        </ul>
                                    </section>
                            }
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
};

export default compose(
    connect(mapStateToProps),
    withTranslation()
)(Watchlist);