import React, {PureComponent} from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons'

class SeasonAccordionItem extends PureComponent {

    onEpisodeWatchedClicked = (event) => this.props.onEpisodeWatchedClickedHandler(event, this.props.season_index, this.props.number, false);

    render() {
        const episode = this.props.episode;

        return (
            <li key={episode.id} className="cd-accordion__label cd-accordion__label--icon-img">
                <div className="cd-accordion__item">
                    <h3 className="m-0" style={{fontSize: "1.75rem"}}>
                        {this.props.number + 1} - {episode.name}
                    </h3>
                    <span className="ml-3 episode-date">
                        {episode.air_date}
                    </span>
                    <button data-testid="onEpisodeWatchedClickedHandlerButton" onClick={this.onEpisodeWatchedClicked} className={"check" + (episode.viewedByUser ? " viewed" : "")}>
                        <FontAwesomeIcon icon={faCheckCircle} />
                    </button>
                </div>
            </li>
        );
    }
}

export default SeasonAccordionItem;