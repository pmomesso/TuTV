import React, { Component } from 'react';
import { compose } from 'recompose';
import { withTranslation } from 'react-i18next';
import { connect } from 'react-redux';
import DiscussionReview from './DiscussionReview';
import Axios from 'axios';
import { Digital } from 'react-activity';
import { Formik } from 'formik';
import * as Yup from 'yup';

class Discussion extends Component {
    state = {
        loading: true,
        seriesReviews: null
    }

    componentDidMount = () => {
        Axios.get("/series/" + this.props.series.id + "/reviews")
            .then(res => {
                this.setState({
                    loading: false,
                    seriesReviews: res.data
                });
            });
    };

    deleteComment = (commentToDelete) => {
        this.setState({
            ...this.state,
            seriesReviews: this.state.seriesReviews.filter(comment => comment.id !== commentToDelete.id)
        });
    }

    publishNewComment = (values, actions) => {
        const { series } = this.props;
        const { seriesReviews } = this.state;

        Axios.post("/series/" + series.id + "/reviews/", JSON.stringify(values))
            .then((res) => {
                let newComment = res.data;

                let newCommentList = [ ...seriesReviews ];
                newCommentList.unshift(newComment);

                this.setState({
                    seriesReviews: newCommentList
                });

                actions.resetForm();
            })
            .catch((err) => {
                
            })
            .finally(() => actions.setSubmitting(false));
    }

    render() {
        if(this.state.loading)
            return (
                <section>
                    <div style={{width: "100%",height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
                        <Digital color="#727981" size={32} speed={1} animating={true} />
                    </div>
                </section>
            );

        const { t, logged_user, series } = this.props;

        const reviews = this.state.seriesReviews.map(seriesReview => {
            return <DiscussionReview key={seriesReview.id} deleteReview={this.deleteComment} seriesReview={seriesReview} series={series} />
        });

        return (
            <div id="discussion">
                <h2 id="community-title" className="title">
                    { t("series.discussion") }
                </h2>
                <div id="show-comments">
                    <div className="comments">
                        
                        { (logged_user && !logged_user.isBanned) &&
                            <Formik
                                initialValues={{ body: '', isSpam: false }}
                                validationSchema={Yup.object({
                                    body: Yup.string()
                                        .min(6, 'Must be 6 characters or more')
                                        .required('Required'),
                                })}
                                onSubmit={this.publishNewComment}
                                >
                                {formik => (
                                    <div className="new-comment comment">
                                        <div className="disclaimer">
                                            <p className="disclaimer-title">
                                                { t("series.spoil") }
                                            </p>
                                        </div>
                                        <form className="post" onSubmit={formik.handleSubmit}>
                                            <div className="top">
                                                <div className="holder mode-comment mode">
                                                    <div className="comment-mode mode">
                                                        <div className="textarea-wrapper">
                                                            <div className="mentions-input-box">
                                                                <textarea maxLength="255" placeholder={ t("series.enterComment") } path="body"
                                                                    style={{overflow: "hidden", height: "40px"}}
                                                                    {...formik.getFieldProps('body')}/>
                                                                {formik.touched.body && formik.errors.body ?
                                                                    (<span className="error m-3 w-100">{formik.errors.body}</span>) : null}
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="author">
                                                    {
                                                        (logged_user.avatar) ?
                                                            (<img src={`data:image/jpeg;base64,${logged_user.avatar}`} className="author-picture img-circle" alt={logged_user.userName}/>)
                                                            :
                                                            (<img src={"https://d36rlb2fgh8cjd.cloudfront.net/default-images/default-user-q80.png"} className="author-picture img-circle" alt={logged_user.userName}/>)
                                                    }
                                                </div>
                                            </div>
                                            <div className="submit-comment">
                                                <label htmlFor="isSpam" className="spoiler-span">
                                                    <input {...formik.getFieldProps('isSpam')} type="checkbox" className="check-spoiler" path="isSpam" id="isSpam" />
                                                    { t("series.markSpoiler") }
                                                </label>
                                                <button type="submit" className="submit-comment-btn">
                                                    { t("series.post") }
                                                </button>   
                                            </div>
                                        </form>
                                    </div>
                                )}
                            </Formik>
                        }

                        <div className="comments-list">
                            { (this.state.seriesReviews.length === 0) &&
                                t("series.noPosts") 
                            }

                            {reviews}
                        </div>
                    </div>
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
)(Discussion);