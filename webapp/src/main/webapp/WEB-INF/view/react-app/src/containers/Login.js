import React, {Component} from 'react';
import { Trans } from 'react-i18next';
import { NavLink } from 'react-router-dom';
import { Formik } from 'formik';
import * as Yup from 'yup';

import Axios from 'axios';
import { connect } from 'react-redux';

class Login extends Component {

    state = {
        "error": 0
    };

    render() {

        return (
            <div className="h-100">
                <div className="full-overlay"></div>
                <div className="container h-100">
                    <div className="row justify-content-center h-100">
                        <div className="col-lg-6 col-sm-11 align-self-center white-background">
                            <div className="w-100 divide-section-bottom text-center">
                                <img src={require('../img/shortcuticon.png')} alt="TUTV" />
                                <span className="title-page">
                                    <Trans i18nKey="login.title"/>
                                </span>
                            </div>

                            <Formik
                                initialValues={{ username: '', password: '', rememberme: false }}
                                validationSchema={Yup.object({
                                    username: Yup.string()
                                        .email('Invalid email address')
                                        .required('Required'),
                                    password: Yup.string()
                                        .max(20, 'Must be 20 characters or less')
                                        .required('Required'),
                                })}
                                onSubmit={(values, actions) => {
                                    Axios.get("/user", {
                                        headers: {
                                            authorization: 'Basic ' + btoa(values.username + ":" + values.password)
                                        }
                                    }).then((res) => {
                                            let token = res.headers.authorization;
                                            let user = res.data;

                                            this.props.loginUser(token, user);
                                            this.props.history.push("/");
                                    }).catch((err) => {
                                        this.setState({ error: err.response.status});
                                    }).finally(() => actions.setSubmitting(false));
                                }}
                                >
                                {formik => (
                                    <form onSubmit={formik.handleSubmit}>
                                        <div className="container">
                                            <div className="row w-100">
                                                <div className="col-4 align-self-center">
                                                    <label className="ml-lg-4" htmlFor="username">
                                                        <Trans i18nKey="login.username"/>
                                                    </label>
                                                </div>
                                                <div className="col-8 align-self-center">
                                                    <input {...formik.getFieldProps('username')} className="m-3 w-100" id="username" name="username" type="text" minLength="6" maxLength="32" />
                                                
                                                    {formik.touched.username && formik.errors.username ? (
                                                        <span className="error m-3 w-100">{formik.errors.username}</span>
                                                    ) : null}

                                                </div>
                                            </div>
                                            <div className="row w-100">
                                                <div className="col-4 align-self-center">
                                                    <label className="ml-lg-4" htmlFor="password">
                                                        <Trans i18nKey="login.password"/>
                                                    </label>
                                                </div>
                                                <div className="col-8 align-self-center">
                                                    <input {...formik.getFieldProps('password')} className="m-3 w-100" id="password" name="password" type="password" minLength="6" maxLength="32" />

                                                    {formik.touched.password && formik.errors.password ? (
                                                        <span className="error m-3 w-100">{formik.errors.password}</span>
                                                    ) : null}

                                                </div>
                                            </div>
                                            {
                                                (this.state.error !== 0) &&
                                                <div className="row w-100">
                                                    <div className="col-4 align-self-center"></div>
                                                    <div className="col-8 align-self-center">
                                                        {
                                                            (this.state.error === 403) ?
                                                                (
                                                                    <span className="error m-3 w-100"><Trans i18nKey="login.ErrorNotConfirmed"/></span>
                                                                )
                                                                :
                                                                (
                                                                    (this.state.error === 401) ?
                                                                        (<span className="error m-3 w-100"><Trans i18nKey="login.ErrorInvalidCredentials"/></span>)
                                                                        :
                                                                        (<span className="error m-3 w-100"><Trans i18nKey="login.ErrorOther"/></span>)
                                                                )
                                                        }
                                                    </div>
                                                </div>
                                            }
                                            <div className="text-center p-3">
                                                <label htmlFor="rememberme">
                                                    <input {...formik.getFieldProps('rememberme')} id="rememberme" name="rememberme" type="checkbox"/>
                                                    <Trans i18nKey="login.rememberme"/>
                                                </label>
                                            </div>


                                            {formik.errors.general && 
                                                <div className="text-center w-100">
                                                        <span className="error m-3 w-100">
                                                            <Trans i18nKey="login.invalidCredentials"/>
                                                        </span>
                                                    </div>
                                            }


                                            <div className="text-center m-3">
                                                <button type="submit" className="tutv-button">
                                                    <Trans i18nKey="login.submit"/>
                                                </button>
                                            </div>

                                        </div>


                                    </form>
                                )}
                            </Formik>

                            <div className="divide-section-top text-center">
                                <span>
                                    <Trans i18nKey="login.noaccount"/>
                                </span>
                                {' '}
                                <NavLink to="/register">
                                    <Trans i18nKey="login.createaccount"/>
                                </NavLink>
                                <br />
                                <NavLink to="/">
                                    <Trans i18nKey="login.continue"/>
                                </NavLink>
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
        loginUser: (token, user) => { dispatch({ type: "LOGIN", payload: { "token": token, "user": user, "updateLocalStorage": true } }) }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);