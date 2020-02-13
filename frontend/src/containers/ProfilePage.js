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

class ProfilePage extends Component {

    state = {
        user: null,
        loading: true
    }

    componentDidMount = () => {
        let user_id = this.props.match.params.profile_id;

        Axios.get("/users/" + user_id)
                .then(res => {
                    this.setState({
                        user: res.data,
                        loading: false
                    })
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

        const user = this.state.user;

        const currUser = this.props.logged_user && this.props.logged_user.id === user.id;

        const recentlyWatched = [
            {
                "id": 1,
                "name": "Once Upon A Time",
                "posterUrl": "/49qD372jeHUTmdNMGJkjCFZdv9y.jpg",
                "followers": 13,
                "bannerUrl": "/49qD372jeHUTmdNMGJkjCFZdv9y.jpg"
              },
              {
                "id": 2,
                "name": "Cleverman",
                "posterUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg",
                "followers": 0,
                "bannerUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg"
              },
              {
                "id": 3,
                "name": "Cleverman",
                "posterUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg",
                "followers": 0,
                "bannerUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg"
              },
              {
                "id": 4,
                "name": "Cleverman",
                "posterUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg",
                "followers": 0,
                "bannerUrl": "/ndwQn6o3qTpkN8pHmOeVoToDS6J.jpg"
              }
        ];
        const followedSeries = [];

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
                                    {/* TODO: UPLOAD AVATAR */}
                                    <button href="#" className="avatar-upload-link" id="showUploadAvatarPopup">
                                        <img src={"/api/user/" + user.id + "/avatar"} alt="avatar" />
                                    </button>
                                </div>
                                <div className="profile-infos">
                                    <h1 className="name">
                                        {user.userName.toUpperCase()}
                                    </h1>
                                </div>

                                {/* TAB TITLES */}
                                <ul className="nav nav-tabs align-self-center">
                                    <li id="followedTab" role="presentation" className="tab-shows active">
                                        <a id="followedLink" href="#tab-shows" data-toggle="tab" aria-controls="tab-shows"
                                            aria-expanded="true">
                                            <div className="label">
                                                <Trans i18nKey="profile.followed"/>
                                            </div>
                                        </a>
                                    </li>
                                    {
                                        currUser && 
                                        <li id="listsTab" role="presentation" className="tab-information">
                                            <a id="listsLink" href="#tab-lists" data-toggle="tab" aria-controls="tab-lists" aria-expanded="true" >
                                                <div className="label">
                                                    <Trans i18nKey="profile.lists"/>
                                                </div>
                                            </a>
                                        </li>
                                    }
                                    {
                                        currUser && 
                                        <li id="statsTab" role="presentation" className="tab-information">
                                            <a id="statsLink" href="#tab-stats" data-toggle="tab" aria-controls="tab-stats" aria-expanded="true" >
                                                <div className="label">
                                                    <Trans i18nKey="profile.stats"/>
                                                </div>
                                            </a>
                                        </li>
                                    }
                                    {
                                        currUser && 
                                        <li id="informationTab" role="presentation" className="tab-information">
                                            <a id="informationLink" href="#tab-information" data-toggle="tab" aria-controls="tab-information"
                                                aria-expanded="true">
                                                <div className="label">
                                                    <Trans i18nKey="profile.information"/>
                                                </div>
                                            </a>
                                        </li>
                                    }
                                </ul>
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
                                                                <SeriesList source={recentlyWatched} />
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
                                                                /*const options = {
                                                                    headers: {'Content-Type': 'application/json'}
                                                                };

                                                                Axios.post(CONSTANTS.APIURL + "/users/login", JSON.stringify(values), options)
                                                                    .then((res) => {
                                                                        let token = res.headers.authorization;
                                                                        let user = res.data;

                                                                        this.props.loginUser(token, user);
                                                                        this.props.history.push("/");
                                                                    })
                                                                    .catch((err) => {
                                                                        if(err.response.status === 401)
                                                                            actions.setFieldError("general", "MENSAJE USER O PASS INCORRECTA!");
                                                                        else
                                                                            alert("Error: " + err.response.status);
                                                                    })
                                                                    .finally(() => actions.setSubmitting(false));*/
                                                            }}
                                                            >
                                                            {formik => (
                                                                <form onSubmit={formik.handleSubmit}>

                                                                    <div className="row form-group">
                                                                        <label className="col-sm-4 control-label" path="username">
                                                                            <Trans i18nKey="register.username" />
                                                                        </label>
                                                                        <div className="col-sm-6">
                                                                            <input {...formik.getFieldProps('username')} path="username" type="text" minlength="6" maxlength="32" className="form-control" name="username" placeholder="JohnDoe" />
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
}

export default connect(mapStateToProps)(ProfilePage);