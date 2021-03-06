import React, { PureComponent } from 'react';
import { withTranslation } from 'react-i18next';
import { Link, withRouter } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faPlusCircle, faMinusCircle, faEllipsisH, faList, faTrash} from '@fortawesome/free-solid-svg-icons';
import { connect } from 'react-redux';
import Axios from 'axios';
import { store } from 'react-notifications-component';

import { compose } from "recompose";

import { ContextMenu, MenuItem, ContextMenuTrigger, SubMenu } from "react-contextmenu";

class TvSeriesPoster extends PureComponent {
    state = {
        series: this.props.series
    };

    getRandomInt = (min, max) => {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    onSeriesAddToListClicked = (event, data) => {
        this.props.addSeriesToListHandler(data.list, this.state.series);
    };

    onSeriesFollowClicked = (event) => {
        const { t, logged_user } = this.props;

        if (logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        let newValue = !this.state.series.loggedInUserFollows;

        let promise;
        if (newValue) {
            promise = Axios.post("/users/" + logged_user.id + "/following", JSON.stringify({ "seriesId": this.state.series.id }));
        } else {
            promise = Axios.delete("/users/" + logged_user.id + "/following/" + this.state.series.id);
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

                if (this.props.onSeriesFollowClickedHandler !== undefined) {
                    this.props.onSeriesFollowClickedHandler(event, this.state.series.id);
                }

                var message;
                if (newValue) {
                    message = this.state.series.name + t("series.followed");
                } else {
                    message = this.state.series.name + t("series.unfollowed");
                }

                store.addNotification({
                    title: t("lists.success"),
                    message: message,
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
            })
            .catch((err) => {
                //alert("Error: " + err.response.status);
            });
    };

    onRemoveFromListClicked = () => {
        const { t, logged_user, list } = this.props;

        if (logged_user === null) {
            this.props.history.push("/login" + this.props.location.pathname);
            return;
        }

        Axios.delete("/users/" + logged_user.id + "/lists/" + list.id + "/series/" + this.state.series.id)
            .then(res => {
                if (this.props.onRemoveFromListClickedHandler !== undefined) {
                    this.props.onRemoveFromListClickedHandler();
                }

                store.addNotification({
                    title: t("lists.success"),
                    message: this.state.series.name + t("lists.deletedFrom") + list.name,
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
            })
    };

    contextTrigger = null;
    toggleMenu = e => {
        if(this.contextTrigger) {
            this.contextTrigger.handleContextClick(e);
        }
    };

    render() {
        const { series } = this.state;

        const { t, userLists } = this.props;

        const contextMenuId = "seriesContext_" + series.id + "_" + this.getRandomInt(0, 99999);

        const userListsMenu = !userLists ? null : userLists.map(userList => {
            return (
                <MenuItem key={userList.id} data={{list: userList}} onClick={this.onSeriesAddToListClicked}>{ userList.name }</MenuItem>
            )
        });

        return (
            <li className="float-left">
                <ContextMenuTrigger id={ contextMenuId } ref={c => this.contextTrigger = c}>
                    <div className="image-crop">
                        <Link to={"/series/" + series.id}>
                            <img src={"https://image.tmdb.org/t/p/original" + series.posterUrl} alt={series.name} />
                        </Link>
                        {this.props.logged_user !== null &&
                            <button className="check-follow">
                                <FontAwesomeIcon icon={faEllipsisH} onClick={this.toggleMenu} className="icon" />
                            </button>
                        }
                    </div>
                </ContextMenuTrigger>

                <div className="show-details poster-details">
                    <h2>{series.name}</h2>
                    <span className="secondary-link">
                        {t("index.followers", { count: series.followers })}
                    </span>
                </div>
                <ContextMenu id={ contextMenuId }>
                    <MenuItem onClick={this.onSeriesFollowClicked}>
                        <FontAwesomeIcon 
                            icon={!this.state.series.loggedInUserFollows ? faPlusCircle : faMinusCircle} 
                            className="icon" />
                        &nbsp;
                        { t(!this.state.series.loggedInUserFollows ? "series.follow" : "series.unfollow") }
                    </MenuItem>
                    { userListsMenu &&
                        (<div>
                            <MenuItem divider />
                            <SubMenu title={ t("series.addToList") }>
                                { (userListsMenu.length > 0) &&
                                    (
                                        <div>
                                            { userListsMenu }
                                            <MenuItem divider />
                                        </div>
                                    )
                                }
                                
                                <MenuItem onClick={this.onSeriesAddToListClicked}>
                                    <FontAwesomeIcon icon={faList} />
                                    &nbsp;
                                    { t("series.newList") }
                                </MenuItem>
                            </SubMenu>
                        </div>)
                    }
                    {
                        this.props.isLists &&
                        <MenuItem onClick={this.onRemoveFromListClicked}>
                            <FontAwesomeIcon
                                icon={faTrash}
                                className="icon" />
                            &nbsp;
                            { t("lists.deleteFrom") }
                        </MenuItem>
                    }
                </ContextMenu>
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
)(withRouter(TvSeriesPoster));