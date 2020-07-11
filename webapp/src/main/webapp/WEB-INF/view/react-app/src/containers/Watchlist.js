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
import $ from "jquery";

let linkHeaderParser = require('parse-link-header');

class Watchlist extends Component {

    state = {
        source: null,
        shows: null,
        prevUrl: null,
        nextUrl: null,
        loading: true
    };

    nextPage = () => {
        this.setState({
            source: this.state.nextUrl,
            loading: true
        }, this.fetchData);
    };

    prevPage = () => {
        this.setState({
            source: this.state.prevUrl,
            loading: true
        }, this.fetchData);
    };

    fetchData = () => {
        var width = window.screen.width;
        var height = window.screen.height;

        var section_width = $(".page-center-inner")[0].offsetWidth - 40 - 24;

        var pagesize;
        if (width > 768)
            pagesize = Math.floor((section_width - section_width*0.04)/187);
        else
            pagesize = Math.floor((section_width - section_width*0.04)/148);

        if (height > 750)
            pagesize *= 3;
        else
            pagesize *= 2;

        Axios.get(this.state.source, {params: {pagesize: pagesize}})
            .then(res => {
                let linkHeader = res.headers["link"];
                let linkHeaderParsed = linkHeaderParser(linkHeader);

                let nextUrl = null;
                let prevUrl = null;
                if(linkHeaderParsed) {
                    nextUrl = linkHeaderParsed.next ? linkHeaderParsed.next.url : null;
                    prevUrl = linkHeaderParsed.prev ? linkHeaderParsed.prev.url : null;
                }

                this.setState({
                    shows: res.data,
                    nextUrl: nextUrl,
                    prevUrl: prevUrl,
                    loading: false
                });
            });
    };

    componentDidMount = () => {
        if(this.props.logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }
        if (this.state.source === null) {
            this.setState({source: "/users/" + this.props.logged_user.id + "/watchlist"}, this.fetchData)
        } else {
            this.setState( {source: this.props.source }, this.fetchData)
        }
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
                                    <section id="new-shows" className="h-100 p-0">
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
                                            {
                                                (typeof this.state.prevUrl === "string") &&
                                                    <span className="clickable carousel-genre-left float-left" data-slide="prev" onClick={this.prevPage}>
                                                        <span className="carousel-control-prev-icon my-prev-icon"></span>
                                                    </span>
                                            }

                                            { watchlist }

                                            {
                                                (typeof this.state.nextUrl === "string") &&
                                                    <span className="clickable carousel-genre-right float-left" data-slide="next" onClick={this.nextPage}>
                                                        <span className="carousel-control-next-icon my-next-icon"></span>
                                                    </span>
                                            }
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