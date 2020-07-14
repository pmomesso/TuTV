import React, {PureComponent} from 'react';
import { withTranslation } from 'react-i18next';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons'
import SeasonAccordionItem from './SeasonAccordionItem';

class SeasonAccordion extends PureComponent {

    onSeasonWatchedClicked = (event) => this.props.onSeasonWatchedClicked(event, this.props.number);

    render() {
        const { t } = this.props;

        const season = this.props.season;
        let season_index = this.props.number;

        let episodeCount = season.episodes.length;
        let viewedEpisodes = 0;
        var episodeList = [];
        for (let j = 0; j < episodeCount; j++) {
            let episode = season.episodes[j];
            if (episode.viewedByUser)
                viewedEpisodes++;

            episodeList.push(
                <SeasonAccordionItem key={j} episode={episode} season_index={season_index} number={j} onEpisodeWatchedClickedHandler={this.props.onEpisodeWatchedClickedHandler}/>
            );
        }


        return (
            <ul key={season_index} className="cd-accordion cd-accordion--animated">
                <li className="cd-accordion__item cd-accordion__item--has-children"  style={{fontSize: "10px"}}>
                    <input className="cd-accordion__input" type="checkbox" name={"group-" + season_index} id={"group-" + season_index} />
                    <label
                        className={"cd-accordion__label cd-accordion__label--icon-folder drop" + (viewedEpisodes === episodeCount ? " drop-watched" : "")}
                        style={{marginBottom: "0px"}}
                        htmlFor={"group-" + season_index}>

                        <span className="big-size">
                            { t("series.season_number", { count: season.number }) }
                        </span>
                        <span className="ml-3 viewed-episodes">
                            {viewedEpisodes} / {episodeCount }
                        </span>
                        <div>
                            <button data-testid="onSeasonWatchedClickedButton" onClick={this.onSeasonWatchedClicked} className={"check-season" + (viewedEpisodes === episodeCount ? " viewed" : "")}>
                                <FontAwesomeIcon icon={faCheckCircle} />
                            </button>
                        </div>
                    </label>
                    <ul className="cd-accordion__sub cd-accordion__sub--l1">
                        { episodeList }
                    </ul>
                </li>
            </ul>
        );
    }
}

export default withTranslation()(SeasonAccordion);