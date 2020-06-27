import React from 'react';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';

const TvSeriesPoster = (props) => {
    const { t } = useTranslation();
    const { series } = props;
    return (
        <li className="float-left">
            <Link to={ "/series/" + series.id }>
                <div className="image-crop">
                    <img 
                        src={"https://image.tmdb.org/t/p/original" + series.posterUrl} 
                        alt={series.name}/>
                    <div className="overlay">
                        <span className="zoom-btn overlay-btn"></span>
                    </div>
                </div>
                <div className="show-details poster-details">
                    <h2>{ series.name }</h2>
                    <span className="secondary-link">
                        { t("index.followers", { count: series.followers }) }
                    </span>
                </div>
            </Link>
        </li>
    )
}

export default TvSeriesPoster;