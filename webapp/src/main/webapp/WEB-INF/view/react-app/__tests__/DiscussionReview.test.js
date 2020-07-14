import React from 'react'
import { render, fireEvent, screen } from '../__tests_utils/testUtils'
import DiscussionReview from '../src/components/DiscussionReview'

const series = null;
const seriesReview = {"body": "Review 1", "id": 1, "likes": 0, "loggedInUserLikes": false, "seriesReviewComments": [], "spam": false, "user": {"admin": false, "avatar": "", "banned": false, "id": 1, "userName": "User 1"}};

it('should call toggleUserBanned if toggleUserBannedButton clicked', () => {
    const mockToggleUserBanned = jest.fn();
    render(<DiscussionReview toggleUserBanned={mockToggleUserBanned}
                             seriesReview={seriesReview}
                             series={series} />);

    fireEvent.click(screen.getByTestId("toggleUserBannedButton"));
    expect(mockToggleUserBanned).toHaveBeenCalledTimes(1);
});

it('should call deleteComment if deleteReviewButton clicked', () => {
    const mockDeleteReview = jest.fn();
    render(<DiscussionReview deleteReview={mockDeleteReview}
                             seriesReview={seriesReview}
                             series={series} />);

    fireEvent.click(screen.getByTestId("deleteReviewButton"));
    expect(mockDeleteReview).toHaveBeenCalledTimes(1);
});