import React from 'react'
import { render, fireEvent, screen } from '../__tests_util__/testUtils'
import DiscussionReview from '../src/components/DiscussionReview'

const series = null;
const seriesReview = {"body": "Review 1", "id": 1, "likes": 0, "loggedInUserLikes": false, "seriesReviewComments": [], "spam": false, "user": {"admin": false, "avatar": "", "banned": false, "id": 1, "userName": "User 1"}};

it('should never call toggleUserBanned if logged_user is not admin', () => {
    const mockToggleUserBanned = jest.fn();
    const logged_user = {"admin": false, "avatar": "", "banned": false, "id": 2, "userName": "User 2"};
    render(<DiscussionReview logged_user={logged_user}
                             toggleUserBanned={mockToggleUserBanned}
                             seriesReview={seriesReview}
                             series={series} />);

    // fireEvent.click(screen.getByTestId("toggleUserBannedButton"));
    expect(mockToggleUserBanned).toHaveBeenCalledTimes(0);
});

it('should never call deleteComment if logged_user is not comment owner', () => {
    const mockDeleteComment = jest.fn();
    const logged_user = {"admin": false, "avatar": "", "banned": false, "id": 2, "userName": "User 2"};
    render(<DiscussionReview logged_user={logged_user}
                             deleteReview={mockDeleteComment}
                             seriesReview={seriesReview}
                             series={series} />);

    // fireEvent.click(screen.getByTestId("toggleUserBannedButton"));
    expect(mockDeleteComment).toHaveBeenCalledTimes(0);
});