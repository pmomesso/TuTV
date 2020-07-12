import React from 'react';
import { NavLink, Link, withRouter, matchPath, useLocation } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import Modal from 'react-modal';
import { connect } from 'react-redux';

import $ from "jquery";
// import Popper from 'popper.js';
import 'bootstrap/dist/js/bootstrap.bundle.min';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUsers, faBars, faBell, faTasks } from '@fortawesome/free-solid-svg-icons'
import { faCompass, faCalendarCheck, faUser } from '@fortawesome/free-regular-svg-icons'
import NotificationItem from "./NotificationItem";
import { compose } from 'recompose';

const customStyles = {
    content : {
        display               : 'block !important',
        top                   : '390px',
        left                  : '205px',
        marginLeft            : '-130px',
        width                 : '260px',
        backgroundColor       : '#f3f3f3',
        padding               : '10px',
        boxShadow             : '0 0 1px 1px #777',
        borderRadius          : '4px',
        marginTop             : '10px',
        maxHeight             : '330px',
        overflow              : 'hidden'
    },
    overlay: {
        zIndex: 100,
        backgroundColor: 'none'
    }
};

const Navbar = (props) => {
    const { t } = useTranslation();
    let location = useLocation();

    const [modalIsOpen,setIsOpen] = React.useState(false);

    function openModal() {
        setIsOpen(true);
    }
    function closeModal(){
        setIsOpen(false);
    }
    function extend(){
        var navigation = $(".page-left");
        if ($(navigation).hasClass("extended")) {
            $(navigation).removeClass("extended");
        } else {
            $(navigation).addClass("extended");
        }
    }

    function searchKeyPress(event) {
        if(event.key === 'Enter') {
            const searchPageMatch = matchPath(location.pathname, {
                path: '/search',
                exact: true,
                strict: false
            });

            if(!searchPageMatch)
                props.history.push('/search');
        }
    }

    function handleSearchChange(event) {
        props.updateSearchName(event.target.value);
    }

    return (
        <div className="page-left page-sidebar page-column">
            <Modal isOpen={modalIsOpen} onRequestClose={closeModal} style={customStyles} contentLabel="Example Modal">
                <div id="notifications" style={{maxHeight: '330px'}}>
                    <ul className="notifications-list list-unstyled container">
                        <NotificationItem message="notification message with link to resource" viewed={true}/>
                        <NotificationItem message="notification message with link to resource" viewed={false}/>
                        <NotificationItem message="notification message with link to resource" viewed={false}/>
                        <NotificationItem message="notification message with link to resource" viewed={false}/>
                        <NotificationItem message="notification message with link to resource" viewed={true}/>
                        <NotificationItem message="notification message with link to resource" viewed={true}/>
                    </ul>
                </div>
            </Modal>

            <button className="extend-left-link">
                <span className="fa-normal" onClick={extend} >
                    <FontAwesomeIcon icon={ faBars } />
                </span>
            </button>
            <div className="wrapper">
                <a id="home-link" href="/">
                    <img className="logo tutv" src={require('../img/Tutv.png')} alt="TUTV"/>
                    <span id="home-text">TUTV</span>
                </a>
                <div id="global-search" className="navbar-form form-search">
                    <div className="form-group">
                        <input type="text" id="global-search-input" value={props.search_name} onChange={handleSearchChange} name="name" className="show-search" placeholder={ t("search.search") } onKeyPress={searchKeyPress}/>
                    </div>
                    <div className="form-group advanced-search">
                        <Link id="advancedSearchLink" to="/search">
                            { t("search.advancedSearch") }
                        </Link>
                    </div>
                </div>
                <div className="all-left-navs">
                    <section id="menu">
                        <ul className="menu list-unstyled">
                            {props.logged_user !== null && props.logged_user.admin &&
                            <li className="upcoming ">
                                <NavLink id="menu_users" to="/users" activeClassName="active">
                                    <FontAwesomeIcon icon={faUsers} style={{
                                        marginRight: "15px",
                                        verticalAlign: "middle",
                                        fontSize: "30px",
                                        fontWeight: "300",
                                        color: "#999"
                                    }}/>
                                    <span>
                                         {t("index.users")}
                                    </span>
                                </NavLink>
                            </li>}
                            {props.logged_user !== null &&
                            <li className="upcoming">
                                <NavLink id="menu_upcoming" to="/upcoming" activeClassName="active">
                                    <FontAwesomeIcon icon={faCalendarCheck} style={{
                                        marginRight: "15px",
                                        verticalAlign: "middle",
                                        fontSize: "30px",
                                        fontWeight: "300",
                                        color: "#999"
                                    }}/>
                                    <span>
                                        {t("index.upcoming")}
                                    </span>
                                </NavLink>
                            </li>
                            }
                            {props.logged_user !== null &&
                            <li className="home ">
                                <NavLink id="menu_watchlist" to="/watchlist" activeClassName="active">
                                    <FontAwesomeIcon icon={faTasks} style={{
                                        marginRight: "15px",
                                        verticalAlign: "middle",
                                        fontSize: "30px",
                                        fontWeight: "300",
                                        color: "#999"
                                    }}/>
                                    <span>
                                        {t("index.watchlist")}
                                    </span>
                                </NavLink>
                            </li>
                            }
                            <li className="explore">
                                <NavLink exact id="menu_home" to="/" activeClassName="active">
                                    <FontAwesomeIcon icon={ faCompass } style={{marginRight: "15px", verticalAlign: "middle", fontSize: "30px", fontWeight: "300", color: "#999"}} />
                                    <span>
                                        { t("index.explore") }
                                    </span>
                                </NavLink>
                            </li>
                        </ul>
                    </section>

                    {props.logged_user !== null &&
                        <section id="user-nav">
                            <h1>
                                {props.logged_user.userName}
                            </h1>
                            <div className="alt-user-nav">
                                <NavLink to="#" activeClassName="active">
                                    <span onClick={openModal} className="notifications-btn icon-btn">
                                        <FontAwesomeIcon icon={ faBell } />
                                        <div className="badge zero font" style={{color: "#ccc"}}>count</div>
                                    </span>
                                </NavLink>
                            </div>
                            <ul className="menu list-unstyled">
                                <li className="profile">
                                    <NavLink to={"/profiles/" + props.logged_user.id}>
                                    <FontAwesomeIcon icon={ faUser } style={{marginRight: "15px", verticalAlign: "middle", fontSize: "30px", fontWeight: "300", color: "#999"}} />
                                        <span>
                                            { t("index.profile") }
                                        </span>
                                    </NavLink>
                                </li>
                            </ul>
                        </section>
                    }
                    <section>
                        {props.logged_user === null ?
                            <NavLink className="signout-link" to="/login">
                                <img className="logo logo_icon" src={require('../img/sign_in.png')} alt={ t("index.signin") }/>
                                <span>
                                    { t("index.signin") }
                                </span>
                            </NavLink>
                        :
                            <NavLink className="signout-link" to="/logout">
                                <img className="logo logo_icon" src={require('../img/sign_out.png')} alt={ t("index.signout")} />
                                <span>
                                    { t("index.signout") }
                                </span>
                            </NavLink>
                        }
                    </section>
                </div>
            </div>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        logged_user: state.auth.user,
        search_name: state.search.searchName
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        updateSearchName: searchName => { dispatch({ type: "UPDATE_SEARCH", payload: { "searchName": searchName} }) }
    }
};

export default compose(
    connect(mapStateToProps, mapDispatchToProps),
)(withRouter(Navbar));