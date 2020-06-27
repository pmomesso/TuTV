import React, { Component } from 'react';
import { Trans } from 'react-i18next';
import { NavLink } from 'react-router-dom';
import { Formik } from 'formik';
import * as Yup from 'yup';
import Axios from 'axios';
import { withTranslation } from 'react-i18next';
import Modal from 'react-bootstrap/Modal'

import LoadingModal from '../components/LoadingModal.js';

class Register extends Component {

    state = {
        "showingConfirmationModal": false,
        "loading": false
    }

    render() {
        return (
            <div className="h-100">

                <LoadingModal backdrop="static" visible={this.state.loading} />

                <Modal backdrop="static" centered show={this.state.showingConfirmationModal}>
                    <Modal.Header>
                        <Modal.Title>
                            <Trans i18nKey="register.successModalTitle" />
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Trans i18nKey="register.successModalBody" />
                    </Modal.Body>
                    <Modal.Footer>
                        <NavLink to="/" className="tutv-button">
                            <Trans i18nKey="register.continue" />
                        </NavLink>
                    </Modal.Footer>
                </Modal>

                <div className="full-overlay"></div>
                <div className="container h-100">
                    <div className="row justify-content-center h-100">
                        <div className="col-lg-8 col-sm-11 align-self-center white-background">
                            <div className="w-100 divide-section-bottom text-center">
                                <img src={require('../img/shortcuticon.png')} alt="TUTV" />
                                <span className="title-page">
                                    <Trans i18nKey="register.title" />
                                </span>
                            </div>

                            <Formik
                                initialValues={{ username: '', mail: '', password: '', repeatPassword: '' }}
                                validationSchema={Yup.object({
                                    mail: Yup.string()
                                        .email('Invalid email address')
                                        .required('Required'),
                                    password: Yup.string()
                                        .max(20, 'Must be 20 characters or less')
                                        .required('Required'),
                                    repeatPassword: Yup.string()
                                        .required('Required')
                                        .oneOf([Yup.ref('password'), null], 'Passwords must match'),
                                    username: Yup.string()
                                        .max(20, 'Must be 20 characters or less')
                                        .required('Required'),
                                })}
                                onSubmit={(values, actions) => {
                                    this.setState({"loading": true});
                                    Axios.post("/users/register", JSON.stringify(values))
                                        .then((res) => {
                                            this.setState({
                                                "showingConfirmationModal": true
                                            });
                                        })
                                        .catch((err) => {
                                            if(err.response.status === 409) {
                                                if(Array.isArray(err.response.data))
                                                    err.response.data.forEach(error => actions.setFieldError(error.field, this.props.t("register." + error.i18Key)));
                                                else
                                                actions.setFieldError(err.response.data.field, this.props.t("register." + err.response.data.i18Key));
                                                
                                            } else                                             
                                                alert("Error: " + err.response.status);
                                        })
                                        .finally(() => {
                                            this.setState({"loading": false});
                                            actions.setSubmitting(false)
                                        });
                                }}
                            >
                                {formik => (
                                    <form onSubmit={formik.handleSubmit}>
                                        <div className="container">
                                            <div className="row w-100">
                                                <div className="col-5 h-100 align-self-center">
                                                    <label className="ml-lg-5" htmlFor="mail">
                                                        <Trans i18nKey="register.mail" />
                                                    </label>
                                                </div>
                                                <div className="col-7 align-self-center">
                                                    <input {...formik.getFieldProps('mail')} className="m-3 w-100" id="mail" name="mail" type="text" minLength="6" maxLength="60" />
                                                    {formik.touched.mail && formik.errors.mail ? (
                                                        <span className="error m-3">{formik.errors.mail}</span>
                                                        //<Trans i18nKey="register.mailExists" />
                                                    ) : null}
                                                </div>
                                            </div>
                                            <div className="row w-100">
                                                <div className="col-5 align-self-center">
                                                    <label className="ml-lg-5" htmlFor="username">
                                                        <Trans i18nKey="register.username" />
                                                    </label>
                                                </div>
                                                <div className="col-7 align-self-center">
                                                    <input {...formik.getFieldProps('username')} className="m-3 w-100" id="username" name="username" type="text" minLength="6" maxLength="32" />
                                                    {formik.touched.username && formik.errors.username ? (
                                                        <span className="error m-3">{formik.errors.username}</span>
                                                        //<Trans i18nKey="profile.usernameExists" />
                                                    ) : null}
                                                </div>
                                            </div>
                                            <div className="row w-100">
                                                <div className="col-5 align-self-center">
                                                    <label className="ml-lg-5" htmlFor="password">
                                                        <Trans i18nKey="register.password" />
                                                    </label>
                                                </div>
                                                <div className="col-7 align-self-center">
                                                    <input {...formik.getFieldProps('password')} className="m-3 w-100" id="password" name="password" type="password" minLength="6" maxLength="32" />
                                                    {formik.touched.password && formik.errors.password ? (
                                                        <span className="error m-3">{formik.errors.password}</span>
                                                    ) : null}
                                                </div>
                                            </div>
                                            <div className="row w-100">
                                                <div className="col-5 align-self-center">
                                                    <label className="ml-lg-5" htmlFor="repeatPassword">
                                                        <Trans i18nKey="register.repeatPassword" />
                                                    </label>
                                                </div>
                                                <div className="col-7 align-self-center">
                                                    <input {...formik.getFieldProps('repeatPassword')} className="m-3 w-100" id="repeatPassword" name="repeatPassword" type="password" minLength="6" maxLength="32" />
                                                    {formik.touched.repeatPassword && formik.errors.repeatPassword ? (
                                                        <span className="error m-3">{formik.errors.repeatPassword}</span>
                                                        //<Trans i18nKey="register.unmatchedPassword" />
                                                    ) : null}
                                                </div>
                                            </div>
                                        </div>
                                        <div className="text-center m-3">
                                            <button type="submit" className="tutv-button">
                                                <Trans i18nKey="register.submit" />
                                            </button>
                                        </div>

                                    </form>
                                )}
                            </Formik>








                            <div className="divide-section-top text-center">
                                <span>
                                    <Trans i18nKey="register.haveaccount" />
                                </span>
                                {' '}
                                <NavLink to="/login">
                                    <Trans i18nKey="register.login" />
                                </NavLink>
                                <br />
                                <NavLink to="/">
                                    <Trans i18nKey="register.continue" />
                                </NavLink>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}

export default withTranslation()(Register);