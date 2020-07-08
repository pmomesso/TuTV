import React, { Component } from 'react';
import DiscussionReviewComment from './DiscussionReviewComment';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart, faTrash, faExclamationTriangle } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import { withTranslation } from 'react-i18next';
import { compose } from 'recompose';
import { connect } from 'react-redux';
import Axios from 'axios';
import { Formik } from 'formik';
import * as Yup from 'yup';
import { confirmAlert } from 'react-confirm-alert';

class DiscussionReview extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            revealSpoilers: false,
            seriesReview: props.seriesReview
        };
    }

    toggleLike = () => {
        const newVal = !this.state.seriesReview.loggedInUserLikes;

        Axios.put("/series/" + this.props.series.id + "/reviews/" + this.state.seriesReview.id, 
            JSON.stringify( {"loggedInUserLikes": newVal} ))
            .then(res => {
                this.setState({
                    revealSpoilers: this.state.revealSpoilers,
                    seriesReview: res.data
                });
            })
    }

    deleteComment = (commentToDelete) => {
        const { series, t } = this.props;

        confirmAlert({
            title: t("series.deleteConfirmTitle"),
            message: t("series.deleteConfirmDialog"),
            buttons: [
              {
                label: t("series.yes"),
                onClick: () => {
                    Axios.delete("/series/" + series.id + "/reviews/" + this.state.seriesReview.id + "/comments/" + commentToDelete.id)
                        .then(() => {
                            let newSeriesReview = {
                                ...this.state.seriesReview,
                                seriesReviewComments: this.state.seriesReview.seriesReviewComments.filter(comment => comment.id !== commentToDelete.id)
                            }
        
                            this.setState({
                                ...this.state,
                                seriesReview: newSeriesReview
                            });
                        });
                }
              },
              {
                label: t("series.no")
              }
            ]
        });
    }

    toggleUserBan = () => {

    }

    toggleSpoilerVisibility = () => {
        this.setState({
            revealSpoilers: !this.state.revealSpoilers
        });
    }

    publishNewComment = (values, actions) => {
        const { series } = this.props;
        const { seriesReview } = this.state;

        Axios.post("/series/" + series.id + "/reviews/" + seriesReview.id + "/comments", JSON.stringify(values))
            .then((res) => {
                let newComment = res.data;

                let newCommentList = [ ...seriesReview.seriesReviewComments ];
                newCommentList.push(newComment);
                
                let newReview = {
                    ...seriesReview,
                    seriesReviewComments: newCommentList
                }

                this.setState({
                    seriesReview: newReview
                });

                actions.resetForm();
            })
            .catch((err) => {
                
            })
            .finally(() => actions.setSubmitting(false));
    }

    render() {
        const { t, logged_user, series, deleteReview } = this.props;

        const { seriesReview, revealSpoilers } = this.state;

        let replies = seriesReview.seriesReviewComments.map((comment, id) => {
            return <DiscussionReviewComment key={comment.id} deleteComment={this.deleteComment} comment={comment} series={series} seriesReview={seriesReview} />
        });

        return (
            <div className="comment clearfix extended">
                <article className="post">
                    <div className="top">
                        <div className="holder">
                            <div className="author-label mb-3">
                                { logged_user ?
                                    (<Link to={"/users/" + seriesReview.user.id} title={t("index.profile")}>
                                        <span>{seriesReview.user.userName}</span>
                                    </Link>)
                                    :
                                    (<span>{seriesReview.user.userName}</span>)
                                }

                                { ( logged_user && logged_user.isAdmin && logged_user.id !== seriesReview.user.id ) &&
                                    (
                                        ( seriesReview.user.isBanned ) ?
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
                                    { ( logged_user && (logged_user.isAdmin || logged_user.id === seriesReview.user.id) ) &&
                                        <button className="remove" onClick={() => deleteReview(seriesReview)}>
                                            <FontAwesomeIcon icon={faTrash} />
                                        </button>
                                    }

                                    { ( logged_user && seriesReview.loggedInUserLikes ) ?
                                        (<button className="heart post-liked no-padding" onClick={this.toggleLike}>
                                            <FontAwesomeIcon icon={faHeart} />
                                        </button>)
                                        :
                                        (<button className="heart no-padding" onClick={this.toggleLike}>
                                            <FontAwesomeIcon icon={faHeart} />
                                        </button>)
                                    }
                                    { seriesReview.likes }
                                </div>

                                { ( seriesReview.spam && !revealSpoilers ) ?
                                    (<blockquote className="original">
                                        <FontAwesomeIcon icon={faExclamationTriangle} />
                                        { t("series.hasSpoiler") }

                                        <button className="show_spoiler" onClick={this.toggleSpoilerVisibility}>
                                            { t("series.show") }
                                        </button>
                                    </blockquote>)
                                    :
                                    (<p>{ seriesReview.body }</p>)
                                }
                            </div>
                        </div>
                    </div>
                </article>

                <div className="replies sub-comment">
                    { replies }

                    { (logged_user && !logged_user.isBanned) &&
                        <Formik
                            initialValues={{ body: '' }}
                            validationSchema={Yup.object({
                                body: Yup.string()
                                    .min(6, 'Must be 6 characters or more')
                                    .required('Required'),
                            })}
                            onSubmit={this.publishNewComment}
                            >
                            {formik => (
                                <form onSubmit={formik.handleSubmit}>
                                    <div className="holder">
                                        <div className="textarea-wrapper">
                                            <div className="mentions-input-box">
                                                <textarea rows="1" maxLength="255"  placeholder={t("series.enterReply")}
                                                    path="commentBody" style={{overflow: "hidden", height: "50px"}}
                                                    {...formik.getFieldProps('body')}/>
                                                {formik.touched.body && formik.errors.body ? (
                                                    <span className="error m-3 w-100">{formik.errors.body}</span>
                                                ) : null}
                                                <button type="submit" className="post-comment">
                                                    { t("series.post") }
                                                </button>
                                            </div>
                                        </div>
                                        <div className="clearfix"></div>
                                    </div>
                                </form>
                            )}
                        </Formik>
                    }
                </div>
            </div>
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
)(DiscussionReview);