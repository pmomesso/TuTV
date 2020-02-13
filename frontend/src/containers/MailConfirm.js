import React, { Component } from 'react';
import { Trans } from 'react-i18next';
import { NavLink } from 'react-router-dom';
import Axios from 'axios';

import { Spinner } from 'react-activity';
import 'react-activity/dist/react-activity.css';
import { connect } from 'react-redux';

class MailConfirm extends Component {

    state = {
        "loading": true,
        "error": false
    }

    componentDidMount = () => {
        let token = this.props.match.params.token;
    
        const data = { "confirmationKey": token };

        Axios.post("/users/mailconfirm", JSON.stringify(data))
            .then((res) => {
                let token = res.headers.authorization;
                let user = res.data;

                this.props.loginUser(token, user);
                this.setState({ loading: false });
            })
            .catch((err) => {
                this.setState({ error: true })
            })
            .finally(() => this.setState({ loading: false }));

      }

    render() {

        if(this.state.loading)
            return (
                <div className="h-100">

                    <div className="full-overlay"></div>

                    <div className="container h-100">
                        <div className="row justify-content-center h-100">
                            <div className="col-lg-8 col-sm-11 align-self-center white-background">
                                <div className="w-100 text-center">
                                    <img src={require('../img/shortcuticon.png')} alt="TUTV" />
                                    <span className="title-page">
                                        TuTv
                                    </span>
                                </div>

                                <br/>

                                <div class="text-center">
                                    <div style={{width: "100%",height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
                                        <Spinner color="#727981" size={32} speed={1} animating={true} />
                                    </div>
                                    <Trans i18nKey="mailconfirm.loading" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );

            if(this.state.error)
                return (
                    <div className="h-100">

                        <div className="full-overlay"></div>

                        <div className="container h-100">
                            <div className="row justify-content-center h-100">
                                <div className="col-lg-8 col-sm-11 align-self-center white-background">
                                    <div className="w-100 text-center">
                                        <img src={require('../img/shortcuticon.png')} alt="TUTV" />
                                        <span className="title-page">
                                            TuTv
                                        </span>
                                    </div>

                                    <br/>

                                    <div class="text-center">
                                        <Trans i18nKey="mailconfirm.error" />
                                    </div>

                                    <br/>

                                    <div className="text-center">
                                        <div className="text-center m-3">
                                            <NavLink to="/" className="tutv-button">
                                                <Trans i18nKey="login.continue" />
                                            </NavLink>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                );

        return (
            <div className="h-100">

                <div className="full-overlay"></div>
                <div className="container h-100">
                    <div className="row justify-content-center h-100">
                        <div className="col-lg-8 col-sm-11 align-self-center white-background">
                            <div className="w-100 divide-section-bottom text-center">
                                <img src={require('../img/shortcuticon.png')} alt="TUTV" />
                                <span className="title-page">
                                    <Trans i18nKey="mailconfirm.title" values={{ name: this.props.logged_user.userName[0].toUpperCase() + this.props.logged_user.userName.slice(1)}} />
                                </span>
                            </div>

                            <div class="text-center">
                                <Trans i18nKey="mailconfirm.success" />
                            </div>

                            <div className="divide-section-top text-center">
                                <div className="text-center m-3">
                                    <NavLink to="/" className="tutv-button">
                                        <Trans i18nKey="index.explore" />
                                    </NavLink>
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

const mapDispatchToProps = (dispatch) => {
    return {
        loginUser: (token, user) => { dispatch({ type: "LOGIN", payload: { "token": token, "user": user, "updateLocalStorage": true } }) }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(MailConfirm);