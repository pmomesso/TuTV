import React, {Component} from 'react';
import SeriesList from '../components/SeriesList'
import Axios from 'axios';
import SeriesCarousel from '../components/SeriesCarousel';
import { connect } from 'react-redux';
import { compose } from 'recompose';
import { withTranslation } from 'react-i18next';

class Explore extends Component {
    state = {
        bannerSeries: [],
        userLists: null,
        genreList: [],
    };

    componentDidMount = () => {
        Axios.get("/genres")
        .then(res => {
            this.setState({
                genreList: res.data,
            })
        });

        if(this.props.logged_user)
            Axios.get("/users/" + this.props.logged_user.id + "/lists")
            .then(res => {
                this.setState({
                    userLists: res.data,
                })
            });
    };

    createSeriesListAndAddSeriesHandler = (series) => {
        const { t, logged_user } = this.props;
        let listName = window.prompt(t("series.newListName"),"");

        if(!listName)
            return;

        Axios.post("/users/" + logged_user.id + "/lists", JSON.stringify({"name": listName}))
            .then(res => {
                this.setState({
                    ...this.state,
                    userLists: [res.data].concat(this.state.userLists)
                });
                this.addSeriesToListHandler(res.data, series);
            })
            .catch(res => {

            });
    }

    addSeriesToListHandler = (list, series) => {
        if(!list) { //Crear lista
            this.createSeriesListAndAddSeriesHandler(series);
        } else {
            Axios.post("/users/" + this.props.logged_user.id + "/lists/" + list.id + "/series",
                JSON.stringify({"seriesId": series.id}));
        }
    }

    render() {
        const seriesLists = this.state.genreList.map(genre => {
            return(
                <SeriesList key={ genre.id } name={ genre.name } source={ genre.seriesUri } addSeriesToListHandler={this.addSeriesToListHandler} userLists={this.state.userLists}/>
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

const mapStateToProps = (state) => {
    return {
        logged_user: state.auth.user
    }
};

export default compose(
    connect(mapStateToProps),
    withTranslation()
)(Explore);