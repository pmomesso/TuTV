import React, {Component} from 'react';
import {withRouter} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircle} from "@fortawesome/free-solid-svg-icons";
import Axios from 'axios';
import { connect } from 'react-redux';

class NotificationItem extends Component {
    onNotificationClicked = () => {
        const {logged_user, notification, forceUpdateList} = this.props;

        //Axios.put("/users/" + logged_user.id + "/notifications/" + notification.id + "/viewed", JSON.stringify({"viewedByUser": true}))
        Axios.delete("/users/" + logged_user.id + "/notifications/" + notification.id)
        .then((res) => {
            if(notification.series)
                this.props.history.push('/series/' + notification.series.id);

            forceUpdateList();
        })
    };

    render() {
        const { viewedByUser, message } = this.props.notification;

        return (
            <li className="row justify-content-center" style={{cursor: "pointer"}} onClick={ this.onNotificationClicked }>
                <div className="col-10" >
                    { message }
                </div>
                <div className="col-2 text-center align-self-center">
                    { (!viewedByUser) &&
                        <FontAwesomeIcon icon={ faCircle } style={{color: "#f00"}}/>
                    }
                </div>
            </li>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        logged_user: state.auth.user
    }
}

export default 
connect(mapStateToProps)(withRouter(NotificationItem));