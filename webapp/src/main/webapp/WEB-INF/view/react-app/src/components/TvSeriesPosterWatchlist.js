import React, { PureComponent } from 'react';
import { Trans } from 'react-i18next';
import { Link } from 'react-router-dom';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCheckCircle} from "@fortawesome/free-solid-svg-icons";

class TvSeriesPosterWatchlist extends PureComponent {

    onEpisodeWatchedClicked = (event) => this.props.onEpisodeWatchedClickedHandler(event, this.props.series.id, this.props.episode.seasonNumber, this.props.episode.numEpisode);

    render() {
        const { series, episode } = this.props;

        return (
            <li>
                <div className="image-crop">
                    <Link to={ "/series/" + series.id }>
                        <img src={"https://image.tmdb.org/t/p/original" + series.posterUrl} alt={series.name}/>
                    </Link>
                    <button className="check-follow">
                        <FontAwesomeIcon icon={ faCheckCircle } onClick={this.onEpisodeWatchedClicked} className="icon" />
                    </button>
                </div>
                <div className="episode-details poster-details">
                    <h2> <Trans i18nKey={ "watchlist.season" }/>{ episode.seasonNumber.toString().padStart(2,'0') }E{ episode.numEpisode.toString().padStart(2,'0') }</h2>
                    <Link to={ "/series/" + series.id } className="nb-reviews-link secondary-link">
                        { series.name }
                    </Link>
                </div>
            </li>
        )
    }
};

export default TvSeriesPosterWatchlist;