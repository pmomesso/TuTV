import React from 'react';
import { Trans } from 'react-i18next';
import { Link } from 'react-router-dom';

const TvSeriesPosterUpcoming = (props) => {
    const { series, episode } = props;
    return (
        <li>
            // TODO format date
            <h4>{ episode.air_date }</h4>
            <Link to={ "/series/" + series.id }>
                <div className="image-crop">
                    <img
                        src={"https://image.tmdb.org/t/p/original" + series.posterUrl}
                        alt={series.name}/>
                    <div className="overlay">
                        <span className="zoom-btn overlay-btn"></span>
                    </div>
                </div>
                <div className="episode-details poster-details">
                    <h2> <Trans i18nKey={ "watchlist.season" }/>{ episode.seasonNumber.toString().padStart(2,'0') }E{ episode.numEpisode.toString().padStart(2,'0') }</h2>
                    <span className="secondary-link">
                        { series.name }
                    </span>
                </div>
            </Link>
        </li>
    )
};

export default TvSeriesPosterUpcoming;