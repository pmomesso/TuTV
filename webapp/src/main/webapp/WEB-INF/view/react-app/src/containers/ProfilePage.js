import React, {PureComponent} from 'react';
import {Trans, withTranslation} from 'react-i18next';
import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';
import SeriesList from '../components/SeriesList';
import { connect } from 'react-redux';
import { compose } from 'recompose';
import Axios from 'axios';
import { Formik } from 'formik';
import * as Yup from 'yup';
import $ from "jquery";
import Chart from "chart.js";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {confirmAlert} from "react-confirm-alert";
import ErrorPage from "./ErrorPage";

class ProfilePage extends PureComponent {

    state = {
        user: null,
        avatar: null,
        recentlyWatched: null,
        followingChanged: null,
        stats: null,
        lists: null,
        loading: true,
        error: false
    };

    componentDidMount = () => {
        this.getData();
    };

    componentDidUpdate = (prevProps) => {
        if (this.props.match.params.profile_id !== prevProps.match.params.profile_id ) {
            this.getData();
        }
    };

    getData = () => {
        let user_id = this.props.match.params.profile_id;
        var that = this;

        if (this.props.logged_user && this.props.logged_user.id.toString() === user_id) {
            Axios.all([
                Axios.get("/users/" + user_id),
                Axios.get("/users/" + user_id + "/avatar"),
                Axios.get("/users/" + user_id + "/recentlyWatched"),
                Axios.get("/users/" + user_id + "/stats"),
                Axios.get("/users/" + user_id + "/lists")
            ]).then(Axios.spread(function(userData, avatarData, recentlyWatchedData, statsData, listsData) {
                that.setState({
                    user: userData.data,
                    avatar: avatarData.data.avatarBase64,
                    recentlyWatched: recentlyWatchedData.data,
                    followingChanged: true,
                    stats: statsData.data.stats,
                    lists: listsData.data,
                    loading: false
                });

                if (that.state.stats.length !== 0) {
                    var labels = [];
                    var values = [];
                    $.each(statsData.data.stats, function (index, stat) {
                        labels.push(stat.genre.name);
                        values.push(stat.stat);
                    });
                    $("#genresChart").remove();
                    $("#canvasContainer").append("<canvas id='genresChart'/>");
                    var ctx = document.getElementById('genresChart');
                    that.createChart(ctx, labels, values);
                }
            })).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
        } else {
            Axios.all([
                Axios.get("/users/" + user_id),
                Axios.get("/users/" + user_id + "/avatar"),
                Axios.get("/users/" + user_id + "/recentlyWatched")
            ]).then(Axios.spread(function(userData, avatarData, recentlyWatchedData) {
                that.setState({
                    user: userData.data,
                    avatar: avatarData.data.avatarBase64,
                    recentlyWatched: recentlyWatchedData.data,
                    followingChanged: true,
                    loading: false
                });
            })).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
        }
    };

    toggleUploadAvatar = () => {
        if(!this.props.logged_user || this.state.user.id !== this.props.logged_user.id)
            return;

        $("#uploadAvatarPopup").toggle();
    };

    setActiveFollowedLink = () => {
        $('#followedTab').addClass('active');
        $('#tab-shows').addClass('active');
        $('#listsTab').removeClass('active');
        $('#tab-lists').removeClass('active');
        $('#informationTab').removeClass('active');
        $('#tab-information').removeClass('active');
        $('#statsTab').removeClass('active');
        $('#tab-stats').removeClass('active');
    };

    setActiveListsLink = () => {
        $('#listsTab').addClass('active');
        $('#tab-lists').addClass('active');
        $('#followedTab').removeClass('active');
        $('#tab-shows').removeClass('active');
        $('#statsTab').removeClass('active');
        $('#tab-stats').removeClass('active');
        $('#informationTab').removeClass('active');
        $('#tab-information').removeClass('active');
    };

    setActiveStatsLink = () => {
        $('#statsTab').addClass('active');
        $('#tab-stats').addClass('active');
        $('#followedTab').removeClass('active');
        $('#tab-shows').removeClass('active');
        $('#listsTab').removeClass('active');
        $('#tab-lists').removeClass('active');
        $('#informationTab').removeClass('active');
        $('#tab-information').removeClass('active');
    };

    setActiveInformationLink = () => {
        $('#informationTab').addClass('active');
        $('#tab-information').addClass('active');
        $('#followedTab').removeClass('active');
        $('#tab-shows').removeClass('active');
        $('#listsTab').removeClass('active');
        $('#tab-lists').removeClass('active');
        $('#statsTab').removeClass('active');
        $('#tab-stats').removeClass('active');
    };

    readAvatar = () => {

        var wrongTypeDiv = $("#wrongFileTypeError");
        $(wrongTypeDiv).hide();

        var maxSizeErrorDiv = $("#avatarMaxSizeError");
        $(maxSizeErrorDiv).hide();

        var fileInput = $("#avatarFileInput");
        var value = $(fileInput).val();
        var file = value.toLowerCase();
        var supportedExtensions = ["jpg","jpeg","png"];
        var extension = file.substring(file.lastIndexOf('.') + 1);
        if (supportedExtensions.indexOf(extension) === -1){
            $(wrongTypeDiv).show();
            return;
        }

        var maxSize = fileInput.data("max-size");
        if (fileInput.get(0).files.length) {
            var fileSize = fileInput.get(0).files[0].size; // in bytes
            if (fileSize > maxSize) {
                $(maxSizeErrorDiv).show();
                return;
            }
        } else {
            return;
        }

        var that = this;
        var FR = new FileReader();
        FR.addEventListener("load", function(e) {
            const options = {
                headers: {'Content-Type': e.target.result.split("base64,")[0].replace("data:", "").replace(";", "")}
            };
            Axios.put("/users/" + that.props.logged_user.id + "/avatar", e.target.result.split("base64,")[1], options)
                .then((res) => {
                    that.setState({
                        avatar: e.target.result.split("base64,")[1]
                    });
                    $("#uploadAvatarPopup").toggle();
                }).catch((err) => {
                    const error_status = "error." + err.response.status + "status";
                    const error_body = "error." + err.response.status + "body";
                    this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
                });
        });
        FR.readAsDataURL( $(fileInput)[0].files[0] );
    };

    onSeriesFollowClickedHandler = (event) => {
        let user_id = this.props.match.params.profile_id;
        var that = this;

        Axios.get("/users/" + user_id + "/stats")
            .then(res => {
                that.setState({
                    stats: res.data.stats,
                    followingChanged: !this.state.followingChanged
                });

                if (that.state.stats.length !== 0) {
                    var labels = [];
                    var values = [];
                    $.each(res.data.stats, function (index, stat) {
                        labels.push(stat.genre.name);
                        values.push(stat.stat);
                    });
                    $("#genresChart").remove();
                    $("#canvasContainer").append("<canvas id='genresChart'/>");
                    var ctx = document.getElementById('genresChart');
                    that.createChart(ctx, labels, values);
                }
            }).catch((err) => {
                const error_status = "error." + err.response.status + "status";
                const error_body = "error." + err.response.status + "body";
                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
            });
    };

    createChart = (ctx, labels, values) => {
        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: labels,
                datasets: [{
                    data: values,
                    backgroundColor: [
                        '#3cb44b', '#469990', '#aaffc3', '#42d4f4', '#4363d8',
                        '#000075', '#911eb4', '#f032e6', '#e6beff', '#800000',
                        '#e6194b', '#f58231', '#ffd8b1', '#ffe119', '#bfef45'
                    ]
                }]
            },
            options: {
                maintainAspectRatio: false,
                legend: {
                    display: true,
                    position: 'bottom',
                    labels: {
                        padding: 20
                    }
                }
            }
        });
    };

    removeList = (list_id, index) => {
        const { t } = this.props;

        confirmAlert({
            title: t("series.deleteConfirmTitle"),
            message: t("profile.lists.deleteConfirm"),
            buttons: [
                {
                    label: t("series.yes"),
                    onClick: () => {
                        Axios.delete("/users/" + this.props.match.params.profile_id + "/lists/" + list_id)
                            .then(() => {
                                let newLists = [ ...this.state.lists];
                                newLists.splice(index, 1);

                                this.setState({
                                    lists: newLists
                                });
                            }).catch((err) => {
                                const error_status = "error." + err.response.status + "status";
                                const error_body = "error." + err.response.status + "body";
                                this.setState({error:true, error_status: error_status, error_body: error_body, loading: false});
                            });
                    }
                },
                {
                    label: t("series.no")
                }
            ]
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

        if (!this.state.error) {
            const user = this.state.user;
            const currUser = this.props.logged_user && this.props.logged_user.id === user.id;
            const avatar = this.state.avatar;
            const recentlyWatched = this.state.recentlyWatched;
            const stats = this.state.stats;
            const lists = this.state.lists;

            var listsElement = null;
            if (currUser) {
                listsElement = lists.map((list, index) => {
                    return(
                        <div key={list.id} className="profile-shows">
                            <div>
                                <div className="overflow-hidden">
                                    <h2 className="small float-left">{list.name}</h2>
                                    <div className="icon-margin float-left">
                                        <button onClick={() => this.removeList(list.id, index)} className="heart no-padding"><FontAwesomeIcon icon={faTrash} /></button>
                                    </div>
                                </div>
                                <SeriesList isLists={true} list={list} source={list.seriesUri} section={"#profile"} onSeriesFollowClickedHandler={this.onSeriesFollowClickedHandler} />
                            </div>
                        </div>
                    );
                });
            }

            return (
                <div>
                    <div className="main-block-container">
                        <div id="profile">
                            <div className="images">
                                <div className="images-inner"></div>
                                <img src={require('../img/background.jpg')} alt="Background" />
                            </div>
                            <div className="profile-nav">
                                <div className="row wrapper">
                                    <div className="avatar">
                                    <span className="avatar-upload-link" id="showUploadAvatarPopup" onClick={this.toggleUploadAvatar}>
                                        {
                                            (avatar) ?
                                                (<img src={`data:image/jpeg;base64,${this.state.avatar}`} alt="avatar"/>)
                                                :
                                                (<img src={"https://d36rlb2fgh8cjd.cloudfront.net/default-images/default-user-q80.png"} alt="avatar"/>)
                                        }{
                                        currUser &&
                                        <span className="avatar-upload-label">
                                                <Trans i18nKey="profile.edit"/>
                                            </span>
                                    }
                                    </span>
                                    </div>
                                    <div className="profile-infos">
                                        <h1 className="name">
                                            {user.userName.toUpperCase()}
                                        </h1>
                                    </div>

                                    {/* TAB TITLES */}
                                    <ul className="nav nav-tabs align-self-center">
                                        <li id="followedTab" role="presentation" className="tab-shows active">
                                            <a id="followedLink" href="#tab-shows" data-toggle="tab" aria-controls="tab-shows" aria-expanded="true" onClick={this.setActiveFollowedLink}>
                                                <div className="label">
                                                    <Trans i18nKey="profile.followed"/>
                                                </div>
                                            </a>
                                        </li>
                                        {
                                            currUser &&
                                            <li id="listsTab" role="presentation" className="tab-information">
                                                <a id="listsLink" href="#tab-lists" data-toggle="tab" aria-controls="tab-lists" aria-expanded="true" onClick={this.setActiveListsLink}>
                                                    <div className="label">
                                                        <Trans i18nKey="profile.lists"/>
                                                    </div>
                                                </a>
                                            </li>
                                        }
                                        {
                                            currUser &&
                                            <li id="statsTab" role="presentation" className="tab-information">
                                                <a id="statsLink" href="#tab-stats" data-toggle="tab" aria-controls="tab-stats" aria-expanded="true" onClick={this.setActiveStatsLink}>
                                                    <div className="label">
                                                        <Trans i18nKey="profile.stats"/>
                                                    </div>
                                                </a>
                                            </li>
                                        }
                                        {
                                            currUser &&
                                            <li id="informationTab" role="presentation" className="tab-information">
                                                <a id="informationLink" href="#tab-information" data-toggle="tab" aria-controls="tab-information" aria-expanded="true" onClick={this.setActiveInformationLink}>
                                                    <div className="label">
                                                        <Trans i18nKey="profile.information"/>
                                                    </div>
                                                </a>
                                            </li>
                                        }
                                    </ul>
                                </div>
                            </div>
                            <div className="popover fade bottom in" role="tooltip" id="uploadAvatarPopup" style={{top: '250px', left: '-41px', display: 'none'}}>
                                <div className="arrow" style={{left: '50%'}}></div>
                                <h3 className="popover-title">
                                    <Trans i18nKey="profile.upload"/>
                                </h3>
                                <div className="popover-content">
                                    <h3 className="popover-title" id="avatarMaxSizeError" style={{display: 'none'}}>
                                        <span style={{color: 'red'}}><Trans i18nKey="profile.avatarMaxSize"/> 2MB</span>
                                    </h3>
                                    <h3 className="popover-title" id="wrongFileTypeError" style={{display: 'none'}}>
                                        <span style={{color: 'red'}}><Trans i18nKey="profile.wrongFileType"/></span>
                                    </h3>
                                    <input id="avatarFileInput" type="file" name="avatar" accept=".jpg,.jpeg,.png" data-max-size={2097152} onChange={this.readAvatar}/>
                                </div>
                            </div>
                            <div className="profile-content">
                                <div className="wrapper">
                                    <div className="tab-content">
                                        <div id="tab-shows" className="tab-pane active" role="tabpanel">
                                            <div className="profile-shows">

                                                {(recentlyWatched.length) ?
                                                    (<div id="recently-watched-shows">
                                                        <h2 className="small">
                                                            <Trans i18nKey="profile.recently" />
                                                        </h2>
                                                        <SeriesList key={recentlyWatched} source={recentlyWatched} onSeriesFollowClickedHandler={this.onSeriesFollowClickedHandler}/>
                                                    </div>):(<div></div>)
                                                }
                                                <div id="all-shows">
                                                    <h2 className="small">
                                                        <Trans i18nKey="profile.all" />
                                                    </h2>
                                                    <SeriesList isLists={false} user={user} currUser={currUser} key={this.state.followingChanged} source={"/users/" + user.id + "/following"} section={"#profile"} onSeriesFollowClickedHandler={this.onSeriesFollowClickedHandler}/>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="tab-lists" className="tab-pane" role="tabpanel">
                                            {(currUser && listsElement.length) ?
                                                listsElement
                                                :
                                                (<div id="all-shows">
                                                    <h2 className="small"> </h2>
                                                    <section>
                                                        <div className="container h-100">
                                                            <div className="row justify-content-center h-100">
                                                                <div className="col-lg-8 col-sm-12 align-self-center">
                                                                    <div className="text-center m-4">
                                                                        <h4>
                                                                            <Trans i18nKey="profile.noLists" />
                                                                        </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>)
                                            }
                                        </div>
                                        <div id="tab-stats" className="tab-pane" role="tabpanel">
                                            <div className="profile-shows">
                                                <div>
                                                    <h2 className="small"><Trans i18nKey="profile.favoriteGenres"/></h2>
                                                    <div className="row justify-content-center">
                                                        {
                                                            (currUser && stats.length !== 0) ?
                                                                (<div id="canvasContainer" className="mt-lg-5 mt-sm-0"><canvas id="genresChart"/></div>)
                                                                :
                                                                (<div className="container h-100">
                                                                    <div className="row justify-content-center h-100">
                                                                        <div className="col-lg-8 col-sm-12 align-self-center">
                                                                            <div className="text-center m-4">
                                                                                <h4><Trans i18nKey="profile.noStats"/></h4>
                                                                                <h4><Trans i18nKey="watchlist.discover"/></h4>
                                                                            </div>
                                                                            <div className="text-center m-4">
                                                                                <button className="tutv-button m-4" onClick={event =>  window.location.href='/'}><Trans i18nKey="watchlist.explore"/></button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>)
                                                        }
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        {
                                            currUser &&
                                            <div id="tab-information" className="tab-pane" role="tabpanel">
                                                <section id="basic-settings" className="container">
                                                    <div className="row text-center">
                                                        <div className="col my-auto self-align-center">
                                                            <div className="other-infos infos-zone">
                                                                <Formik
                                                                    initialValues={{
                                                                        username: user.userName,
                                                                        mail: user.mail
                                                                    }}
                                                                    validationSchema={Yup.object({
                                                                        mail: Yup.string()
                                                                            .email(<Trans
                                                                                i18nKey="register.invalidEmail"/>)
                                                                            .required('Required'),
                                                                        username: Yup.string()
                                                                            .max(20, <Trans
                                                                                i18nKey="register.passwordTooLong"/>)
                                                                            .required('Required'),
                                                                    })}
                                                                    onSubmit={(values, actions) => {
                                                                        const options = {
                                                                            headers: {'Content-Type': 'application/json'}
                                                                        };
                                                                        let data = {"userName": values.username};
                                                                        Axios.put("/users/" + this.props.logged_user.id + "/username", JSON.stringify(data), options)
                                                                            .then((res) => {
                                                                                let newUser = {
                                                                                    ...this.state.user,
                                                                                    userName: values.username
                                                                                };

                                                                                this.setState({
                                                                                    user: newUser
                                                                                });

                                                                                let user = res.data;
                                                                                this.props.updateUserName(user);
                                                                            }).catch((err) => {
                                                                            const error_status = "error." + err.response.status + "status";
                                                                            const error_body = "error." + err.response.status + "body";
                                                                            this.setState({
                                                                                error: true,
                                                                                error_status: error_status,
                                                                                error_body: error_body,
                                                                                loading: false
                                                                            });
                                                                        }).finally(() => actions.setSubmitting(false));
                                                                    }}
                                                                >
                                                                    {formik => (
                                                                        <form onSubmit={formik.handleSubmit}>

                                                                            <div className="row form-group">
                                                                                <label
                                                                                    className="col-sm-4 control-label"
                                                                                    path="username">
                                                                                    <Trans i18nKey="register.username"/>
                                                                                </label>
                                                                                <div className="col-sm-6">
                                                                                    <input {...formik.getFieldProps('username')}
                                                                                           path="username" type="text"
                                                                                           minLength="6" maxLength="32"
                                                                                           className="form-control"
                                                                                           name="username"
                                                                                           placeholder="JohnDoe"/>
                                                                                    {formik.touched.username && formik.errors.username ? (
                                                                                        <span
                                                                                            className="error m-3 w-100">{formik.errors.username}</span>
                                                                                    ) : null}
                                                                                </div>
                                                                            </div>
                                                                            <div className="row form-group">
                                                                                <label
                                                                                    className="col-sm-4 control-label">
                                                                                    <Trans i18nKey="register.mail"/>
                                                                                </label>
                                                                                <div className="col-sm-6">
                                                                                    <input {...formik.getFieldProps('mail')}
                                                                                           type="email"
                                                                                           className="form-control"
                                                                                           name="mail" disabled/>
                                                                                    {formik.touched.mail && formik.errors.mail ? (
                                                                                        <span
                                                                                            className="error m-3 w-100">{formik.errors.mail}</span>
                                                                                    ) : null}
                                                                                </div>
                                                                            </div>
                                                                            <div
                                                                                className="row text-center justify-content-center">
                                                                                <div className="col align-self-center">
                                                                                    <div className="text-center m-4">
                                                                                        <button type="submit"
                                                                                                className="tutv-button m-4">
                                                                                            <Trans
                                                                                                i18nKey="profile.save"/>
                                                                                        </button>
                                                                                    </div>
                                                                                </div>
                                                                            </div>

                                                                        </form>
                                                                    )}
                                                                </Formik>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </section>
                                            </div>
                                        }
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
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

const mapDispatchToProps = (dispatch) => {
    return {
        updateUserName: (user) => { dispatch({ type: "UPDATE_USERNAME", payload: { "user": user, "updateLocalStorage": true } }) }
    }
};

export default compose(
    connect(mapStateToProps, mapDispatchToProps),
    withTranslation()
)(ProfilePage);