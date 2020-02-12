import React, {Component} from 'react';
import { Trans } from 'react-i18next';
import { NavLink } from 'react-router-dom';

class Register extends Component {

    render() {

        return (
            <div className="h-100">
                <div className="full-overlay"></div>
                <div className="container h-100">
                    <div className="row justify-content-center h-100">
                        <div className="col-lg-8 col-sm-11 align-self-center white-background">
                            <div className="w-100 divide-section-bottom text-center">
                                <img src={require('../img/shortcuticon.png')} alt="TUTV" />
                                <span className="title-page">
                                    <Trans i18nKey="register.title"/>
                                </span>
                            </div>
                            <form>
                                <div className="container">
                                    <div className="row w-100">
                                        <div className="col-5 h-100 align-self-center">
                                            <label className="ml-lg-5" htmlFor="mail">
                                                <Trans i18nKey="register.mail"/>
                                            </label>
                                        </div>
                                        <div className="col-7 align-self-center">
                                            <input className="m-3 w-100" id="mail" name="mail" type="text" minLength="6" maxLength="60" />
                                            <p className="error m-3"><Trans i18nKey="register.mailExists"/></p>
                                        </div>
                                    </div>
                                    <div className="row w-100">
                                        <div className="col-5 align-self-center">
                                            <label className="ml-lg-5" htmlFor="username">
                                                <Trans i18nKey="register.username"/>
                                            </label>
                                        </div>
                                        <div className="col-7 align-self-center">
                                            <input className="m-3 w-100" id="username" name="username" type="text" minLength="6" maxLength="32" />
                                            <p className="error m-3"><Trans i18nKey="profile.usernameExists"/></p>
                                        </div>
                                    </div>
                                    <div className="row w-100">
                                        <div className="col-5 align-self-center">
                                            <label className="ml-lg-5" htmlFor="password">
                                                <Trans i18nKey="register.password"/>
                                            </label>
                                        </div>
                                        <div className="col-7 align-self-center">
                                            <input className="m-3 w-100" id="password" name="password" type="password" minLength="6" maxLength="32" />
                                            <p className="error m-3">error cantidad de caracteres</p>
                                        </div>
                                    </div>
                                    <div className="row w-100">
                                        <div className="col-5 align-self-center">
                                            <label className="ml-lg-5" htmlFor="repeatPassword">
                                                <Trans i18nKey="register.repeatPassword"/>
                                            </label>
                                        </div>
                                        <div className="col-7 align-self-center">
                                            <input className="m-3 w-100" id="repeatPassword" name="repeatPassword" type="password" minLength="6" maxLength="32" />
                                            <p className="error m-3"><Trans i18nKey="register.unmatchedPassword"/></p>
                                        </div>
                                    </div>
                                </div>
                                <div className="text-center m-3">
                                    <button className="tutv-button">
                                        <Trans i18nKey="register.submit"/>
                                    </button>
                                </div>
                            </form>
                            <div className="divide-section-top text-center">
                                <span>
                                    <Trans i18nKey="register.haveaccount"/>
                                </span>
                                {' '}
                                <NavLink to="/login">
                                    <Trans i18nKey="register.login"/>
                                </NavLink>
                                <br />
                                <NavLink to="/">
                                    <Trans i18nKey="register.continue"/>
                                </NavLink>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}

export default Register;