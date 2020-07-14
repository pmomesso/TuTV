import React from 'react'
import { render, fireEvent, screen } from '../__tests_utils/testUtils'
import TvSeriesPoster from '../src/components/TvSeriesPoster'

const series = {"id": 1};
const userLists = [{"id": 1, "name": "Favoritos", "seriesUri": "/api/users/1/lists/1/series"}];

it('should call addSeriesToListHandler if onSeriesAddToListButton clicked', () => {
    const mockAddSeriesToListHandler = jest.fn();
    render(<TvSeriesPoster addSeriesToListHandler={mockAddSeriesToListHandler}
                           userLists={userLists}
                           series={series} />);
    fireEvent.click(screen.getAllByRole("menuitem")[3]);
    expect(mockAddSeriesToListHandler).toHaveBeenCalledTimes(1);
});