import React from 'react'
import { render, fireEvent, screen } from '../__tests_utils/testUtils'
import SeasonAccordionItem from '../src/components/SeasonAccordionItem'

const episode = {"id": 1, "name": "Episode 1", "viewedByUser": true, "air_date": ""};

it('should call onEpisodeWatchedClickedHandler if onEpisodeWatchedClickedHandlerButton clicked', () => {
    const mockOnEpisodeWatchedClickedHandler = jest.fn();
    render(<SeasonAccordionItem season_index={1}
                                number={1}
                                episode={episode}
                                onEpisodeWatchedClickedHandler={mockOnEpisodeWatchedClickedHandler} />);
    fireEvent.click(screen.getByTestId("onEpisodeWatchedClickedHandlerButton"));
    expect(mockOnEpisodeWatchedClickedHandler).toHaveBeenCalledTimes(1);
});