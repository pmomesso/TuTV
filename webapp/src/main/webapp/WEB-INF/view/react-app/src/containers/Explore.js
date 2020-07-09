import React, {Component} from 'react';
import SeriesList from '../components/SeriesList'
import Axios from 'axios';
import SeriesCarousel from '../components/SeriesCarousel';

class Explore extends Component {
    state = {
        bannerSeries: [ ],
        seriesLists: [ ],
        genreList: []
    };

    componentDidMount = () => {
        Axios.get("/genres")
        .then(res => {
            this.setState({
                genreList: res.data,
            })
        });
    };

    render() {
        const seriesLists = this.state.genreList.map(genre => {
            return(
                <SeriesList key={ genre.id } name={ genre.name } source={ genre.seriesUri }/>
            )
        });

        return (
            <div className="alt-block" style={{background: 'white'}}>
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