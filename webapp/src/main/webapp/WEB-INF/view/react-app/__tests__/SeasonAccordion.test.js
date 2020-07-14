import React from 'react'
import { render, fireEvent, screen } from '../__tests_utils/testUtils'
import SeasonAccordion from '../src/components/SeasonAccordion'

const season = {"number": 1, "episodes": [{"viewedByUser": true},{"viewedByUser": true}]};

it('should call addSeriesToListHandler if onSeriesAddToListButton clicked', () => {
    const mockOnSeasonWatchedClicked = jest.fn();
    render(<SeasonAccordion number={1} season={season}
                            onSeasonWatchedClicked={mockOnSeasonWatchedClicked} />);
    fireEvent.click(screen.getByTestId("onSeasonWatchedClickedButton"));
    expect(mockOnSeasonWatchedClicked).toHaveBeenCalledTimes(1);
});
