import React, {Component} from 'react';
import SeriesList from '../components/SeriesList'
import Axios from 'axios';
import SeriesCarousel from '../components/SeriesCarousel';

import * as CONSTANTS from '../constants.js'

class Explore extends Component {
    state = {
        bannerSeries: [ ],
        seriesLists: [ ],
        genreList: []
    }

    componentDidMount = () => {
        Axios.get(CONSTANTS.APIURL + "/series/genres")
        .then(res => {
            this.setState({
                genreList: res.data.genres,
            })
        });
    }

    render() {
        const seriesLists = this.state.genreList.map(genre => {
            console.log(genre.name);
            return(
                <SeriesList key={ genre.id } name={ genre.i18Key } source={ "/series/genres/" + genre.id }/>
            )
        });

        return (
            <div className="alt-block">
                <div className="main-block">
                    <div className="main-block-container">
                        <div id="explore">
                            <SeriesCarousel source="/series/featured"/>
                            { seriesLists }
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Explore;