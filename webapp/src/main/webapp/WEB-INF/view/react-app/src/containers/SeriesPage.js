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

import { connect } from 'react-redux';

class SeriesPage extends Component {
    state = {
        id: null,
        series: null,
        comments: null,
        loading: true
    }

    componentDidMount = () => {
        let seriesId = this.props.match.params.series_id;

        Axios.get("/series/" + seriesId)
                .then(res => {
                    this.setState({
                        series: res.data,
                        loading: false
                    })
                });
    }

    onEpisodeWatchedClickedHandler = (event, season_index, episode_index) => {
        event.preventDefault();

        if(this.props.logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        let series = this.state.series;

        let newValue = !this.state.series.seasons[season_index].episodes[episode_index].viewedByUser;

        let newEpisode = {
            ...this.state.series.seasons[season_index].episodes[episode_index],
            "viewedByUser": newValue
        }

        let newSeasonEpisodeList = [ ...this.state.series.seasons[season_index].episodes ]
        newSeasonEpisodeList[episode_index] = newEpisode;

        let newSeason = {
            ...this.state.series.seasons[season_index],
            "episodes": newSeasonEpisodeList
        }

        let newSeasonList = [ ...this.state.series.seasons ]
        newSeasonList[season_index] = newSeason;

        let newSeries = {
            ...this.state.series,
            "seasons": newSeasonList
        }

        let data = { "viewed": newValue };

        Axios.put("/series/" + this.state.series.id + "/seasons/" + series.seasons[season_index].id + "/episodes/" + series.seasons[season_index].episodes[episode_index].id, JSON.stringify(data))
            .then((res) => {
        
                this.setState({ "series": newSeries });
            })
            .catch((err) => {
                /* TODO SI CADUCO LA SESION? */
                //alert("Error: " + err.response.status);
            });

        

    }

    onSeasonWatchedClicked = (event, season_index) => {
        event.preventDefault();

        if(this.props.logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        let newValue;// = true;
        
        if(this.state.series.seasons[season_index].episodes.length > 0)
            newValue = !this.state.series.seasons[season_index].episodes.every((episode) => episode.viewedByUser === true);

        let newSeasonEpisodeList = [];

        this.state.series.seasons[season_index].episodes.forEach((episode) => {
            let newEpisode = {
                ...episode,
                "viewedByUser": newValue
            }

            newSeasonEpisodeList.push(newEpisode);
        });

        let newSeason = {
            ...this.state.series.seasons[season_index],
            "episodes": newSeasonEpisodeList
        };

        let newSeasonList = [ ...this.state.series.seasons ];
        newSeasonList[season_index] = newSeason;

        let newSeries = {
            ...this.state.series,
            "seasons": newSeasonList
        };

        let data = { "viewed": newValue };

        Axios.put("/series/" + this.state.series.id + "/seasons/" + this.state.series.seasons[season_index].id, JSON.stringify(data))
            .then((res) => {
        
                this.setState({ "series": newSeries });
            })
            .catch((err) => {
                /* TODO SI CADUCO LA SESION? */
                //alert("Error: " + err.response.status);
            });

        this.setState({ "series": newSeries });
    }

    onRatingChanged = (newValue) => {
        if(this.props.logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        let data = { "loggedInUserRating": newValue };

        Axios.put("/series/" + this.state.series.id, JSON.stringify(data))
            .then((res) => {
                let newSeries = {
                    ...this.state.series,
                    userRating: res.data.userRating,
                    loggedInUserRating: newValue
                }
        
                this.setState({
                    series: newSeries
                });
            })
            .catch((err) => {
                /* TODO SI CADUCO LA SESION? */
                //alert("Error: " + err.response.status);
            });
    }

    onSeriesFollowClicked = () => {

        if(this.props.logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        let newValue = !this.state.series.loggedInUserFollows;

        let data = { "loggedInUserFollows": newValue };

        Axios.put("/series/" + this.state.series.id, JSON.stringify(data))
            .then((res) => {
                let newSeries = {
                    ...this.state.series,
                    followers: res.data.followers,
                    loggedInUserFollows: newValue
                }
        
                this.setState({
                    series: newSeries
                });
            })
            .catch((err) => {
                /* TODO SI CADUCO LA SESION? */
                //alert("Error: " + err.response.status);
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
        
        const series = this.state.series;

        let seasonList = [];

        let seasonCount = series.seasons.length;
        for(let i = 0; i < seasonCount; i++) {
            seasonList.push(
                <SeasonAccordion number={i} key={i} season={series.seasons[i]} onSeasonWatchedClicked={this.onSeasonWatchedClicked} onEpisodeWatchedClickedHandler={this.onEpisodeWatchedClickedHandler}/>
            );
        }

        //let reviewList = [];

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
                                            <h2>
                                                {(series.userRating == null) ? 
                                                <Trans i18nKey="series.no_rating"/>
                                                : series.userRating + " / 5"}
                                                </h2>
                                        </div>
                                        <button className="add-button" onClick={this.onSeriesFollowClicked}>
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
                                            <Trans i18nKey="series.seasons" count={ seasonCount }/>
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
                                                //fractions="2"
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

const mapStateToProps = (state) => {
    return {
        logged_user: state.auth.user
    }
}

export default connect(mapStateToProps)(SeriesPage);