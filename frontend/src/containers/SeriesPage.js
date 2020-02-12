import React, {Component} from 'react';
import { Trans } from 'react-i18next';

//import $ from 'jquery';
//import Popper from 'popper.js';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import Rating from 'react-rating';

import SeasonAccordion from '../components/SeasonAccordion';
import Axios from 'axios';

import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';

import * as CONSTANTS from '../constants.js'

class SeriesPage extends Component {
    state = {
        id: null,
        series: null,
        comments: null,
        loading: true
    }

    componentDidMount = () => {
        let seriesId = this.props.match.params.series_id;

        Axios.get(CONSTANTS.APIURL + "/series/" + seriesId)
                .then(res => {
                    this.setState({
                        series: res.data,
                        loading: false
                    })
                });
    }

    onEpisodeWatchedClickedHandler = (event, season_index, episode_index) => {
        event.preventDefault();
        alert("onEpisodeWatched S" + season_index + "E" + episode_index);
    }

    onSeasonWatchedClicked = (event) => {
        event.preventDefault();
        alert("onSeasonWatched");
        /*this.setState({
            "series.season[0].viewedByUser": true
        });*/
    }

    onRatingChanged = (newValue) => {
        alert("Puntaje: " + newValue);
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
        
        const series = this.state.series;

        let seasonList = [];

        let seasonCount = series.seasons.length;
        for(let i = 0; i < seasonCount; i++) {
            seasonList.push(
                <SeasonAccordion number={i} key={i} season={series.seasons[i]} onEpisodeWatchedClickedHandler={this.onEpisodeWatchedClickedHandler}/>
            );
        }

        return (
            <div className="main-block">
                <div id="explore" style={{backgroundColor: "#ededed"}}>
                    <section id="new-shows">
                        <h1>{series.name}</h1>
                        <div id="myCarousel" className="carousel slide" data-ride="carousel">
                            <div className="carousel-inner">
                                <div className="carousel-item active">
                                    <img src={"https://image.tmdb.org/t/p/original"  + series.bannerUrl} itemProp="image" alt=""/>
                                    <div className="carousel-caption">
                                        <div className="text-center">
                                            <span className={series.loggedInUserFollows ? "star" : "star-unchecked"} />
                                            <h2>{series.userRating + " / 5"}</h2>
                                        </div>
                                        <button className="add-button" type="submit">
                                            <Trans i18nKey={series.loggedInUserFollows ? "series.unfollow" : "series.follow"} />
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <div className="main-block-container">
                        <div id="show-details" className="show">
                            <div className="row show-nav">
                                <div className="col-lg-8">
                                    <div className="basic-infos">
                                        <span>{ series.network }</span>
                                        <span className="separator">•</span>
                                        <span>
                                            <Trans i18nKey="series.seasons" count={ 3 }/>
                                        </span>
                                    </div>
                                    <div className="overview">
                                        { series.seriesDescription }
                                    </div>
                                    <div className="followers">
                                        <Trans i18nKey="index.followers" count={ series.followers }/>
                                    </div>
                                </div>
                                <div className="col-lg-4">
                                    <div className="container h-20">
                                        <div className="starrating risingstar d-flex justify-content-right flex-row-reverse">
                                            <Rating 
                                                initialRating={ series.loggedInUserRating }
                                                emptySymbol={<span className="star-unchecked"/>}
                                                fullSymbol={<span className="star"/>}
                                                fractions="2"
                                                onChange={this.onRatingChanged}/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="content" className="show-main alt">
                                <div className="content-container">
                                    <div className="left">
                                        <div className="w-100 no-padding no-margin">

                                           {seasonList}

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default SeriesPage;