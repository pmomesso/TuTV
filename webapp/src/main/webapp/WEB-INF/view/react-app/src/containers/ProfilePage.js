import React, { Component } from 'react';
import { Trans } from 'react-i18next';
import { NavLink } from 'react-router-dom';
import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';
import SeriesList from '../components/SeriesList';
import { connect } from 'react-redux';
import Axios from 'axios';
import { Formik } from 'formik';
import * as Yup from 'yup';
import $ from "jquery";
import Chart from "chart.js";

class ProfilePage extends Component {

    state = {
        user: null,
        avatar: null,
        recentlyWatched: null,
        following: null,
        stats: null,
        lists: null,
        loading: true
    };

    componentDidMount = () => {
        let user_id = this.props.match.params.profile_id;
        var that = this;
        Axios.all([
            Axios.get("/users/" + user_id),
            Axios.get("/users/" + user_id + "/avatar"),
            Axios.get("/users/" + user_id + "/recentlyWatched"),
            Axios.get("/users/" + user_id + "/following"),
            Axios.get("/users/" + user_id + "/stats"),
            Axios.get("/users/" + user_id + "/lists")
        ]).then(Axios.spread(function(userData, avatarData, recentlyWatchedData, followingData, statsData, listsData) {
            that.setState({
                user: userData.data,
                avatar: avatarData.data,
                recentlyWatched: recentlyWatchedData.data,
                following: followingData.data,
                stats: statsData.data,
                lists: listsData.data,
                loading: false
            });

            var labels = [];
            var values = [];
            $.each(statsData.data.stats, function (index, stat) {
                labels.push(stat.genre.name);
                values.push(stat.stat);
            });
            var ctx = document.getElementById('genresChart');
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
        }));
    };

    toggleUploadAvatar = () => {
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
            console.log(e.target.result);
            console.log(e.target.result.split("base64,")[1]);
            const options = {
                headers: {'Content-Type': 'text/plain'}
            };
            // let data = { "avatarBase64": e.target.result.split("base64,")[1] };
            // let data = { "avatarBase64": e.target.result };
            Axios.put("/users/" + that.props.logged_user.id + "/avatar", e.target.result, options)
                .then((res) => {
                    console.log(res);
                })
                .catch((err) => {
                    console.log(err);
                    /* TODO SI CADUCO LA SESION? */
                    //alert("Error: " + err.response.status);
                });
        });
        FR.readAsDataURL( $(fileInput)[0].files[0] );
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

        const user = this.state.user;
        const currUser = this.props.logged_user && this.props.logged_user.id === user.id;
        const avatar = this.state.avatar;
        const recentlyWatched = this.state.recentlyWatched;
        const stats = this.state.stats;
        const lists = this.state.lists;
        const followedSeries = this.state.following;

        const listsElement = lists.map(list => {
            return(
                <div key={list.id} className="profile-shows">
                    <div>
                        <div className="overflow-hidden">
                            <h2 className="small float-left">{list.name}</h2>
                            <button className="show-link float-left icon-margin" data-toggle="modal" data-target={"#modifyList" + list.id}>
                                <span>MODIFY</span>
                            </button>
                            {/* <form action="/removeList?id=${list.id}&userId=${userProfile.id}"
                                                                        method="post" class="icon-margin float-left" onsubmit="confirmAction(event,'<spring:message code="profile.sureRemove" arguments="${list.name}"/>')">
                                 <button type="submit" class="heart no-padding" style="font-family: FontAwesome,serif; font-style: normal">&#xf1f8</button>
                               </form> */}
                        </div>
                        <SeriesList source={list.series} />
                    </div>
                </div>
            );
        });
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
                                    <a href="#" className="avatar-upload-link" id="showUploadAvatarPopup" onClick={this.toggleUploadAvatar}>
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
                                    </a>
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

                                            {(currUser && recentlyWatched.length) &&
                                                <div id="recently-watched-shows">
                                                    <h2 className="small">
                                                        <Trans i18nKey="profile.recently" />
                                                    </h2>
                                                    <section>
                                                        <SeriesList source={recentlyWatched} />
                                                    </section>
                                                </div>
                                            }
                                            <div id="all-shows">
                                                <h2 className="small">
                                                    <Trans i18nKey="profile.all" />
                                                </h2>
                                                <section>
                                                    {
                                                        (followedSeries.length) ?
                                                            (<section>
                                                                <SeriesList source={followedSeries} />
                                                            </section>)
                                                            :
                                                            (currUser) ?
                                                                (<div className="container h-100">
                                                                    <div className="row justify-content-center h-100">
                                                                        <div className="col-lg-8 col-sm-12 align-self-center">
                                                                            <div className="text-center m-4">
                                                                                <h4>
                                                                                    <Trans i18nKey="watchlist.discover" />
                                                                                </h4>
                                                                            </div>
                                                                            <div className="text-center m-4">
                                                                                <NavLink className="tutv-button m-4" id="menu_home" to="/">
                                                                                    <Trans i18nKey="watchlist.explore" />
                                                                                </NavLink>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>)
                                                                :
                                                                (<div className="container h-100">
                                                                    <div className="row justify-content-center h-100">
                                                                        <div className="col-lg-8 col-sm-12 align-self-center">
                                                                            <div className="text-center m-4">
                                                                                <h4>
                                                                                    <Trans i18nKey="profile.userNoShows" values={{ user: user.userName.toUpperCase() }} />
                                                                                </h4>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>)
                                                    }
                                                </section>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="tab-lists" className="tab-pane" role="tabpanel">
                                        {(listsElement.length) ?
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
                                                        (stats) ?
                                                            (<div className="mt-lg-5 mt-sm-0"><canvas id="genresChart"></canvas></div>)
                                                            :
                                                            (<div className="container h-100">
                                                                <div className="row justify-content-center h-100">
                                                                    <div className="col-lg-8 col-sm-12 align-self-center">
                                                                        <div className="text-center m-4">
                                                                            <h4><Trans i18nKey="profile.noStats"/></h4>
                                                                            <h4><Trans i18nKey="watchlist.discover"/></h4>
                                                                        </div>
                                                                        <div class="text-center m-4">
                                                                            <button class="tutv-button m-4" onclick="window.location.href='/'"><Trans i18nKey="watchlist.explore"/></button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>)
                                                    }
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="tab-information" className="tab-pane" role="tabpanel">
                                        <section id="basic-settings" className="container">
                                            <div className="row text-center">
                                                <div className="col my-auto self-align-center">
                                                    <div className="other-infos infos-zone">
                                                        <Formik
                                                            initialValues={{ username: user.userName, mail: user.mail }}
                                                            validationSchema={Yup.object({
                                                                mail: Yup.string()
                                                                    .email('Invalid email address')
                                                                    .required('Required'),
                                                                username: Yup.string()
                                                                    .max(20, 'Must be 20 characters or less')
                                                                    .required('Required'),
                                                            })}
                                                            onSubmit={(values, actions) => {
                                                                const options = {
                                                                    headers: {'Content-Type': 'application/json'}
                                                                };
                                                                let data = { "userName": values.username };
                                                                Axios.put("/users/" + this.props.logged_user.id, JSON.stringify(data), options)
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
                                                                    })
                                                                    .catch((err) => {
                                                                        /* TODO SI CADUCO LA SESION? */
                                                                        //alert("Error: " + err.response.status);
                                                                    })
                                                                    .finally(() => actions.setSubmitting(false));
                                                            }}
                                                            >
                                                            {formik => (
                                                                <form onSubmit={formik.handleSubmit}>

                                                                    <div className="row form-group">
                                                                        <label className="col-sm-4 control-label" path="username">
                                                                            <Trans i18nKey="register.username" />
                                                                        </label>
                                                                        <div className="col-sm-6">
                                                                            <input {...formik.getFieldProps('username')} path="username" type="text" minLength="6" maxLength="32" className="form-control" name="username" placeholder="JohnDoe" />
                                                                            {formik.touched.username && formik.errors.username ? (
                                                                                    <span className="error m-3 w-100">{formik.errors.username}</span>
                                                                                ) : null}
                                                                        </div>
                                                                    </div>
                                                                    <div className="row form-group">
                                                                        <label className="col-sm-4 control-label">
                                                                            <Trans i18nKey="register.mail" />
                                                                        </label>
                                                                        <div className="col-sm-6">
                                                                            <input {...formik.getFieldProps('mail')} type="email" className="form-control" name="mail" disabled />
                                                                            {formik.touched.mail && formik.errors.mail ? (
                                                                                    <span className="error m-3 w-100">{formik.errors.mail}</span>
                                                                                ) : null}
                                                                        </div>
                                                                    </div>
                                                                    <div className="row text-center justify-content-center">
                                                                        <div className="col align-self-center">
                                                                            <div className="text-center m-4">
                                                                                <button type="submit" className="tutv-button m-4" >
                                                                                    <Trans i18nKey="profile.save" />
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
                                </div>
                            </div>
                        </div>
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

const mapDispatchToProps = (dispatch) => {
    return {
        updateUserName: (user) => { dispatch({ type: "UPDATE_USERNAME", payload: { "user": user, "updateLocalStorage": true } }) }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(ProfilePage);