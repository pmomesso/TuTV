import React, {Component} from 'react';
import { withTranslation } from 'react-i18next';

import 'bootstrap/dist/js/bootstrap.bundle.min';
import Rating from 'react-rating';

import SeasonAccordion from '../components/SeasonAccordion';
import Axios from 'axios';

import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';

import { connect } from 'react-redux';
import { compose } from 'recompose';
import Discussion from '../components/Discussion';
import { confirmAlert } from 'react-confirm-alert';
import ErrorPage from "./ErrorPage";

class SeriesPage extends Component {
    state = {
        id: null,
        series: null,
        comments: null,
        loading: true,
        error: false
    };

    componentDidMount = () => {
        this.setState({
            loading: true
        }, () => {
            this.fetchData();
        })
    };

    componentDidUpdate = (prevProps) => {
        if (this.props.match.params.series_id !== prevProps.match.params.series_id ) {
            this.fetchData();
        }
    };

    fetchData = () => {
        let seriesId = this.props.match.params.series_id;

        Axios.get("/series/" + seriesId)
            .then(res => {
                this.setState({
                    series: res.data,
                    loading: false
                })
            }).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
    };

    arePreviousNotViewed = (season_index, episode_index) => {
        for (var i = 0; i <= season_index; i++) {
            for (var j = 0; j < this.state.series.seasons[i].episodes.length; j++) {
                if (i === season_index && j === episode_index) return false;
                if (!this.state.series.seasons[i].episodes[j].viewedByUser) return true;
            }
        }
        return false;
    };

    onEpisodeWatchedClickedHandler = (event, season_index, episode_index) => {
        const {t} = this.props;

        if (this.props.logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        let series = this.state.series;

        if (!series.loggedInUserFollows) this.onSeriesFollowClicked();

        let newValue = !this.state.series.seasons[season_index].episodes[episode_index].viewedByUser;

        let data = {"viewedByUser": newValue};

        if (newValue) {
            if (this.arePreviousNotViewed(season_index, episode_index)) {
                confirmAlert({
                    title: t("series.deleteConfirmTitle"),
                    message: t("series.viewPrevious"),
                    buttons: [
                        { label: t("series.yes"), onClick: () => { this.updateEpisodeWatched(season_index, episode_index, data, true); }},
                        { label: t("series.no"), onClick: () => { this.updateEpisodeWatched(season_index, episode_index, data); }}]
                });
            } else {
                this.updateEpisodeWatched(season_index, episode_index, data);
            }
        } else {
            this.updateEpisodeWatched(season_index, episode_index, data);
        }
    };

    updateEpisodeWatched = (season_index, episode_index, data, markPrevious = false) => {
        let series = this.state.series;
        const markPreviousQueryParameter = markPrevious ? "?markPrevious=true" : "";
        Axios.put("/series/" + series.id + "/seasons/" + series.seasons[season_index].number + "/episodes/" + series.seasons[season_index].episodes[episode_index].numEpisode + "/viewed" + markPreviousQueryParameter, JSON.stringify(data))
            .then(() => {
                this.fetchData();
            }).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
    };

    onSeasonWatchedClicked = (event, season_index) => {
        const {t} = this.props;

        if(this.props.logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        let series = this.state.series;
        
        if (!series.loggedInUserFollows) this.onSeriesFollowClicked();

        let newValue;// = true;
        
        if(series.seasons[season_index].episodes.length > 0)
            newValue = !this.state.series.seasons[season_index].episodes.every((episode) => episode.viewedByUser === true);

        let data = { "viewedByUser": newValue };

        if (newValue) {
            if (this.arePreviousNotViewed(season_index, 0)) {
                confirmAlert({
                    title: t("series.deleteConfirmTitle"),
                    message: t("series.viewPrevious"),
                    buttons: [
                        { label: t("series.yes"), onClick: () => { this.updateSeasonWatched(season_index, data, true); }},
                        { label: t("series.no"), onClick: () => { this.updateSeasonWatched(season_index, data); }}]
                });
            } else {
                this.updateSeasonWatched(season_index, data);
            }
        } else {
            this.updateSeasonWatched(season_index, data);
        }
    };

    updateSeasonWatched = (season_index, data, markPrevious = false) => {
        let series = this.state.series;
        const markPreviousQueryParameter = markPrevious ? "?markPrevious=true" : "";
        Axios.put("/series/" + series.id + "/seasons/" + series.seasons[season_index].number + "/viewed" + markPreviousQueryParameter, JSON.stringify(data))
            .then(() => {
                this.fetchData();
            }).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
    };

    onRatingChanged = (newValue) => {
        if(this.props.logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        let data = { "loggedInUserRating": newValue };

        Axios.put("/series/" + this.state.series.id + "/rating", JSON.stringify(data))
            .then((res) => {
                let newSeries = {
                    ...this.state.series,
                    userRating: res.data.seriesRating,
                    loggedInUserRating: newValue
                };
        
                this.setState({
                    series: newSeries
                });
            }).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
    };

    onSeriesFollowClicked = () => {

        if(this.props.logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        let newValue = !this.state.series.loggedInUserFollows;

        let promise;
        if(newValue) {
            promise = Axios.post("/users/" + this.props.logged_user.id + "/following", 
                { "seriesId": this.state.series.id });
        } else {
            promise = Axios.delete("/users/" + this.props.logged_user.id + "/following/" + this.state.series.id);
        }

        promise
            .then((res) => {
                let newSeries = {
                    ...this.state.series,
                    followers: res.data.followers,
                    loggedInUserFollows: newValue
                }
        
                this.setState({
                    series: newSeries
                });
            }).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
    };

    render() {

        const { t } = this.props;

        if(this.state.loading)
            return (
                <section>
                    <div style={{width: "100%",height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
                        <Digital color="#727981" size={32} speed={1} animating={true} />
                    </div>
                </section>
            );

        if (!this.state.error) {

            const series = this.state.series;

            let seasonList = [];

            let seasonCount = series.seasons.length;
            for (let i = 0; i < seasonCount; i++) {
                seasonList.push(
                    <SeasonAccordion number={i} key={i} season={series.seasons[i]}
                                     onSeasonWatchedClicked={this.onSeasonWatchedClicked}
                                     onEpisodeWatchedClickedHandler={this.onEpisodeWatchedClickedHandler}/>
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
                                        <img src={"https://image.tmdb.org/t/p/original" + series.bannerUrl}
                                             itemProp="image" alt=""/>
                                        <div className="carousel-caption">
                                            <div className="text-center">
                                                <span
                                                    className={series.loggedInUserFollows ? "star" : "star-unchecked"}/>
                                                <h2>
                                                    {(series.userRating == null) ?
                                                        t("series.no_rating")
                                                        : series.userRating.toFixed(1) + " / 5.0"}
                                                </h2>
                                            </div>
                                            <button className="add-button" onClick={this.onSeriesFollowClicked}>
                                                {t(series.loggedInUserFollows ? "series.unfollow" : "series.follow")}
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
                                            <span>{series.network}</span>
                                            <span className="separator">•</span>
                                            <span>
                                                {t("series.seasons", {count: seasonCount})}
                                            </span>
                                        </div>
                                        <div className="overview">
                                            {series.seriesDescription}
                                        </div>
                                        <div className="followers">
                                            {t("index.followers", {count: series.followers})}
                                        </div>
                                    </div>
                                    <div className="col-lg-4">
                                        <div className="container h-20">
                                            <div
                                                className="starrating risingstar d-flex justify-content-right flex-row-reverse">
                                                <Rating
                                                    initialRating={series.loggedInUserRating}
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
                                            <Discussion key={this.state.series} series={this.state.series}/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        } else {
            return <ErrorPage status={this.state.error_status} body={this.state.error_body}/>
        }
    }
}

const mapStateToProps = (state) => {
    return {
        logged_user: state.auth.user
    }
}

export default compose(
    connect(mapStateToProps),
    withTranslation()
)(SeriesPage);