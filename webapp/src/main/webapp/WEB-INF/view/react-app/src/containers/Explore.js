import React, {Component} from 'react';
import SeriesList from '../components/SeriesList'
import Axios from 'axios';
import SeriesCarousel from '../components/SeriesCarousel';
import { connect } from 'react-redux';
import { compose } from 'recompose';
import { withTranslation } from 'react-i18next';
import { store } from 'react-notifications-component';
import ErrorPage from "./ErrorPage";

class Explore extends Component {
    state = {
        bannerSeries: [],
        userLists: null,
        genreList: [],
        error: false
    };

    componentDidMount = () => {
        Axios.get("/genres")
        .then(res => {
            const genres = res.data.sort((a,b)=> a.name.localeCompare(b.name));

            this.setState({
                genreList: genres,
            })
        }).catch((err) => {
            const error_status = "error." + err.response.status + "status";
            const error_body = "error." + err.response.status + "body";
            this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
        });

        if(this.props.logged_user)
            Axios.get("/users/" + this.props.logged_user.id + "/lists")
            .then(res => {
                this.setState({
                    userLists: res.data,
                })
            }).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
    };

    createSeriesListAndAddSeriesHandler = (series) => {
        const { t, logged_user } = this.props;
        let listName = window.prompt(t("series.newListName"),"");

        if(!listName) {
            store.addNotification({
                title: "Error",
                message: t("lists.noName"),
                type: "danger",
                insert: "top",
                container: "top-right",
                animationIn: ["animated", "fadeIn"],
                animationOut: ["animated", "fadeOut"],
                dismiss: {
                    duration: 4000,
                    onScreen: true
                }
            });
            return;
        }

        Axios.post("/users/" + logged_user.id + "/lists", JSON.stringify({"name": listName}))
            .then(res => {
                this.setState({
                    ...this.state,
                    userLists: [res.data].concat(this.state.userLists)
                });
                if (res.status === 200) {
                    store.addNotification({
                        title:  t("lists.success"),
                        message:  listName + t("lists.added"),
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
                }

                this.addSeriesToListHandler(res.data, series);
            }).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
    };

    addSeriesToListHandler = (list, series) => {
        const { t, logged_user } = this.props;

        if(!list) { //Crear lista
            this.createSeriesListAndAddSeriesHandler(series);
        } else {
            Axios.post("/users/" + logged_user.id + "/lists/" + list.id + "/series", JSON.stringify({"seriesId": series.id}))
                .then(res => {
                    if (res.status === 200) {
                        store.addNotification({
                            title:  t("lists.success"),
                            message:  series.name + t("lists.addedTo") + list.name,
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
                    }
                })
                .catch(res => {
                    if (res.response.status === 304) {
                        store.addNotification({
                            title:  "Info",
                            message: series.name + t("lists.alreadyAdded") + list.name,
                            type: "info",
                            insert: "top",
                            container: "top-right",
                            animationIn: ["animated", "fadeIn"],
                            animationOut: ["animated", "fadeOut"],
                            dismiss: {
                                duration: 4000,
                                onScreen: true
                            }
                        });
                    }
                });
        }
    };

    render() {
        if (!this.state.error) {
            const seriesLists = this.state.genreList.map(genre => {
                return(
                    <SeriesList key={ genre.id } childKey={true} name={ genre.name } source={ genre.seriesUri } section={"#explore"} addSeriesToListHandler={this.addSeriesToListHandler} userLists={this.state.userLists}/>
                )
            });

            return (
                <div className="alt-block" style={{background: 'white'}}>
                    <div className="main-block">
                        <div className="main-block-container">
                            <div id="explore">
                                <SeriesCarousel source="/series/featured"/>
                                { seriesLists }
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
};

export default compose(
    connect(mapStateToProps),
    withTranslation()
)(Explore);