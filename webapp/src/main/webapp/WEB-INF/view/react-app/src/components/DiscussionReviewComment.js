import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash, faHeart } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import { withTranslation } from 'react-i18next';
import { compose } from 'recompose';
import { connect } from 'react-redux';
import Axios from 'axios';

class DiscussionReviewComment extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            comment: props.comment
        };
    }

    toggleLike = () => {
        const newVal = !this.state.comment.loggedInUserLikes;
    
        Axios.put("/series/" + this.props.series.id + "/reviews/" + this.props.seriesReview.id + "/comments/" + this.state.comment.id, 
            JSON.stringify( {"loggedInUserLikes": newVal} ))
            .then(res => {
                this.setState({
                    ...this.state,
                    comment: res.data
                });
            })
    }

    delete = () => {

    }

    toggleUserBan = () => {

    }

    render() {
        const { t, logged_user } = this.props;

        const { comment } = this.state;

        return (
            <article className="reply clearfix initialized">
                <div className="holder" key={comment.id}>
                    <div className="author-label">
                        { logged_user ?
                            (<Link to={"/users/" + comment.user.id} title={t("index.profile")}>
                                <span>{comment.user.userName}</span>
                            </Link>)
                            :
                            (<span>{comment.user.userName}</span>)
                        }

                        { ( logged_user && logged_user.isAdmin && logged_user.id !== comment.user.id ) &&
                            (
                                ( comment.user.isBanned ) ?
                                    (<button className="remove" onClick={this.toggleUserBan}>
                                        <img src="/resources/img/unban.png" title={t("series.unban")} alt={t("series.unban")}/>
                                    </button>)
                                    :
                                    (<button className="remove" onClick={this.toggleUserBan}>
                                        <img src="/resources/img/ban.png" title={t("series.ban")} alt={t("series.ban")}/>
                                    </button>)
                            )
                        }

                        <div className="float-right mr-5">
                            { ( logged_user && (logged_user.isAdmin || logged_user.id === comment.user.id) ) &&
                                <button className="remove" onClick={this.delete}>
                                    <FontAwesomeIcon icon={faTrash} />
                                </button>
                            }

                            { ( logged_user && comment.loggedInUserLikes ) ?
                                (<button className="heart post-liked no-padding" onClick={this.toggleLike}>
                                    <FontAwesomeIcon icon={faHeart} />
                                </button>)
                                :
                                (<button className="heart no-padding" onClick={this.toggleLike}>
                                    <FontAwesomeIcon icon={faHeart} />
                                </button>)
                            }
                            { comment.numLikes }
                        </div>
                    </div>
                    <blockquote>
                        <p>{ comment.body }</p>
                    </blockquote>
                </div>
            </article>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        logged_user: state.auth.user
    }
}

export default compose(
    withTranslation(),
    connect(mapStateToProps),
)(DiscussionReviewComment);