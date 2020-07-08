import React, { Component } from 'react';
import { Trans } from 'react-i18next';
import { withTranslation } from 'react-i18next';
import { NavLink } from 'react-router-dom';
import { Digital } from 'react-activity';
import 'react-activity/dist/react-activity.css';
import { connect } from 'react-redux';
import { compose } from 'recompose';
import Axios from 'axios';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faBan } from '@fortawesome/free-solid-svg-icons'

let linkHeaderParser = require('parse-link-header');

class UsersPage extends Component {

    state = {
        source: null,
        users: null,
        prevUrl: null,
        nextUrl: null,
        loading: true
    };

    nextPage = () => {
        this.setState({
            source: this.state.nextUrl,
            loading: true
        }, this.fetchData);
    };

    prevPage = () => {
        this.setState({
            source: this.state.prevUrl,
            loading: true
        }, this.fetchData);
    };

    fetchData = () => {
        Axios.get(this.state.source)
            .then(res => {
                let linkHeader = res.headers["link"];
                let linkHeaderParsed = linkHeaderParser(linkHeader);

                let nextUrl = null;
                let prevUrl = null;
                if(linkHeaderParsed) {
                    nextUrl = linkHeaderParsed.next ? linkHeaderParsed.next.url : null;
                    prevUrl = linkHeaderParsed.prev ? linkHeaderParsed.prev.url : null;
                }

                this.setState({
                    users: res.data,
                    nextUrl: nextUrl,
                    prevUrl: prevUrl,
                    loading: false
                });
            });
    };

    componentDidMount = () => {
        this.setState( {source: this.props.source }, this.fetchData)
    };

    toggleBan = (user_id, banned, index) => {
        Axios.put("/users/" + user_id + "/banned", {"banned": banned})
            .then((res) => {

                let newUsers = [ ...this.state.users];
                newUsers[index] = res.data;

                this.setState({
                    users: newUsers
                });
            })
            .catch((err) => {
                /* TODO SI CADUCO LA SESION? */
                //alert("Error: " + err.response.status);
            });
    };

    render() {

        const { t } = this.props;

        if(this.state.loading)
            return (
                <section>
                    <div style={{width: "100%",height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
                        <Digital color="#727981" size={32} speed={1} animating={true} />
                    </div>
                </section>
            );

        const users = this.state.users;

        const usersTable = users.map((user, index) => {
            return(
                <tr key={index}>
                    <td>
                        {
                            (user.avatar) ?
                                (<img src={`data:image/jpeg;base64,${user.avatar}`} className="no-display-small" alt={user.userName}/>)
                                :
                                (<img src={require('../img/user.png')} className="no-display-small" alt={user.userName}/>)
                        }
                        <NavLink to={"/profiles/" + user.id} className="user-link">
                            {user.userName}
                        </NavLink>
                        {
                            (user.admin) ?
                                (<span className="user-subhead">Admin</span>)
                                :
                                (<span className="user-subhead"><Trans i18nKey="users.member"/></span>)
                        }
                    </td>
                    <td className="text-center">
                        {
                            (user.banned) ?
                                (<span className="label label-danger"><Trans i18nKey="users.banned"/></span>)
                                :
                                (<span className="label label-success"><Trans i18nKey="users.active"/></span>)
                        }
                    </td>
                    <td>
                        <span>{user.mail}</span>
                    </td>
                    <td style={{width: "20%"}}>
                        {
                            (user.banned) ?
                                (<button className="remove" title={`${t('series.unban')}`} onClick={() => this.toggleBan(user.id, false, index)}>
                                    <img src={require('../img/unban.png')} title={`${t('series.unban')}`} alt={`${t('series.unban')}`}/>
                                </button>)
                                :
                                (<button className="heart post-liked" title={`${t('series.ban')}`} onClick={() => this.toggleBan(user.id, true, index)}>
                                    <FontAwesomeIcon icon={ faBan } style={{fontSize: "20px"}}/>
                                </button>)
                        }
                    </td>
                </tr>
            );
        });

        return (
            <div>
                <div className="main-block-container">
                    <div id="home" className="h-100">
                        <section id="new-shows" className="h-100 p-small-0">
                            <h1 className="no-display-small mb-5"><Trans i18nKey="users.title"/></h1>
                            <div className="container bootstrap snippet">
                                <div className="row">
                                    <div className="col-lg-12">
                                        <div className="main-box no-header clearfix">
                                            <div className="main-box-body clearfix">
                                                <div className="table-responsive">
                                                    <table className="table user-list">
                                                        <thead>
                                                            <tr>
                                                                <th><span><Trans i18nKey="users.user"/></span></th>
                                                                <th className="text-center"><span><Trans i18nKey="users.status"/></span></th>
                                                                <th><span><Trans i18nKey="users.email"/></span></th>
                                                                <th><Trans i18nKey="users.action"/></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            { usersTable }
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <nav className="text-center" aria-label="...">
                                            <ul className="pagination">
                                                <li className={`page-item ${(typeof this.state.prevUrl !== "string") ? "disabled" : ""}`}>
                                                    {
                                                        (typeof this.state.prevUrl === "string") ?
                                                            (<a className="page-link" onClick={this.prevPage}>
                                                                <Trans i18nKey="users.previous"/>
                                                            </a>)
                                                            :
                                                            (<span className="page-link">
                                                                <Trans i18nKey="users.previous"/>
                                                            </span>)
                                                    }
                                                </li>
                                                <li className={`page-item ${(typeof this.state.nextUrl !== "string") ? "disabled" : ""}`}>
                                                    {
                                                        (typeof this.state.nextUrl === "string") ?
                                                            (<a className="page-link" onClick={this.nextPage}>
                                                                <Trans i18nKey="users.next"/>
                                                            </a>)
                                                            :
                                                            (<span className="page-link">
                                                                <Trans i18nKey="users.next"/>
                                                            </span>)
                                                    }
                                                </li>
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </section>
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

export default compose(
    connect(mapStateToProps),
    withTranslation()
)(UsersPage);