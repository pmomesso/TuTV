import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faTrash, faHeart, faBan} from '@fortawesome/free-solid-svg-icons';
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
    
        Axios.put("/series/" + this.props.series.id + "/reviews/" + this.props.seriesReview.id + "/comments/" + this.state.comment.id + "/like",
            JSON.stringify( {"loggedInUserLikes": newVal} ))
            .then(res => {
                let newComment = {
                    ...this.state.comment,
                    loggedInUserLikes: res.data.loggedInUserLikes,
                    numLikes: res.data.numLikes
                };

                this.setState({
                    ...this.state,
                    comment: newComment
                });
            })
    };

    delete = () => {

    }

    toggleUserBan = () => {

    }

    render() {
        const { t, logged_user, deleteComment } = this.props;

        const { comment } = this.state;

        return (
            <article className="reply clearfix initialized">
                <div className="holder" key={comment.id}>
                    <div className="author-label">
                        { logged_user ?
                            (<Link to={"/users/" + comment.user.id} title={t("index.profile")}>
                                <span style={{fontFamily: "proximaNova", color: "#777"}}>{comment.user.userName}</span>
                            </Link>)
                            :
                            (<span style={{fontFamily: "proximaNova", color: "#777"}}>{comment.user.userName}</span>)
                        }

                        { ( logged_user && logged_user.admin && logged_user.id !== comment.user.id ) &&
                            (
                                ( comment.user.isBanned ) ?
                                    (<button className="remove" onClick={this.toggleUserBan}>
                                        <img src={require('../img/unban.png')} title={t("series.unban")} alt={t("series.unban")}/>
                                    </button>)
                                    :
                                    (<button className="heart post-liked" onClick={this.toggleUserBan}>
                                            <FontAwesomeIcon icon={ faBan }/>
                                    </button>)
                            )
                        }

                        <div className="float-right">
                            { ( logged_user && (logged_user.admin || logged_user.id === comment.user.id) ) &&
                                <button className="remove-small p-0" onClick={() => deleteComment(comment)}>
                                    <FontAwesomeIcon icon={faTrash} />
                                </button>
                            }

                            { ( logged_user && comment.loggedInUserLikes ) ?
                                (<button className="heart post-liked" onClick={this.toggleLike}>
                                    <FontAwesomeIcon icon={faHeart} />
                                </button>)
                                :
                                (<button className="heart" onClick={this.toggleLike}>
                                    <FontAwesomeIcon icon={faHeart} />
                                </button>)
                            }
                            <span>{ comment.numLikes }</span>
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