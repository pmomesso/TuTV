import React, {Component} from 'react';
import {NavLink} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircle} from "@fortawesome/free-solid-svg-icons";

class NotificationItem extends Component {

    render() {
        let notificationCircle;
        if (this.props.viewed) {
            notificationCircle = (
                <div className="col-2 text-center align-self-center">
                <FontAwesomeIcon icon={ faCircle } style={{color: "#f00"}}/>
            </div>)
        } else {
            notificationCircle = <div className="col-2"></div>
        }

        return (
            <li className="row justify-content-center">
            <div className="col-10"><NavLink to="/">{this.props.message}</NavLink></div>
        {notificationCircle}
        </li>
    )
    }
}

export default NotificationItem;