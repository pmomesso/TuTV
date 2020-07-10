import React, { Component } from 'react';
import TvSeriesPosterUpcoming from '../components/TvSeriesPosterUpcoming'
import { Trans } from 'react-i18next';
import { NavLink } from 'react-router-dom';
import Axios from 'axios';
import { connect } from 'react-redux';

import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';
import $ from "jquery";

let linkHeaderParser = require('parse-link-header');

class Upcoming extends Component {

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
            this.setState({source: "/users/" + this.props.logged_user.id + "/upcoming"}, this.fetchData)
        } else {
            this.setState( {source: this.props.source }, this.fetchData)
        }
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

        let upcoming = this.state.shows.map((showEpisodePair) => {
            return(
                <TvSeriesPosterUpcoming key={ showEpisodePair.series.id } series={ showEpisodePair.series } episode={ showEpisodePair.episode } />
            );
        });

        return (
            <div className="main-block h-100">
                <div className="main-block-container h-100">
                    <div id="home" className="h-100">
                        <section id="new-shows" className="h-100">
                            {
                                (!upcoming.length) ?
                                    <div className="container h-100">
                                        <div className="row justify-content-center h-100">
                                            <div className="col-lg-8 col-sm-12 align-self-center">
                                                <div className="text-center">
                                                    <h2>
                                                        <Trans i18nKey="upcoming.noshows" />
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
                                    <div className="h-100">
                                        <h1><Trans i18nKey="upcoming.title" /></h1>
                                        <ul className="to-watch-list posters-list list-unstyled list-inline single-row">
                                            {
                                                (typeof this.state.prevUrl === "string") &&
                                                    <span className="clickable carousel-genre-left float-left" data-slide="prev" onClick={this.prevPage}>
                                                        <span className="carousel-control-prev-icon my-prev-icon"></span>
                                                    </span>
                                            }

                                            { upcoming }

                                            {
                                                (typeof this.state.nextUrl === "string") &&
                                                    <span className="clickable carousel-genre-right float-left" data-slide="next" onClick={this.nextPage}>
                                                        <span className="carousel-control-next-icon my-next-icon"></span>
                                                    </span>
                                            }
                                        </ul>
                                    </div>
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
};

export default connect(mapStateToProps)(Upcoming);