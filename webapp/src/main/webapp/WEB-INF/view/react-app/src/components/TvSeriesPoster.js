import React, { PureComponent } from 'react';
import { withTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlusCircle, faMinusCircle } from '@fortawesome/free-solid-svg-icons';
import { connect } from 'react-redux';
import Axios from 'axios';
import { store } from 'react-notifications-component';

import { compose } from "recompose";

class TvSeriesPoster extends PureComponent {
    state = {
        series: this.props.series
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
                };
        
                this.setState({
                    series: newSeries
                });

                store.addNotification({
                    title: "Cambio estado de seguimiento",
                    type: "info",
                    insert: "top",
                    container: "top-right",
                    animationIn: ["animated", "fadeIn"],
                    animationOut: ["animated", "fadeOut"],
                    dismiss: {
                      duration: 4000,
                      pauseOnHover: true,
                      showIcon: true,
                    }
                });
            })
            .catch((err) => {
                /* TODO SI CADUCO LA SESION? */
                //alert("Error: " + err.response.status);
            });
    };

    render() {
        const { series } = this.state;

        const { t } = this.props;
        
        return (
            <li className="float-left">
                <div className="image-crop">
                    <Link to={ "/series/" + series.id }>
                        <img  src={"https://image.tmdb.org/t/p/original" + series.posterUrl} alt={series.name}/>
                    </Link>
                    <div className="overlay">
                        <span className="zoom-btn overlay-btn">
                            {this.props.logged_user !== null &&
                            <button className="check-follow">
                                <FontAwesomeIcon icon={ !this.state.series.loggedInUserFollows ? faPlusCircle : faMinusCircle } onClick={this.onSeriesFollowClicked} className="icon" />
                            </button>
                            }
                        </span>
                    </div>
                </div>
                <div className="show-details poster-details">
                    <h2>{ series.name }</h2>
                    <span className="secondary-link">
                        { t("index.followers", { count: series.followers }) }
                    </span>
                </div>
            </li>
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
)(TvSeriesPoster);