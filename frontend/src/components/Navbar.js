import React from 'react';
import { NavLink } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import Modal from 'react-modal';
import { connect } from 'react-redux';

/*import $ from 'jquery';
import Popper from 'popper.js';*/
import 'bootstrap/dist/js/bootstrap.bundle.min';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faBars, faBell, faTasks } from '@fortawesome/free-solid-svg-icons'
import { faCompass, faCalendarCheck, faUser } from '@fortawesome/free-regular-svg-icons'
import NotificationItem from "./NotificationItem";

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

    const [modalIsOpen,setIsOpen] = React.useState(false);
    function openModal() {
        setIsOpen(true);
    }
    function closeModal(){
        setIsOpen(false);
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

            <a href="/#" className="extend-left-link">
                <span className="fa-normal" /*onclick="extend()"*/>
                    <FontAwesomeIcon icon={ faBars } />
                </span>
            </a>
            <div className="scrollable scrolling-element">
                <div className="wrapper">
                    <a id="home-link" href="/">
                        <img className="logo tutv" src={require('../img/Tutv.png')} alt="TUTV"/>
                        <span id="home-text">TUTV</span>
                    </a>
                    <form id="global-search" method="get" className="navbar-form form-search" action="/searchResults">
                        <div className="form-group">
                            <img className="logo logo_icon" src={require('../img/search.png')} alt={ t("search.search") }/>
                        </div>
                        <div className="form-group">
                            <input type="text" id="global-search-input" name="name" className="show-search" placeholder={ t("search.search") }/>
                        </div>
                        <div className="form-group advanced-search">
                            <a id="advancedSearchLink" href="/search">
                                { t("search.advancedSearch") }
                            </a>
                        </div>
                        <input type="submit" className="novisible" />
                    </form>
                    <div className="all-left-navs">
                        <section id="menu">
                            <ul className="menu list-unstyled">
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
                        {props.logged_user === null && 
                            <section>
                                <NavLink className="signout-link" to="/login">
                                    <img className="logo logo_icon" src={require('../img/sign_in.png')} alt={ t("index.signin") }/>
                                    <span>
                                        { t("index.signin") }
                                    </span>
                                </NavLink>
                            </section>
                        }
                    </div>
                </div>
            </div>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        logged_user: state.auth.user
    }
}

export default connect(mapStateToProps)(Navbar);