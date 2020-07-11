import React from 'react';
import { Trans } from 'react-i18next';
import { NavLink } from 'react-router-dom';

const ErrorPage = (props) => {
    const { status, body } = props;
    return (
        <div className="main-block h-100">
            <div className="main-block-container h-100">
                <div className="h-100">
                    <section id="new-shows" className="h-100 p-0">
                        <div className="container h-100">
                            <div className="row justify-content-center h-100">
                                <div className="col-md-12 align-self-center">
                                    <div className="error-template">
                                        <h1>Oops!</h1>
                                        <h2 className="m-5"><Trans i18nKey={status}/></h2>
                                        <div className="error-details m-5">
                                            <Trans i18nKey={body}/>
                                        </div>
                                        <div className="error-actions">
                                            <NavLink to="/" className="tutv-button btn-lg">
                                                <Trans i18nKey="error.goHome"/>
                                            </NavLink>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    )
};

export default ErrorPage;